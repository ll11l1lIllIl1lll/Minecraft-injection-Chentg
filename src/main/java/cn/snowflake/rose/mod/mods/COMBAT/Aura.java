package cn.snowflake.rose.mod.mods.COMBAT;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventAura;
import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.events.impl.EventRender3D;
import cn.snowflake.rose.management.FriendManager;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.client.RotationUtil;
import cn.snowflake.rose.utils.mcutil.GlStateManager;
import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.render.RenderUtil;
import cn.snowflake.rose.utils.time.TimeHelper;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.EventType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author TAIFENG
 *
 */
public class Aura extends Module {
    private TimeHelper time = new TimeHelper();
    public TimeHelper timer2 = new TimeHelper();
    private TimeHelper switchtime = new TimeHelper();
    public static EntityLivingBase target;

    private static int switchIndex;
    public Value<String> mode = new Value<String>("Aura","Mode", 0);
    public Value<String> sortingMode = new Value<String>("Aura","SortingMode", 0);
    public Value<Double> range= new Value<Double>("Aura_Reach", 4.5D, 1.0D, 7.0D,0.1D);
    public Value<Double> cps = new Value<Double>("Aura_CPS", 12.0D, 1.0D, 20.0D, 1.0D);
    public Value<Double> switchdelay= new Value<Double>("Aura_SwitchDelay", 200.0D, 1.0D, 1000.0D,10.0D);
    public Value<Double> maxtTargets = new Value<Double>("Aura_MultiMaxTarget", 20.0D, 1.0D, 50.0D, 1.0D);
    public Value<Boolean> Esp = new Value("Aura_Esp", true);
    public Value<Boolean> Nowing = new Value<Boolean>("Aura_NoSwing", false);
    public Value<Boolean> players = new Value("Aura_Player", true);
    public Value<Boolean> animal = new Value("Aura_Animal", false);
    public Value<Boolean> moster = new Value("Aura_Mob", false);
    public Value<Boolean> village = new Value("Aura_village", false);
    public Value<Boolean> invisible = new Value("Aura_Invisible", false);
    public Value<Boolean> block = new Value("Aura_AutoBlock", true);
    public Value<Boolean> wall = new Value<Boolean>("Aura_ThroughWall", true);
    public Value<Boolean> customnpcs = new Value<Boolean>("Aura_CustomNPCs", false);

    public Aura() {
        super("Aura","Aura", Category.COMBAT);
        this.mode.addValue("Single");
        this.mode.addValue("Switch");
        this.mode.addValue("Multi");
        this.sortingMode.addValue("Health");
        this.sortingMode.addValue("MaxHealth");
        this.sortingMode.addValue("Distance");
        setChinesename("\u6740\u622e\u5149\u73af");
    }

    @Override
    public String getDescription() {
        return "杀戮光环!";
    }

    @SubscribeEvent
    public void On2D(RenderGameOverlayEvent e) {
        if(target !=null) {
            ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            int height = sr.getScaledHeight() / 2 - 20;
            int width = sr.getScaledWidth() / 2 + 15;
            mc.fontRenderer.drawStringWithShadow(target.getCommandSenderName() + " \2474\u2764\247f" + Math.round(target.getHealth()), width,  height + 2, -1);
            RenderUtil.drawEntityOnScreen(width - 10, height + 20, 20, target.rotationYaw,9, target);
        }
    }

    @EventTarget
    public void OnPre(EventMotion e) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    	if (e.isPre()) {
            target = getTarget(range.getValueState());
            if (target != null) {
                if (block.getValueState() && !mc.thePlayer.isBlocking() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                    startAutoBlock();
                }
            } else {
                if (block.getValueState() && mc.thePlayer.isBlocking() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                    stopAutoBlock();
                }
            }
            if (mode.getModeName().equalsIgnoreCase("Single")) {
                setDisplayName("Single");
                target = getTarget(range.getValueState());
                if (target == null) {
                    return;
                }
                float[] rotations = RotationUtil.getRotations(target);
                e.setYaw(rotations[0]);
                e.setPitch(rotations[1]);
//                e.setRPITCH(rotations[1]);//animotion
                mc.thePlayer.rotationYawHead = rotations[0];
                mc.thePlayer.renderYawOffset = rotations[0];
            }
            if (mode.getModeName().equalsIgnoreCase("Switch")) {
                setDisplayName("Switch");
                if (this.getTargets().size() <= 0) {
                    target = null;
                    return;
                }
                if (switchIndex >= this.getTargets().size()) {
                    switchIndex = 0;
                }
                target = (EntityLivingBase) this.getTargets().get(switchIndex);
                if (this.switchtime.isDelayComplete(switchdelay.getValueState().longValue())) {
                    ++switchIndex;
                    this.switchtime.reset();
                }
                float[] rotations = RotationUtil.getRotations(target);
                e.setYaw(rotations[0]);
                e.setPitch(rotations[1]);
                mc.thePlayer.rotationYawHead = rotations[0];
                mc.thePlayer.renderYawOffset = rotations[0];
            }
            if (mode.getModeName().equalsIgnoreCase("Multi")) {
            	 setDisplayName("Multi");
                if (this.getTargets().size() <= 0) {
                    target = null;
                    return;
                }
            	 target = (EntityLivingBase) this.getTargets().get(0);
            }
        }else if (e.getEventType() == EventType.POST){
			if (mode.getModeName().equalsIgnoreCase("Multi")) {
				if (target != null) {
					DoMultiAttack();
				}
			} else {
				if (target != null) {
					DoAttack();
				}
			}
        }
    }

    @Override
    public void onDisable() {
        stopAutoBlock();
        target = null;
        super.onDisable();
    }


    private void startAutoBlock() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(),true);
        this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
    }
    private void stopAutoBlock() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(),false);

        this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);

    }



    private List getTargets() {
        try {
            ArrayList<EntityLivingBase> targets = new ArrayList();
            for(Object entity : mc.theWorld.loadedEntityList) {
                if(canTarget((Entity) entity) && entity instanceof EntityLivingBase) {
                    targets.add((EntityLivingBase) entity);
                }
            }

            if (sortingMode.isCurrentMode("Distance")) {
                targets.sort((o1, o2) -> (int) (o1.getDistanceToEntity(mc.thePlayer) - o2.getDistanceToEntity(mc.thePlayer))
                );
            }else if (sortingMode.isCurrentMode("Health")){
                targets.sort((o1, o2) ->
                        (int) (o1.getHealth() - o2.getHealth())
                );
            }else if (sortingMode.isCurrentMode("MaxHealth")){
                targets.sort((o1, o2) ->
                        (int) (o1.getHealth() - o2.getHealth())
                );
                Collections.reverse(targets);
            }else {
                targets.sort((o1, o2) -> {
                    float[] rot1 = RotationUtil.getRotations(o1);
                    float[] rot2 = RotationUtil.getRotations(o2);
                    return (int)(mc.thePlayer.rotationYaw - rot1[0] - (mc.thePlayer.rotationYaw - rot2[0]));
                });
            }
            return targets;
        } catch (Exception e) {
            return null;
        }
    }

	@EventTarget
	public void onRender(EventRender3D render) {
		if (Esp.getValueState().booleanValue()) {
			if (mode.getModeName().equalsIgnoreCase("Multi")) {
				if (target != null) {
					drawMultiESP();
				}
			} else {
				if (target != null) {
					drawESP();
				}
			}
		}
	}

    private void drawESP() {
        double x = target.lastTickPosX
                + (target.posX - target.lastTickPosX) * JReflectUtility.getRenderPartialTicks()
                - RenderManager.renderPosX;
        double y = target.lastTickPosY
                + (target.posY - target.lastTickPosY) * JReflectUtility.getRenderPartialTicks()
                - RenderManager.renderPosY;
        double z = target.lastTickPosZ
                + (target.posZ - target.lastTickPosZ) * JReflectUtility.getRenderPartialTicks()
                - RenderManager.renderPosZ;
        drawCylinderESP(target,x, y + target.getEyeHeight() + 0.5d, z);
    }
    private void drawMultiESP() {
 
        if (!this.getTargets().isEmpty() ) {
	           if (this.getTargets().size() > 0) {
	               for (int i = 0; i < (this.getTargets().size() > maxtTargets.getValueState() ? maxtTargets.getValueState() : this.getTargets().size()); i++) {
	            	   EntityLivingBase target = (EntityLivingBase) this.getTargets().get(i);
	            	   double x = target.lastTickPosX
	                           + (target.posX - target.lastTickPosX) * JReflectUtility.getRenderPartialTicks()
	                           - RenderManager.renderPosX;
	                   double y = target.lastTickPosY
	                           + (target.posY - target.lastTickPosY) * JReflectUtility.getRenderPartialTicks()
	                           - RenderManager.renderPosY;
	                   double z = target.lastTickPosZ
	                           + (target.posZ - target.lastTickPosZ) * JReflectUtility.getRenderPartialTicks()
	                           - RenderManager.renderPosZ;
	            	   drawCylinderESP(target,x, y + target.getEyeHeight() + 0.5d, z);
	            
	               }
	           }
        }
    }

    public static void disableSmoothLine() {
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 2929);
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 3008);
        GL11.glDepthMask((boolean) true);
        GL11.glCullFace((int) 1029);
        GL11.glDisable((int) 2848);
        GL11.glHint((int) 3154, (int) 4352);
        GL11.glHint((int) 3155, (int) 4352);
    }

    public static void enableSmoothLine(float width) {
        GL11.glDisable((int) 3008);
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glDisable((int) 3553);
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) false);
        GL11.glEnable((int) 2884);
        GL11.glEnable((int) 2848);
        GL11.glHint((int) 3154, (int) 4354);
        GL11.glHint((int) 3155, (int) 4354);
        GL11.glLineWidth((float) width);
    }

    public void drawCylinderESP(EntityLivingBase entity, double x, double y, double z) {
    	Cylinder c = new Cylinder();
    	RenderUtil.pre3D();
        GlStateManager.disableLighting();
        GL11.glTranslated((double) x, (double) y, (double) z);
        GL11.glRotatef((float) (-entity.width), (float) 0.0f, (float) 1.0f, (float) 0.0f);
        RenderUtil.glColor(new Color(1, 89, 1, 150).getRGB());// color4f
        enableSmoothLine(0.1f);//
        GL11.glRotatef((float) -90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.0f, 0.2f, 0.5f, 5, 300);
        disableSmoothLine();
        RenderUtil.post3D();
        RenderUtil.pre3D();
        GL11.glPushMatrix();
        GlStateManager.disableLighting();
        GL11.glTranslated((double) x, (double) y + 0.5f, (double) z);
        GL11.glRotatef((float) (-entity.width), (float) 0.0f, (float) 1.0f, (float) 0.0f);
        RenderUtil.glColor(new Color(2, 168, 2, 150).getRGB());// color4f
        enableSmoothLine(0.1f);//
        GL11.glRotatef((float) -90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.2f, 0.0f, 0.5f, 5, 300);
        disableSmoothLine();
//        GlStateManager.enableLighting();
        GL11.glPopMatrix();
        RenderUtil.post3D();
      
    }
    
	
    private void DoMultiAttack() {
        int aps = (this.cps.getValueState()).intValue();
        int DelayValue = 1000 / aps + this.random.nextInt(50) - 30;
        if ((double) mc.thePlayer.getDistanceToEntity(target) <= this.range.getValueState().floatValue() && this.timer2.isDelayComplete((DelayValue - 20 + this.random.nextInt(50)))) {
            this.Multiatttack();
            timer2.reset();
        }
    }
    private void Multiatttack() {
        if (block.getValueState() && mc.thePlayer.isBlocking()&& mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            stopAutoBlock();
        }
        EventAura ea = new EventAura(target);
        EventManager.call(ea);

        if (!mc.thePlayer.isBlocking() && block.getValueState()) {
            if(!Nowing.getValueState())
        	mc.thePlayer.swingItem();
        	 if (!this.getTargets().isEmpty() ) {
  	           if (this.getTargets().size() > 0) {
  	               for (int i = 0; i < (this.getTargets().size() > maxtTargets.getValueState() ? maxtTargets.getValueState() : this.getTargets().size()); i++) {
//  	            	 mc.thePlayer.swingItem();
  	            	 mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((EntityLivingBase) this.getTargets().get(i), C02PacketUseEntity.Action.ATTACK));
  	               }
  	           }
  		  }
        }else if (!block.getValueState()) {
            if(!Nowing.getValueState())
        	mc.thePlayer.swingItem();
       	 if (!this.getTargets().isEmpty() ) {
	           if (this.getTargets().size() > 0) {
	               for (int i = 0; i < (this.getTargets().size() > maxtTargets.getValueState() ? maxtTargets.getValueState() : this.getTargets().size()); i++) {
//	            	 mc.thePlayer.swingItem();
	            	 mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity((EntityLivingBase) this.getTargets().get(i), C02PacketUseEntity.Action.ATTACK));
	               }
	           }
		  }
        }
        if (block.getValueState() && !mc.thePlayer.isBlocking() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            startAutoBlock();
        }
    }
    
    private void DoAttack() {
        int aps = (this.cps.getValueState()).intValue();
        int DelayValue = 1000 / aps + this.random.nextInt(50) - 30;
        if ((double) mc.thePlayer.getDistanceToEntity(target) <= this.range.getValueState().floatValue() && this.timer2.isDelayComplete((DelayValue - 20 + this.random.nextInt(50)))) {
            this.atttack();
            timer2.reset();
        }
    }
    private void atttack() {
        if (block.getValueState() && mc.thePlayer.isBlocking()&& mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            stopAutoBlock();
        }
        EventAura ea = new EventAura(target);
        EventManager.call(ea);

        if (!mc.thePlayer.isBlocking() && block.getValueState()) {
            if(!Nowing.getValueState())
            mc.thePlayer.swingItem();
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        }else if (!block.getValueState()) {
            if(!Nowing.getValueState())
            mc.thePlayer.swingItem();
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        }
        if (block.getValueState() && !mc.thePlayer.isBlocking() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            startAutoBlock();
        }
    }

    private EntityLivingBase getTarget(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                Entity player =  entity;
                if (canTarget(player)) {
                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = (EntityLivingBase) player;
                    }
                }
            }
        }
        return target;
    }

    private boolean canTarget(Entity entity) {
        if (Client.nshowmod){
            if (Objects.requireNonNull(JReflectUtility.getEntityNumber()).isInstance(entity)){
                return false;
            }
        }
        if (Client.customnpcs) {
            if (Objects.requireNonNull(JReflectUtility.getNPCEntity()).isInstance(entity)){
                if (!customnpcs.getValueState()){
                    return false;
                }
            }
        }else{
            if (customnpcs.getValueState()){
                customnpcs.setValueState(false);
                ChatUtil.sendClientMessage("You have no install the customeNPCs");
            }
        }
        if (!Objects.requireNonNull(ModManager.getModByName("NoFriend")).isEnabled() && FriendManager.isFriend(entity.getCommandSenderName())){
            return false;
        }
        if (entity instanceof EntityBat){
            return false;
        }
        if(!mc.thePlayer.canEntityBeSeen(entity) && !wall.getValueState()) {
            return false;
        }
        if (entity instanceof EntityPlayer && !players.getValueState()) {
            return false;
        }
        if (entity instanceof EntityAnimal && !animal.getValueState()) {
            return false;
        }
        if (entity instanceof EntityMob && !moster.getValueState()) {
            return false;
        }
        if (entity instanceof EntityVillager && !village.getValueState()) {
            return false;
        }
        if (entity.isInvisible() && !invisible.getValueState()) {
            return false;
        }

        return entity != mc.thePlayer && entity.isEntityAlive() && mc.thePlayer.getDistanceToEntity(entity) <= range.getValueState();
    }

    private final Random random = new Random();

    public int random(double min, double max) {
        return (int) (random.nextInt((int) (max - min)) + min);
    }

}
