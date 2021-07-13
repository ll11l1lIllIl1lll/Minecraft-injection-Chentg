package cn.snowflake.rose.mod.mods.WORLD;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.mcutil.EnumFacing;
import cn.snowflake.rose.utils.path.AStarNode;
import cn.snowflake.rose.utils.path.AStarPath;
import cn.snowflake.rose.utils.time.WaitTimer;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Slot;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class MineBot extends Module {

    private EnumFacing facing = EnumFacing.EAST;
    private boolean skipCheck = false;
    private WaitTimer skipCheckTimer = new WaitTimer();

    public MineBot() {
        super("MineBot", "Mine Bot", Category.WORLD );
        setChinesename("\u6316\u77ff\u673a\u5668\u4eba");
    }

    @Override
    public String getDescription() {
        return "挖矿机器人";
    }

    private boolean isInventoryFull() {
        int id = 36;
        while (id < 45) {
            Slot currentSlot = this.mc.thePlayer.inventoryContainer.getSlot(id);
            if (currentSlot == null) {
                return false;
            }
        }
        return true;
    }
    public static ArrayList<AStarNode> path = new ArrayList();
    public static AStarPath pathFinder;

    @EventTarget
    public void onUpdate(EventUpdate event) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Vec3> clazz = (Class<Vec3>) Class.forName("net.minecraft.util.Vec3");
        Constructor vec3 = clazz.getDeclaredConstructor(double.class,double.class,double.class);
        vec3.setAccessible(true);
        BlockPos toFace = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),mc.thePlayer.posZ).offset(facing);
        boolean foundItem = false;
        boolean foundOre = false;
        
//        if(skipCheckTimer.hasTimeElapsed(5000, false)) {
//            Block under = getBlockRelativeToEntity(mc.thePlayer, -0.01d);
//            Block stateUnder = getBlock(getBlockPosRelativeToEntity(mc.thePlayer, -1d));
//            EntityItem theItem = null;
//            for (EntityItem item : getNearbyItems(5)) {
//                if (mc.thePlayer.canEntityBeSeen(item) && item.ticksExisted > 20 && item.ticksExisted < 150) {
//                    foundItem = true;
//                    theItem = item;
//                }
//            }
//            if (foundItem && !foundOre) {
//                faceEntity(theItem);
//                mc.thePlayer.moveFlying(0, 1f, 0.1f);
//                if (theItem.posY > mc.thePlayer.posY && mc.thePlayer.onGround) {
//                    mc.thePlayer.jump();
//                }
//                if (theItem.posY < mc.thePlayer.posY && mc.thePlayer.onGround) {
//                    mc.thePlayer.moveFlying(0, 1f, 0.1f);
//                }
//
//                }else {
//
//                for (int x = -6; x <= 6; x++) {
//                    for (int y = -6; y <= 6; y++) {
//                        for (int z = -6; z <= 6; z++) {
//                            BlockPos blockPos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y,mc.thePlayer.posZ + z);
//                            Block block = getBlock(blockPos);
//                            Block state = getBlock(blockPos);
//
//                            if (state.getMaterial() == Material.air) {
//                                continue;
//                            }
//                            if (block instanceof BlockLiquid || block.getMaterial() == Material.rock) {
//                                MovingObjectPosition trace0 = mc.theWorld.func_147447_a(
//                                        (Vec3)vec3.newInstance(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ),
//                                        (Vec3)vec3.newInstance(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5), true,
//                                        false, true);
//
//                                BlockPos tp = new BlockPos(trace0.blockX,trace0.blockY,trace0.blockZ);
//                                if (tp == null) {
//                                    continue;
//                                }
//                                BlockPos blockPosTrace = tp;
//                                Block blockTrace = getBlock(blockPosTrace);
//                                if(blockTrace instanceof BlockLiquid || block.getMaterial() == Material.rock) {
//                                    facing = facing.getOpposite();
//                                    toFace = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),mc.thePlayer.posZ).offset(facing);
//                                    if (isBlockPosAir(toFace)) {
//                                        toFace = toFace.east();
//                                    }
//                                    skipCheckTimer.reset();
//                                    break;
//                                }
//                            }
//
//                            if (block instanceof BlockOre || block instanceof BlockRedstoneOre) {
//                                MovingObjectPosition trace0 = mc.theWorld.func_147447_a(
//                                        (Vec3)vec3.newInstance(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ),
//                                        (Vec3)vec3.newInstance(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5), true,
//                                        false, true);
//                                BlockPos tp = new BlockPos(trace0.blockX,trace0.blockY,trace0.blockZ);
//
//                                if (tp == null) {
//                                    continue;
//                                }
//                                BlockPos blockPosTrace = tp;
//                                Block blockTrace = getBlock(blockPosTrace);
//                                double dist = getVec3(blockPos).distanceTo(getVec3(blockPosTrace));
//                                double xx = mc.thePlayer.posX - (blockPosTrace.getX() + 0.5);
//                                double yy = mc.thePlayer.posY - (blockPosTrace.getY() + 0.5 - mc.thePlayer.getEyeHeight());
//                                double zz = mc.thePlayer.posZ - (blockPosTrace.getZ() + 0.5);
//                                double dist0 = Math.sqrt(x * x + y * y + z * z);
//                                if(dist0 >= 1.5) {
//                                    float yaw = getFacePos(getVec3(blockPosTrace))[0];
//                                    yaw = normalizeAngle(yaw);
//                                    if (yaw == 45) {
//                                        continue;
//                                    }
//                                    if (yaw == -45) {
//                                        continue;
//                                    }
//                                    if (yaw == 135) {
//                                        continue;
//                                    }
//                                    if (yaw == -135) {
//                                        continue;
//                                    }
//                                }
//                                if (dist <= 0) {
//                                    toFace = blockPosTrace;
//                                    foundOre = true;
//                                    break;
//                                }
//                            }
//
//                        }
//                    }
//                }
//                if (foundOre) {
//                    faceBlock(toFace);
//                    if (!isBlockPosAir(toFace) && mc.thePlayer.getDistance(toFace.x,toFace.y,toFace.z) < 4) {
//                        mineBlock(toFace);
//                    }
//                }else {
//
//                    if (mc.thePlayer.posY > 100) {
//                        if (stateUnder.getMaterial() != Material.air) {
//                            mineBlockUnderPlayer();
//                        }else if (mc.thePlayer.onGround) {
//                            mc.thePlayer.moveFlying(0, 0.5f, 0.1f);
//                        }
//                    }else {
//                        if (isBlockPosAir(toFace)) {
//                            toFace = toFace.down();
//                            if (isBlockPosSafe(toFace)) {
//                                if (isBlockPosAir(toFace) && mc.thePlayer.onGround) {
//                                    mc.thePlayer.moveFlying(0, 1f, 0.1f);
//                                }
//                            }else {
//                                facing = facing.fromAngle(mc.thePlayer.rotationYaw + 90);
//                                toFace = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
//                                        mc.thePlayer.posZ).offset(facing);
//                                if (!isBlockPosSafe(toFace)) {
//                                    facing = facing.fromAngle(mc.thePlayer.rotationYaw - 90);
//                                    toFace = new BlockPos(mc.thePlayer.posX,
//                                            mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ)
//                                            .offset(facing);
//                                    if (!isBlockPosSafe(toFace.down())) {
//                                        facing = facing.fromAngle(mc.thePlayer.rotationYaw + 180);
//                                        toFace = new BlockPos(mc.thePlayer.posX,
//                                                mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ)
//                                                .offset(facing);
//                                    }
//                                }
//                            }
//                        }
//                        if (isBlockPosAir(toFace)) { //Stop bobbing (up and down is irritating)
//                            toFace = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
//                                    mc.thePlayer.posZ).offset(facing);
//                        }
//                        faceBlock(toFace);
//                        if (!isBlockPosAir(toFace)) {
//                            mineBlock(toFace);
//                        }
//                    }
//                }
//            }
//        }else {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.moveFlying(0, 1f, 0.1f);
            }
            if (isBlockPosAir(toFace)) {
                toFace = toFace.down();
            }
            faceBlock(toFace);
            if (!isBlockPosAir(toFace)) {
                mineBlock(toFace);
            }
//        }
    }

    @Override
    public void onEnable() {
        facing = EnumFacing.fromAngle(mc.thePlayer.rotationYaw);
        skipCheckTimer.time = 5000;
        super.onEnable();
    }
    public Block getBlockRelativeToEntity(Entity en, double d) {
        return getBlock(new BlockPos(en.posX, en.posY + d, en.posZ));
    }

    public Block getBlock(BlockPos pos) {
        return mc.theWorld.getBlock(pos.x,pos.y,pos.z);
    }

    public void faceBlock(BlockPos blockPos) throws InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        facePos(getVec3(blockPos));
    }


    public static ArrayList<EntityItem> getNearbyItems(int range) {
        ArrayList<EntityItem> eList = new ArrayList<EntityItem>();
        for (Object o : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
            if (!(o instanceof EntityItem)) {
                continue;
            }
            EntityItem e = (EntityItem) o;
            if (Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e) >= range) {
                continue;
            }

            eList.add(e);
        }
        return eList;
    }

    public static float[] getFacePos(Vec3 vec) {
        double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = vec.yCoord + 0.5
                - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] {
                Minecraft.getMinecraft().thePlayer.rotationYaw
                        + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
                Minecraft.getMinecraft().thePlayer.rotationPitch
                        + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }

    public double normalizeAngle(double angle) {
        return (angle + 360) % 360;
    }

    public float normalizeAngle(float angle) {
        return (angle + 360) % 360;
    }
    public BlockPos getBlockPosRelativeToEntity(Entity en, double d) {
        return new BlockPos(en.posX, en.posY + d, en.posZ);
    }

    public boolean isBlockPosAir(BlockPos blockPos) {
        return mc.theWorld.getBlock(blockPos.x,blockPos.y,blockPos.z).getMaterial() == Material.air;
    }
    public Vec3 getVec3(BlockPos blockPos) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        Class<Vec3> clazz = (Class<Vec3>) Class.forName("net.minecraft.util.Vec3");
        Constructor vec3 = clazz.getDeclaredConstructor(double.class,double.class,double.class);
        vec3.setAccessible(true);
        return (Vec3) vec3.newInstance(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    public void faceEntity(Entity en) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Vec3> clazz = (Class<Vec3>) Class.forName("net.minecraft.util.Vec3");
        Constructor vec3 = clazz.getDeclaredConstructor(double.class,double.class,double.class);
        vec3.setAccessible(true);
        facePos((Vec3) vec3.newInstance(en.posX - 0.5, en.posY + (en.getEyeHeight() - en.height / 1.5), en.posZ - 0.5));
    }

    public void facePos(Vec3 vec) {
        double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        double diffY = vec.yCoord + 0.5
                - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        Minecraft.getMinecraft().thePlayer.rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw
                + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw);
        Minecraft.getMinecraft().thePlayer.rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch
                + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch);
    }

    public void mineBlockUnderPlayer() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        BlockPos pos = getBlockPosRelativeToEntity(mc.thePlayer, -1);
        mineBlock(pos);
    }

    public void mineBlock(BlockPos pos) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Block block = getBlock(pos);

        faceBlock(pos);
        mc.thePlayer.swingItem();
        mc.playerController.onPlayerDamageBlock(pos.x,pos.y,pos.z, 1);
        this.mc.thePlayer.swingItem();
    }



    public boolean isBlockPosSafe(BlockPos pos) {
        return checkBlockPos(pos, 10);
    }

    public boolean checkBlockPos(BlockPos pos, int checkHeight) {
        boolean safe = true;
        boolean blockInWay = false;
        int fallDist = 0;
        if(getBlock(pos).getMaterial() == Material.lava
                || getBlock(pos).getMaterial() == Material.water) {
            return false;
        }
        if(getBlock(pos.up(1)).getMaterial() == Material.lava
                || getBlock(pos.up(1)).getMaterial() == Material.water) {
            return false;
        }
        if(getBlock(pos.up(2)).getMaterial() == Material.lava
                || getBlock(pos.up(2)).getMaterial() == Material.water) {
            return false;
        }
        for(int i = 1; i < checkHeight + 1; i++) {
            BlockPos pos2 = pos.down(i);
            Block block = getBlock(pos2);
            if(block.getMaterial() == Material.air) {
                if(!blockInWay) {
                    fallDist++;
                }
                continue;
            }
            if(!blockInWay) {
                if(block.getMaterial() == Material.lava
                        || block.getMaterial() == Material.water) {
                    return false;
                }
            }
            if(!blockInWay) {
                blockInWay = true;
            }
        }
        if(fallDist > 2) {
            safe = false;
        }
        return safe;
    }

}

