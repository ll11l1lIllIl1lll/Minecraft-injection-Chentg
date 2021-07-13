package cn.snowflake.rose.mod.mods.COMBAT;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.events.impl.EventRender3D;
import cn.snowflake.rose.management.FriendManager;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.client.RotationUtil;
import cn.snowflake.rose.utils.math.Vec3Util;
import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.path.AStarCustomPathFinder;
import cn.snowflake.rose.utils.render.ColorUtil;
import cn.snowflake.rose.utils.render.RenderUtil;
import cn.snowflake.rose.utils.time.TimeHelper;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.opengl.GL11.*;

public class TPAura extends Module {
    public Value<String> Mode;
    private Value<Double> MAXT;
    private Value<Double> RANGE;
    private Value<Double> CPS;
    public Value<Boolean> NOSWING;
    public Value<Boolean> Tracers;
    public Value<Boolean> PATHESP;
    public Value<Boolean> PLAYERS;
    public Value<Boolean> ANIMALS;
    public Value<Boolean> INVISIBLES;
    public Value<Boolean> MOB;
    public Value<Boolean> customnpcs;
    private Value<Boolean> otherentity;
    private Value<Boolean> block;
    private Value<Boolean> onground;
    private Value<Boolean> wall;
    
    private ArrayList<Vec3Util> path;
    private List<Vec3Util>[] test;
    private List<EntityLivingBase> targets;
    private TimeHelper cps;
    public static TimeHelper timer;
    public static boolean canReach;
    private double dashDistance;
    
    public TPAura() {
        super("TPAura","TP Aura", Category.COMBAT);
        this.dashDistance = 5.0;
        this.Mode = new Value<String>("Tpaura", "Mode", 0);
        this.wall = new Value<Boolean>("TPAura_ThroughWall", true);
        this.Tracers = new Value<Boolean>("Tpaura_Tracers", false);
        this.MOB = new Value<Boolean>("Tpaura_Mob", false);
        this.block = new Value<Boolean>("TPAura_AutoBlock", true);
        this.onground = new Value<Boolean>("TPAura_OnGround", false);
        this.PATHESP = new Value<Boolean>("Tpaura_Path", true);
        this.PLAYERS = new Value<Boolean>("Tpaura_Players", true);
        this.ANIMALS = new Value<Boolean>("Tpaura_Animals", false);
        this.INVISIBLES = new Value<Boolean>("Tpaura_Invisible", true);
        this.MAXT = new Value<Double>("Tpaura_Maxtarget", 5.0, 1.0, 50.0, 1.0);
        this.RANGE = new Value<Double>("Tpaura_Reach", 30.0, 8.0, 100.0, 2.0);
        this.CPS = new Value<Double>("Tpaura_Cps", 8.0, 1.0, 20.0, 1.0);
        this.NOSWING = new Value<Boolean>("TPAura_NoSwing", false);
        this.otherentity = new Value<Boolean>("TPAura_OtherEntity", true);
        this.customnpcs = new Value<Boolean>("TPAura_CustomNPC", true);
        this.path = new ArrayList<Vec3Util>();
        this.test = (List<Vec3Util>[])new ArrayList[50];
        this.targets = new CopyOnWriteArrayList<EntityLivingBase>();
        this.cps = new TimeHelper();
        timer = new TimeHelper();
        this.Mode.mode.add("Vanilla");
        setChinesename("\u767e\u7c73\u957f\u5200");
    }

    @Override
    public String getDescription() {
        return "百米大刀!";
    }

    @Override
    public void onEnable() {
        TPAura.timer.reset();

    }

    @EventTarget
    public void onPre(EventMotion em) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (this.Mode.isCurrentMode("Vanilla")) {
            this.setDisplayName("Vanilla");
        }

        int maxtTargets = this.MAXT.getValueState().intValue();
        int delayValue = 20 / this.CPS.getValueState().intValue() * 50;

        this.targets = this.getTargets();
        if (targets !=null) {
            if (block.getValueState() && !mc.thePlayer.isBlocking() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                startAutoBlock();
            }
        }else {
            if (block.getValueState() && mc.thePlayer.isBlocking()&& mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                stopAutoBlock();
            }
        }
        if (this.cps.isDelayComplete(delayValue) && this.targets.size() > 0 && (!onground.getValueState() || mc.thePlayer.onGround)) {
            this.test = (List<Vec3Util>[])new ArrayList[50];
            for (int i = 0; i < (Math.min(this.targets.size(), maxtTargets)); ++i) {
                EntityLivingBase T = this.targets.get(i);
                Vec3Util topFrom = new Vec3Util(mc.thePlayer.posX, mc.thePlayer.posY - 2.5, mc.thePlayer.posZ);
                Vec3Util to = new Vec3Util(T.posX, T.posY - 2.5, T.posZ);
                this.path = this.computePath(topFrom, to);

                this.test[i] = this.path;
                for (Vec3Util pathElm : this.path) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), mc.thePlayer.boundingBox.minY, pathElm.getY(), pathElm.getZ(), true));
                }
                if (block.getValueState() && mc.thePlayer.isBlocking()&& mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                    stopAutoBlock();
                }
                if(!NOSWING.getValueState()) {
                    mc.thePlayer.swingItem();
                }
                mc.playerController.attackEntity((EntityPlayer)mc.thePlayer, (Entity)T);
                if (block.getValueState() && !mc.thePlayer.isBlocking() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                    startAutoBlock();
                }
                Collections.reverse(this.path);
                for (Vec3Util pathElm : this.path) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), mc.thePlayer.boundingBox.minY, pathElm.getY(), pathElm.getZ(), true));
                }

            }
            this.cps.reset();
        }
    }

    @Override
    public void onDisable() {
        stopAutoBlock();
        super.onDisable();
    }
    private void startAutoBlock() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(),true);

        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
    }
    private void stopAutoBlock() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(),false);

        mc.playerController.onStoppedUsingItem(mc.thePlayer);

    }
    @EventTarget
    public void on3D(EventRender3D er) {
        int maxtTargets = MAXT.getValueState().intValue();
////        targets.size() == 0) &&
//        if (!(ESP.getValueState())) {
//            if (targets.size() > 0) {
//                for (int i = 0; i < (targets.size() > maxtTargets ? maxtTargets : targets.size()); i++) {
//                    int color = targets.get(i).hurtResistantTime > 15 ? Colors.getColor(new Color(255, 70, 70, 100)) : Colors.getColor(new Color(0, 70, 255, 100));
//                    drawESP(targets.get(i), color);
//                }
//            }
//        }
        if (!path.isEmpty() && PATHESP.getValueState()) {
            for (int i = 0; i < targets.size(); i++) {
                try {
                    if (test != null)

                    if (Tracers.getValueState()) {
                        glPushMatrix();
                        glDisable(GL_TEXTURE_2D);
                        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                        glEnable(GL_LINE_SMOOTH);
                        glEnable(GL_BLEND);
                        glDisable(GL_DEPTH_TEST);
                        mc.entityRenderer.disableLightmap(1);
                        glBegin(GL_LINE_STRIP);
                        RenderUtil.glColor(ColorUtil.getClickGUIColor().getRGB());
                        double renderPosX = RenderManager.instance.viewerPosX;
                        double renderPosY = RenderManager.instance.viewerPosY;
                        double renderPosZ = RenderManager.instance.viewerPosZ;
                        for(Vec3Util pos : test[i]) {
                            glVertex3d(pos.getX() - renderPosX, pos.getY()  - renderPosY, pos.getZ() - renderPosZ);
                        }
                        glColor4d(1, 1, 1, 1);
                        glEnd();
                        glEnable(GL_DEPTH_TEST);
                        glDisable(GL_LINE_SMOOTH);
                        glDisable(GL_BLEND);
                        glEnable(GL_TEXTURE_2D);
                        glPopMatrix();
                    }
                } catch (Exception e) {

                }
            }

            if (cps.check(1000)) {
                test = new ArrayList[50];
                path.clear();
            }
        }
    }

    private ArrayList<Vec3Util> computePath(Vec3Util topFrom, Vec3Util to) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!this.canPassThrow(new BlockPos(topFrom.toVec3()))) {
            topFrom = topFrom.addVector(0.0, 1.0, 0.0);
        }
        AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        Vec3Util lastLoc = null;
        Vec3Util lastDashLoc = null;
        ArrayList<Vec3Util> path = new ArrayList<Vec3Util>();
        ArrayList<Vec3Util> pathFinderPath = pathfinder.getPath();
        for (Vec3Util pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            }
            else {
                boolean canContinue = true;
                Label_0356: {
                    if (pathElm.squareDistanceTo(lastDashLoc) > this.dashDistance * this.dashDistance) {
                        canContinue = false;
                    }else {
                        double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                        double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                        double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                        double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                        double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                        double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                        for (int x = (int)smallX; x <= bigX; ++x) {
                            for (int y = (int)smallY; y <= bigY; ++y) {
                                for (int z = (int)smallZ; z <= bigZ; ++z) {
                                    if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, onground.getValueState())) {
                                        canContinue = false;
                                        break Label_0356;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }

    private boolean canPassThrow(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlock(pos.getX(), pos.getY(), pos.getZ());
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }

    private boolean canTarget(Entity entity) {
        if (!(entity instanceof EntityMob || entity instanceof EntityAnimal)
                && entity instanceof EntityCreature
                && !otherentity.getValueState()) {
            return false;
        }
        if (entity instanceof EntityBat){
            return false;
        }
        if (Client.customnpcs) {
            if (Objects.requireNonNull(JReflectUtility.getNPCEntity()).isInstance(entity) ){
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

        if (entity instanceof EntityPlayer && !PLAYERS.getValueState()) {
            return false;
        }
        if (entity instanceof EntityAnimal && !ANIMALS.getValueState()) {
            return false;
        }
        if (entity instanceof EntityMob && !MOB.getValueState()) {
            return false;
        }
        if (!ModManager.getModByName("NoFriend").isEnabled() && FriendManager.isFriend(entity.getCommandSenderName())){
            return false;
        }
        if(!mc.thePlayer.canEntityBeSeen(entity) && !wall.getValueState()) {
            return false;
        }
        if (entity.isInvisible() && !INVISIBLES.getValueState()) {
            return false;
        }
        return entity != mc.thePlayer && entity.isEntityAlive() && mc.thePlayer.getDistanceToEntity(entity) <= RANGE.getValueState();
    }
    private List getTargets() {
        try {
            ArrayList<EntityLivingBase> targets = new ArrayList();
            for(Object entity : mc.theWorld.loadedEntityList) {
                if(canTarget((Entity) entity) && entity instanceof EntityLivingBase) {
                    targets.add((EntityLivingBase) entity);
                }
            }
            targets.sort((o1, o2) -> {
                float[] rot1 = RotationUtil.getRotations(o1);
                float[] rot2 = RotationUtil.getRotations(o2);
                return (int)(mc.thePlayer.rotationYaw - rot1[0] - (mc.thePlayer.rotationYaw - rot2[0]));
            });
            return targets;
        } catch (Exception e) {
            return null;
        }
    }

}