package cn.snowflake.rose.mod.mods.COMBAT;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.NativeMethod;
import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.events.impl.EventRender2D;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.management.FriendManager;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.RotationUtil;
import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.render.RenderUtil;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.Priority;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.injection.ClientLoader;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Aimbot extends Module {

    public static Value<Double> index = new Value<Double>("Aimbot_pitchIndex", 1.0, -0.01, 1.0, 0.01);
    public static Value<Double> predict = new Value<Double>("Aimbot_Predict", 8.0, 0.0, 15.0, 1);

    public static Value<Double> range = new Value<Double>("Aimbot_Reach", 10.5D, 3.0D, 200.0D,0.1D);

    public Value<Double> fov = new Value<Double>("Aimbot_Fov", 10.0, 1.0, 180.0, 1.0);
    public Value<Boolean> throughwall = new Value<Boolean>("Aimbot_ThroughWall", false);

    public Value<Boolean> players = new Value<Boolean>("Aimbot_Player", true);
    public Value<Boolean> otherentity = new Value<Boolean>("Aimbot_ModsEntity", false);
    public Value<Boolean> animal = new Value<Boolean>("Aimbot_Animal", false);
    public Value<Boolean> moster = new Value<Boolean>("Aimbot_Mob", false);
    public Value<Boolean> village = new Value<Boolean>("Aimbot_village", false);
    public Value<Boolean> invisible = new Value<Boolean>("Aimbot_Invisible", false);
    public Value<Boolean> silent = new Value<Boolean>("Aimbot_Silent", false);
    public Value<Boolean> targetinfo = new Value<Boolean>("Aimbot_TargetInfo", false);
    public Value<Boolean> circle = new Value<Boolean>("Aimbot_Circle", false);
    public double targetspeed;

    public Value<String> sortingMode = new Value<String>("Aimbot","SortingMode", 0);
    public Value<String> predictmode = new Value<String>("Aimbot","PredictionMode", 0);

    public static EntityLivingBase target;

    public int buffer;
    private Map<EntityPlayer, List<Vec3>> playerPositions;


    public Aimbot() {
        super("Aimbot","Aim Bot", Category.COMBAT);
        this.sortingMode.addValue("Health");
        this.sortingMode.addValue("Distance");
        this.predictmode.addValue("Auto");
        this.predictmode.addValue("Manual");
        this.playerPositions = new HashMap<>();
        this.buffer = 10;
        setChinesename("\u81ea\u7784");
    }

    @Override
    public String getDescription() {
        return "自瞄!";
    }
    @Override
    public void onDisable(){
        target = null;
    }


    @EventTarget
    public void on2D(EventRender2D eventRender2D){
		ScaledResolution res = new ScaledResolution(mc,mc.displayWidth,mc.displayHeight);

		if (canTarget(target)) {
			if(targetinfo.getValueState()) {
			    NativeMethod.method1();

				mc.fontRenderer.drawStringWithShadow(
	                    "HP: " + target.getHealth(),
	                    res.getScaledWidth() / 2 - 10 - mc.fontRenderer.getStringWidth("HP: " + target.getHealth()),
	                    res.getScaledHeight() / 2 - 10,
	                    16777215);

	            mc.fontRenderer.drawStringWithShadow(
	                    "SPD: " + roundToPlace(targetspeed,2),
	                    res.getScaledWidth() / 2 - 10 - mc.fontRenderer.getStringWidth("SPD: " + roundToPlace(targetspeed,2)),
	                    res.getScaledHeight() / 2,
	                    16777215);

	            mc.fontRenderer.drawStringWithShadow(
	                    "name: " + target.getCommandSenderName(),
	                    res.getScaledWidth() / 2 + 10,
	                    res.getScaledHeight() / 2,
	                    16777215);

	            mc.fontRenderer.drawStringWithShadow(
	                    "hurt : " + (target.hurtTime > 0),
	                    res.getScaledWidth() / 2 + 10,
	                    res.getScaledHeight() / 2 - 10,
	                    16777215);

			}
        }
        if (circle.getValueState()) {
            RenderUtil.drawCircle(res.getScaledWidth() / 2, res.getScaledHeight() / 2,
                    fov.getValueState().floatValue() * 3.5f, 500, -1);
        }
    }

    public static double roundToPlace(double p_roundToPlace_0_,int p_roundToPlace_2_) {
        if (p_roundToPlace_2_ < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(p_roundToPlace_0_).setScale(p_roundToPlace_2_, RoundingMode.HALF_UP).doubleValue();
    }



    @EventTarget(Priority.HIGH)
    public void onTick(EventUpdate e){
        if (mc.thePlayer != null) {
            if (Objects.requireNonNull(ModManager.getModByName("NoRecoil")).isEnabled() && NoRecoil.horizontal.getValueState()) {
                mc.thePlayer.rotationPitch = mc.thePlayer.prevRotationPitch;
            }
            if (Objects.requireNonNull(ModManager.getModByName("NoRecoil")).isEnabled() && NoRecoil.vertical.getValueState()){
                mc.thePlayer.rotationYaw = mc.thePlayer.prevRotationYaw;
            }
        }
    }





    @EventTarget
    public void onTick(EventMotion eventMotion){
        if (eventMotion.isPre()) {
           target = getTarget().get(0);
            addTarget();
            if (target != null) {
                Entity ey = null;
                if (target instanceof EntityPlayer) {
                    ey = this.predict(((EntityPlayer) target), predictmode.isCurrentMode("Auto") ? (int) (getResponseTime(mc.thePlayer.getUniqueID())) :  predict.getValueState().intValue());
                } else {
                    ey = target;
                }
                double rotY = ey.posY;
                if (ey instanceof EntityPlayer) {
                    if (roundToPlace(ey.boundingBox.maxY - ey.boundingBox.minY, 2) == 0.6) {//lying
                        rotY = ey.boundingBox.minY + 0.15;
                    } else if (roundToPlace(ey.boundingBox.maxY - ey.boundingBox.minY, 2) == 1.3) {//squatting
                        rotY = ey.boundingBox.minY + 0.65 + index.getValueState();
                    } else if (roundToPlace(ey.boundingBox.maxY - ey.boundingBox.minY, 2) == 1.8) {//standing
                        rotY = ey.boundingBox.minY + 0.85 + index.getValueState();
                    }
                } else {
                    rotY = ey.posY + ey.getEyeHeight() - index.getValueState();
                }
                double X = Math.abs(target.motionX);
                double Z = Math.abs(target.motionZ);
                targetspeed = X + Z;
                float[] rotations = RotationUtil.getPlayerRotations(mc.thePlayer, ey.posX, rotY, ey.posZ);
                if (shouldAim() && canTarget(target)) {
                    if (!silent.getValueState()){
                        Minecraft.getMinecraft().thePlayer.rotationYaw = rotations[0];
                        Minecraft.getMinecraft().thePlayer.rotationPitch = rotations[1];
                    }else{
                        eventMotion.setYaw(rotations[0]);
                        eventMotion.setPitch(rotations[1]);
                    }
                }
            }

        }
    }


    public Entity predict(EntityPlayer player,  int ticks) {
        if (this.playerPositions.containsKey(player)) {
            List<Vec3> previousPositions = this.playerPositions.get(player);
            if (previousPositions.size() > 1) {
                Vec3 origin = previousPositions.get(0);
                List<Vec3> deltas = new ArrayList<Vec3>();
                Vec3 previous = origin;
                for (Vec3 position : previousPositions) {
                    deltas.add(Vec3.createVectorHelper(position.xCoord - previous.xCoord, position.yCoord - previous.yCoord, position.zCoord - previous.zCoord));
                    previous = position;
                }
                double x = 0.0;
                double y = 0.0;
                double z = 0.0;
                for ( Vec3 delta : deltas) {
                    x += delta.xCoord;
                    y += delta.yCoord;
                    z += delta.zCoord;
                }
                x /= deltas.size();
                y /= deltas.size();
                z /= deltas.size();
                EntityPlayer simulated = new EntityOtherPlayerMP(mc.theWorld, player.getGameProfile());
                simulated.noClip = false;
                simulated.setPosition(player.posX, player.posY, player.posZ);
                for (int i = 0; i < ticks; ++i) {
                    simulated.moveEntity(x, y, z);
                }
                return simulated;
            }
        }
        return player;
    }



    private boolean canTarget(EntityLivingBase entity) {
        NativeMethod.method1();
        if(!RotationUtil.canEntityBeSeen(entity) && !throughwall.getValueState()) {
            return false;
        }
		if(!RotationUtil.isVisibleFOV(entity, fov.getValueState().floatValue())){
			return false;
		}
        if (entity instanceof EntityPlayer && !players.getValueState()) {
            return false;
        }
        if (!Objects.requireNonNull(ModManager.getModByName("NoFriend")).isEnabled() && FriendManager.isFriend(entity.getCommandSenderName())){
            return false;
        }
        if (entity instanceof EntityAnimal && !animal.getValueState()) {
            return false;
        }
        if ((entity instanceof EntitySlime || entity instanceof EntityMob)&& !moster.getValueState()) {
            return false;
        }
        if (entity instanceof EntityBat){
            return false;
        }
        if (entity instanceof EntityVillager && !village.getValueState()) {
            return false;
        }
        if (entity.isInvisible() && !invisible.getValueState()) {
            return false;
        }
        if (entity instanceof EntitySquid){
            return false;
        }
        if (Client.nshowmod){
            if (Objects.requireNonNull(JReflectUtility.getEntityNumber()).isInstance(entity)){
                return false;
            }
        }       
        if (Client.deci){
            if (Objects.requireNonNull(JReflectUtility.getCorpse()).isInstance(entity)){
                return false;
            }
        }
        if ( (entity instanceof EntityCreature) && !otherentity.getValueState() ) {
            return false;
        }
        return entity != mc.thePlayer && entity.isEntityAlive();
    }

    public boolean shouldAim(){
    	if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
			return false;
		}
        if(mc.thePlayer.inventory.getCurrentItem() == null){
            return false;
        }
        return true;
    }

    private void addTarget(){
        for (EntityPlayer player : this.playerPositions.keySet()) {
            if (!mc.theWorld.playerEntities.contains(player)) {
                this.playerPositions.remove(player);
            }
        }
        for (Object o : mc.theWorld.playerEntities) {
            EntityPlayer player = (EntityPlayer)o;
            this.playerPositions.putIfAbsent(player, new ArrayList<Vec3>());
            List<Vec3> previousPositions = this.playerPositions.get(player);
            previousPositions.add(Vec3.createVectorHelper(player.posX, player.posY, player.posZ));
            if (previousPositions.size() > this.buffer) {
                int i = 0;
                for (Vec3 position : new ArrayList<Vec3>(previousPositions)) {
                    if (i < previousPositions.size() - this.buffer) {
                        previousPositions.remove(previousPositions.get(i));
                    }
                    ++i;
                }
            }
        }
    }


    private List<EntityLivingBase> getTarget() {
        List<EntityLivingBase> loaded = new ArrayList<EntityLivingBase>();
        mc.theWorld.loadedEntityList.stream()
                .filter(f -> f instanceof EntityLivingBase)
                .filter(f -> !(f instanceof EntityPlayerSP))
                .filter(f -> canTarget((EntityLivingBase) f))
                .filter(f -> mc.thePlayer.getDistanceToEntity((Entity) f) <= range.getValueState())
                .forEach(ent -> {
        	loaded.add((EntityLivingBase)ent);
        });
        if (sortingMode.isCurrentMode("Distance")) {
            loaded.sort((o1, o2) ->
                    (int) (o1.getDistanceToEntity(mc.thePlayer) - o2.getDistanceToEntity(mc.thePlayer))
            );
        }
        if (sortingMode.isCurrentMode("Health")){
            loaded.sort((o1, o2) ->
                    (int) (o1.getHealth() - o2.getHealth())
            );
        }
        return loaded;
    }


    public int getResponseTime(UUID uuid){
        int responseTime = 0;
        try {
           Field field = mc.getNetHandler().getClass().getDeclaredField(ClientLoader.runtimeObfuscationEnabled ? "field_147310_i" : "playerInfoMap");
           field.setAccessible(true);
           Map<?,?> map = (Map<?,?>) field.get(mc.getNetHandler());
           responseTime = (int) Math.ceil((double)((GuiPlayerInfo)map.get(uuid)).responseTime / 50.0D);
           return responseTime;
        }catch (Exception e){

        }
        return responseTime;
    }

}
