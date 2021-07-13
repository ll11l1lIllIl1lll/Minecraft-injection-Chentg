package cn.snowflake.rose.utils.path;


import cn.snowflake.rose.utils.mcutil.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Random;

public class JigsawReach
{

    private static Minecraft mc = Minecraft.getMinecraft();
    private static Random rand = new Random();


    public static ArrayList<Integer> blackList = new ArrayList<Integer>();

    static double x;
    static double y;
    static double z;
    static double xPreEn;
    static double yPreEn;
    static double zPreEn;
    static double xPre;
    static double yPre;
    static double zPre;

//	static ArrayList<Vec3> positions = new ArrayList<Vec3>();
//	static ArrayList<Vec3> positionsBack = new ArrayList<Vec3>();

    private static void preInfiniteReach(double range, double maxXZTP, double maxYTP,
                                         ArrayList<Vec3> positionsBack, ArrayList<Vec3> positions, Vec3 targetPos, boolean tpStraight, boolean up, boolean attack, boolean tpUpOneBlock, boolean sneaking) {

    }

    private static void postInfiniteReach() {

    }

    public static boolean infiniteReach(double range, double maxXZTP, double maxYTP,
                                        ArrayList<Vec3> positionsBack, ArrayList<Vec3> positions, EntityLivingBase en) {

        int ind = 0;
        xPreEn = en.posX;
        yPreEn = en.posY + 2;
        zPreEn = en.posZ;
        xPre = mc.thePlayer.posX;
        yPre = mc.thePlayer.posY + 2;
        zPre = mc.thePlayer.posZ;
        boolean attack = true;
        boolean up = false;
        boolean tpUpOneBlock = false;

        // If something in the way
        boolean hit = false;
        boolean tpStraight = false;

        boolean sneaking = mc.thePlayer.isSneaking();

        positions.clear();
        positionsBack.clear();

        //preInfiniteReach(range, maxXZTP, maxYTP, positionsBack, positions, Vec3.createVectorHelper(en.posX, en.posY, en.posZ), tpStraight, up, attack, tpUpOneBlock, sneaking);
        double step = maxXZTP / range;
        int steps = 0;
        for (int i = 0; i < range; i++) {
            steps++;
            if (maxXZTP * steps > range) {
                break;
            }
        }
        MovingObjectPosition rayTrace = null;
        MovingObjectPosition rayTrace1 = null;
        MovingObjectPosition rayTraceCarpet = null;
        if ((rayTraceWide(Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + 2, mc.thePlayer.posZ),
                Vec3.createVectorHelper(en.posX, en.posY, en.posZ), false, false, true))
                || (rayTrace1 = rayTracePos(
                Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + 2 + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ),
                Vec3.createVectorHelper(en.posX, en.posY + 2 + mc.thePlayer.getEyeHeight(), en.posZ), false, false,
                true)) != null) {
            if ((rayTrace = rayTracePos(Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + 2, mc.thePlayer.posZ),
                    Vec3.createVectorHelper(en.posX, mc.thePlayer.posY + 2, en.posZ), false, false, true)) != null
                    || (rayTrace1 = rayTracePos(
                    Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY +2  + mc.thePlayer.getEyeHeight(),
                            mc.thePlayer.posZ),
                    Vec3.createVectorHelper(en.posX, mc.thePlayer.posY +2  + mc.thePlayer.getEyeHeight(), en.posZ), false, false,
                    true)) != null) {
                MovingObjectPosition trace = null;
                if (rayTrace == null) {
                    trace = rayTrace1;
                }
                if (rayTrace1 == null) {
                    trace = rayTrace;
                }
                if (trace == null) {
                     y = mc.thePlayer.posY + 2;
                     yPreEn = mc.thePlayer.posY + 2;
                } else {

                    if (trace.blockX != 0 && trace.blockY != 0 && trace.blockZ != 0) {
                        boolean fence = false;
                        BlockPos target = new BlockPos(trace.blockX,trace.blockY,trace.blockZ);
                        up = true;
                        y = target.up().getY();
                        yPreEn = target.up().getY();
                        Block lastBlock = null;
                        Boolean found = false;
                        for (int i = 0; i < maxYTP; i++) {
                            MovingObjectPosition tr = rayTracePos(
                                    Vec3.createVectorHelper(mc.thePlayer.posX, target.getY() + i, mc.thePlayer.posZ),
                                    Vec3.createVectorHelper(en.posX, target.getY() + i, en.posZ), false, false, true);
                            if (tr == null) {
                                continue;
                            }
                            if (tr.blockX == 0 && tr.blockY == 0 && tr.blockZ == 0) {
                                continue;
                            }

                            BlockPos blockPos = new BlockPos(tr.blockX,tr.blockY + 2,tr.blockZ);
                            Block block = getBlock(blockPos);
                            if (block.getMaterial() != Material.air) {
                                lastBlock = block;
                                continue;
                            }
                            fence = lastBlock instanceof BlockFence;
                            y = target.getY() + i;
                            yPreEn = target.getY() + i;
                            if (fence) {
                                y += 1;
                                yPreEn += 1;
                                if (i + 1 > maxYTP) {
                                    found = false;
                                    break;
                                }
                            }
                            found = true;
                            break;
                        }
                        double difX = mc.thePlayer.posX - xPreEn;
                        double difZ = mc.thePlayer.posZ - zPreEn;
                        double divider = step * 0;
                        if (!found) {
                            attack = false;
                            return false;
                        }
                    } else {
                        attack = false;
                        return false;
                    }
                }
            } else {
                MovingObjectPosition ent = rayTracePos(
                        Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + 2, mc.thePlayer.posZ),
                        Vec3.createVectorHelper(en.posX, en.posY, en.posZ), false, false, false);
                if (ent != null && ent.entityHit == null) {
                    y = mc.thePlayer.posY + 2;
                    yPreEn = mc.thePlayer.posY +2;
                } else {
                    y = mc.thePlayer.posY +2 ;
                    yPreEn = en.posY;
                }

            }
        }
        if (!attack) {
            return false;
        }
        if (sneaking) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 1));
        }
        for (int i = 0; i < steps; i++) {
            ind++;
            if (i == 1 && up) {
                x = mc.thePlayer.posX;
                y = yPreEn;
                z = mc.thePlayer.posZ;
                sendPacket(false, positionsBack, positions);
            }
            if (i != steps - 1) {
                {
                    double difX = mc.thePlayer.posX - xPreEn;
                    double difY = mc.thePlayer.posY + 2 - yPreEn;
                    double difZ = mc.thePlayer.posZ - zPreEn;
                    double divider = step * i;
                    x = mc.thePlayer.posX - difX * divider;
                    y = mc.thePlayer.posY + 2 - difY * (up ? 1 : divider);
                    z = mc.thePlayer.posZ - difZ * divider;
                }
                sendPacket(false, positionsBack, positions);
            } else {
                // if last teleport
                {
                    double difX = mc.thePlayer.posX - xPreEn;
                    double difY = mc.thePlayer.posY + 2 - yPreEn;
                    double difZ = mc.thePlayer.posZ - zPreEn;
                    double divider = step * i;
                    x = mc.thePlayer.posX - difX * divider;
                    y = mc.thePlayer.posY + 2 - difY * (up ? 1 : divider);
                    z = mc.thePlayer.posZ - difZ * divider;
                }
                sendPacket(false, positionsBack, positions);
                double xDist = x - xPreEn;
                double zDist = z - zPreEn;
                double yDist = y - en.posY;
                double dist = Math.sqrt(xDist * xDist + zDist * zDist);
                if (dist > 4) {
                    x = xPreEn;
                    y = yPreEn;
                    z = zPreEn;
                    sendPacket(false, positionsBack, positions);
                } else if (dist > 0.05 && up) {
                    x = xPreEn;
                    y = yPreEn;
                    z = zPreEn;
                    sendPacket(false, positionsBack, positions);
                }
                if (Math.abs(yDist) < maxYTP && mc.thePlayer.getDistanceToEntity(en) >= 4) {
                    x = xPreEn;
                    y = en.posY;
                    z = zPreEn;
                    sendPacket(false, positionsBack, positions);
                    attackInf(en);
                } else {
                    attack = false;
                }
            }
        }

        // Go back!
        for (int i = positions.size() - 2; i > -1; i--) {
            {
                x = positions.get(i).xCoord;
                y = positions.get(i).yCoord;
                z = positions.get(i).zCoord;
            }
            sendPacket(false, positionsBack, positions);
        }
        x = mc.thePlayer.posX;
        y = mc.thePlayer.posY + 2;
        z = mc.thePlayer.posZ;
        sendPacket(false, positionsBack, positions);
        if (!attack) {
            if (sneaking) {
                mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 0));
            }
            positions.clear();
            positionsBack.clear();
            return false;
        }
        if (sneaking) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 0));
        }
        return true;
    }

    public static boolean infiniteReach(double range, double maxXZTP, double maxYTP,
                                        ArrayList<Vec3> positionsBack, ArrayList<Vec3> positions, BlockPos targetBlockPos) {
        positions.clear();
        positionsBack.clear();
        boolean tpUpOneBlock = false;
        double step = maxXZTP / range;
        int steps = 0;
        for (int i = 0; i < range; i++) {
            steps++;
            // Jigsaw.chatMessage(maxXZTP * steps);
            if (maxXZTP * steps > range) {
                break;
            }
        }
        int ind = 0;
        double posX = ((double)targetBlockPos.getX()) + 0.5;
        double posY = ((double)targetBlockPos.getY()) + 1.0;
        double posZ = ((double)targetBlockPos.getZ()) + 0.5;
        xPreEn = posX;
        yPreEn = posY;
        zPreEn = posZ;
        xPre = mc.thePlayer.posX;
        yPre = mc.thePlayer.posY;
        zPre = mc.thePlayer.posZ;
        boolean attack = true;
        boolean up = false;

        // If something in the way
        boolean hit = false;
        boolean tpStraight = false;
        boolean sneaking = mc.thePlayer.isSneaking();
        MovingObjectPosition rayTrace = null;
        MovingObjectPosition rayTrace1 = null;
        MovingObjectPosition rayTraceCarpet = null;
        if ((rayTraceWide(Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                Vec3.createVectorHelper(posX, posY, posZ), false, false, true))
                || (rayTrace1 = rayTracePos(
                Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ),
                Vec3.createVectorHelper(posX, posY + mc.thePlayer.getEyeHeight(), posZ), false, false,
                true)) != null) {
            if ((rayTrace = rayTracePos(Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                    Vec3.createVectorHelper(posX, mc.thePlayer.posY, posZ), false, false, true)) != null
                    || (rayTrace1 = rayTracePos(
                    Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                            mc.thePlayer.posZ),
                    Vec3.createVectorHelper(posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), posZ), false, false,
                    true)) != null) {
                MovingObjectPosition trace = null;
                if (rayTrace == null) {
                    trace = rayTrace1;
                }
                if (rayTrace1 == null) {
                    trace = rayTrace;
                }
                if (trace == null) {
                    // y = mc.thePlayer.posY;
                    // yPreEn = mc.thePlayer.posY;
                } else {
                    if (trace.blockX != 0 && trace.blockY != 0 && trace.blockZ != 0) {
                        boolean fence = false;
                        BlockPos target = new BlockPos(trace.blockX,trace.blockY,trace.blockZ);
                        // positions.add(BlockTools.getVec3(target));
                        up = true;
                        y = target.up().getY();
                        yPreEn = target.up().getY();
                        Block lastBlock = null;
                        Boolean found = false;
                        for (int i = 0; i < maxYTP; i++) {
                            MovingObjectPosition tr = rayTracePos(
                                    Vec3.createVectorHelper(mc.thePlayer.posX, target.getY() + i, mc.thePlayer.posZ),
                                    Vec3.createVectorHelper(posX, target.getY() + i, posZ), false, false, true);
                            if (tr == null) {
                                continue;
                            }
                            if (tr.blockX == 0 && tr.blockY == 0 && tr.blockZ == 0) {
                                continue;
                            }

                            BlockPos blockPos = new BlockPos(tr.blockX,tr.blockY,tr.blockZ);
                            Block block = getBlock(blockPos);
                            if (block.getMaterial() != Material.air) {
                                lastBlock = block;
                                continue;
                            }
                            fence = lastBlock instanceof BlockFence;
                            y = target.getY() + i;
                            yPreEn = target.getY() + i;
                            if (fence) {
                                y += 1;
                                yPreEn += 1;
                                if (i + 1 > maxYTP) {
                                    found = false;
                                    break;
                                }
                            }
                            found = true;
                            break;
                        }
                        double difX = mc.thePlayer.posX - xPreEn;
                        double difZ = mc.thePlayer.posZ - zPreEn;
                        double divider = step * 0;
                        if (!found) {
                            attack = false;
                            return false;
                        }
                    } else {
                        attack = false;
                        return false;
                    }
                }
            } else {
                MovingObjectPosition ent = rayTracePos(
                        Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                        Vec3.createVectorHelper(posX, posY, posZ), false, false, false);
                if (ent != null && ent.entityHit == null) {
                    y = mc.thePlayer.posY;
                    yPreEn = mc.thePlayer.posY;
                } else {
                    y = mc.thePlayer.posY;
                    yPreEn = posY;
                }

            }
        }
        if (!attack) {
            return false;
        }
        if (sneaking) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 1));
        }
        for (int i = 0; i < steps; i++) {
            ind++;
            if (i == 1 && up) {
                x = mc.thePlayer.posX;
                y = yPreEn;
                z = mc.thePlayer.posZ;
//                sendPacket(false, positionsBack, positions);
            }
            if (i != steps - 1) {
                {
                    double difX = mc.thePlayer.posX - xPreEn;
                    double difY = mc.thePlayer.posY - yPreEn;
                    double difZ = mc.thePlayer.posZ - zPreEn;
                    double divider = step * i;
                    x = mc.thePlayer.posX - difX * divider;
                    y = mc.thePlayer.posY - difY * (up ? 1 : divider);
                    z = mc.thePlayer.posZ - difZ * divider;
                }
//                sendPacket(false, positionsBack, positions);
            } else {
                // if last teleport
                {
                    double difX = mc.thePlayer.posX - xPreEn;
                    double difY = mc.thePlayer.posY - yPreEn;
                    double difZ = mc.thePlayer.posZ - zPreEn;
                    double divider = step * i;
                    x = mc.thePlayer.posX - difX * divider;
                    y = mc.thePlayer.posY - difY * (up ? 1 : divider);
                    z = mc.thePlayer.posZ - difZ * divider;
                }
//                sendPacket(false, positionsBack, positions);
                double xDist = x - xPreEn;
                double zDist = z - zPreEn;
                double yDist = y - posY;
                double dist = Math.sqrt(xDist * xDist + zDist * zDist);
                if (dist > 4) {
                    x = xPreEn;
                    y = yPreEn;
                    z = zPreEn;
//                    sendPacket(false, positionsBack, positions);
                } else if (dist > 0.05 && up) {
                    x = xPreEn;
                    y = yPreEn;
                    z = zPreEn;
//                    sendPacket(false, positionsBack, positions);
                }
                if (Math.abs(yDist) < maxYTP && mc.thePlayer.getDistance(posX, posY, posZ) >= 4) {
                    x = xPreEn;
                    y = posY;
                    z = zPreEn;
//                    sendPacket(false, positionsBack, positions);
                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(0, targetBlockPos.x,targetBlockPos.y,targetBlockPos.z, EnumFacing.UP.ordinal()));
                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(2, targetBlockPos.x,targetBlockPos.y,targetBlockPos.z, EnumFacing.UP.ordinal()));
                } else {
                    attack = false;
                }
            }
        }

        // Go back!
        for (int i = positions.size() - 2; i > -1; i--) {
            {
                x = positions.get(i).xCoord;
                y = positions.get(i).yCoord;
                z = positions.get(i).zCoord;
            }
            sendPacket(false, positionsBack, positions);
        }
        x = mc.thePlayer.posX;
        y = mc.thePlayer.posY;
        z = mc.thePlayer.posZ;
        sendPacket(false, positionsBack, positions);
        if (!attack) {
            if (sneaking) {
                mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 0));
            }
            positions.clear();
            positionsBack.clear();
            return false;
        }
        if (sneaking) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 0));
        }
        return true;
    }

    public static boolean infiniteReach(Vec3 src, Vec3 dest, double range, double maxXZTP, double maxYTP,
                                        ArrayList<Vec3> positionsBack, ArrayList<Vec3> positions) {
        positions.clear();
        positionsBack.clear();
        boolean tpUpOneBlock = false;
        double step = maxXZTP / range;
        int steps = 0;
        for (int i = 0; i < range; i++) {
            steps++;
            // Jigsaw.chatMessage(maxXZTP * steps);
            if (maxXZTP * steps > range) {
                break;
            }
        }
        int ind = 0;
        xPreEn = dest.xCoord;
        yPreEn = dest.yCoord;
        zPreEn = dest.zCoord;
        xPre = mc.thePlayer.posX;
        yPre = mc.thePlayer.posY;
        zPre = mc.thePlayer.posZ;
        boolean attack = true;
        boolean up = false;

        // If something in the way
        boolean hit = false;
        boolean tpStraight = false;
        boolean sneaking = mc.thePlayer.isSneaking();
        MovingObjectPosition rayTrace = null;
        MovingObjectPosition rayTrace1 = null;
        MovingObjectPosition rayTraceCarpet = null;
        if ((rayTraceWide(Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                Vec3.createVectorHelper(dest.xCoord, dest.yCoord, dest.zCoord), false, false, true))
                || (rayTrace1 = rayTracePos(
                Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ),
                Vec3.createVectorHelper(dest.xCoord, dest.yCoord + mc.thePlayer.getEyeHeight(), dest.zCoord), false, false,
                true)) != null) {
            if ((rayTrace = rayTracePos(Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                    Vec3.createVectorHelper(dest.xCoord, mc.thePlayer.posY, dest.zCoord), false, false, true)) != null
                    || (rayTrace1 = rayTracePos(
                    Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                            mc.thePlayer.posZ),
                    Vec3.createVectorHelper(dest.xCoord, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), dest.zCoord), false, false,
                    true)) != null) {
                MovingObjectPosition trace = null;
                if (rayTrace == null) {
                    trace = rayTrace1;
                }
                if (rayTrace1 == null) {
                    trace = rayTrace;
                }
                if (trace == null) {
                    // y = mc.thePlayer.posY;
                    // yPreEn = mc.thePlayer.posY;
                } else {
                    if (trace.blockX != 0 && trace.blockY != 0 && trace.blockZ != 0) {
                        boolean fence = false;
                        BlockPos target = new BlockPos(trace.blockX,trace.blockY,trace.blockZ);
                        // positions.add(BlockTools.getVec3(target));
                        up = true;
                        y = target.up().getY();
                        yPreEn = target.up().getY();
                        Block lastBlock = null;
                        Boolean found = false;
                        for (int i = 0; i < maxYTP; i++) {
                            MovingObjectPosition tr = rayTracePos(
                                    Vec3.createVectorHelper(mc.thePlayer.posX, target.getY() + i, mc.thePlayer.posZ),
                                    Vec3.createVectorHelper(dest.xCoord, target.getY() + i, dest.zCoord), false, false, true);
                            if (tr == null) {
                                continue;
                            }
                            if (tr.blockX == 0 && tr.blockY == 0 && tr.blockZ == 0) {
                                continue;
                            }

                            BlockPos blockPos = new BlockPos(tr.blockX,tr.blockY,tr.blockZ);
                            Block block = getBlock(blockPos);
                            if (block.getMaterial() != Material.air) {
                                lastBlock = block;
                                continue;
                            }
                            fence = lastBlock instanceof BlockFence;
                            y = target.getY() + i;
                            yPreEn = target.getY() + i;
                            if (fence) {
                                y += 1;
                                yPreEn += 1;
                                if (i + 1 > maxYTP) {
                                    found = false;
                                    break;
                                }
                            }
                            found = true;
                            break;
                        }
                        double difX = mc.thePlayer.posX - xPreEn;
                        double difZ = mc.thePlayer.posZ - zPreEn;
                        double divider = step * 0;
                        if (!found) {
                            attack = false;
                            return false;
                        }
                    } else {
                        attack = false;
                        return false;
                    }
                }
            } else {
                MovingObjectPosition ent = rayTracePos(
                        Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                        Vec3.createVectorHelper(dest.xCoord, dest.yCoord, dest.zCoord), false, false, false);
                if (ent != null && ent.entityHit == null) {
                    y = mc.thePlayer.posY;
                    yPreEn = mc.thePlayer.posY;
                } else {
                    y = mc.thePlayer.posY;
                    yPreEn = dest.yCoord;
                }

            }
        }
        if (!attack) {
            return false;
        }
        if (sneaking) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 1));
        }
        for (int i = 0; i < steps; i++) {
            ind++;
            if (i == 1 && up) {
                x = mc.thePlayer.posX;
                y = yPreEn;
                z = mc.thePlayer.posZ;
                sendPacket(false, positionsBack, positions);
            }
            if (i != steps - 1) {
                {
                    double difX = mc.thePlayer.posX - xPreEn;
                    double difY = mc.thePlayer.posY - yPreEn;
                    double difZ = mc.thePlayer.posZ - zPreEn;
                    double divider = step * i;
                    x = mc.thePlayer.posX - difX * divider;
                    y = mc.thePlayer.posY - difY * (up ? 1 : divider);
                    z = mc.thePlayer.posZ - difZ * divider;
                }
                sendPacket(false, positionsBack, positions);
            } else {
                // if last teleport
                {
                    double difX = mc.thePlayer.posX - xPreEn;
                    double difY = mc.thePlayer.posY - yPreEn;
                    double difZ = mc.thePlayer.posZ - zPreEn;
                    double divider = step * i;
                    x = mc.thePlayer.posX - difX * divider;
                    y = mc.thePlayer.posY - difY * (up ? 1 : divider);
                    z = mc.thePlayer.posZ - difZ * divider;
                }
                sendPacket(false, positionsBack, positions);
                double xDist = x - xPreEn;
                double zDist = z - zPreEn;
                double yDist = y - dest.yCoord;
                double dist = Math.sqrt(xDist * xDist + zDist * zDist);
                if (dist > 4) {
                    x = xPreEn;
                    y = yPreEn;
                    z = zPreEn;
                    sendPacket(false, positionsBack, positions);
                } else if (dist > 0.05 && up) {
                    x = xPreEn;
                    y = yPreEn;
                    z = zPreEn;
                    sendPacket(false, positionsBack, positions);
                }
                if (Math.abs(yDist) < maxYTP) {
                    x = xPreEn;
                    y = dest.yCoord;
                    z = zPreEn;
                    sendPacket(false, positionsBack, positions);
                    //Attack / interact

                } else {
                    attack = false;
                }
            }
        }

        // Go back!
        for (int i = positions.size() - 2; i > -1; i--) {
            {
                x = positions.get(i).xCoord;
                y = positions.get(i).yCoord;
                z = positions.get(i).zCoord;
            }
            sendPacket(false, positionsBack, positions);
        }
        x = mc.thePlayer.posX;
        y = mc.thePlayer.posY;
        z = mc.thePlayer.posZ;
        sendPacket(false, positionsBack, positions);
        if (!attack) {
            if (sneaking) {
                mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 0));
            }
            positions.clear();
            positionsBack.clear();
            return false;
        }
        if (sneaking) {
            mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, 0));
        }
        return true;
    }

    public static void attackInf(EntityLivingBase en) {
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(en, C02PacketUseEntity.Action.ATTACK));

        float sharpLevel = EnchantmentHelper.func_152377_a(mc.thePlayer.getHeldItem(), en.getCreatureAttribute());
        boolean vanillaCrit = (mc.thePlayer.fallDistance > 0.0F) && (!mc.thePlayer.onGround)
                && (!mc.thePlayer.isOnLadder()) && (!mc.thePlayer.isInWater())
                && (!mc.thePlayer.isPotionActive(Potion.blindness)) && (mc.thePlayer.ridingEntity == null);
        if (sharpLevel > 0.0F) {
            mc.thePlayer.onEnchantmentCritical(en);
        }
    }

    public static void sendPacket(boolean goingBack, ArrayList<Vec3> positionsBack, ArrayList<Vec3> positions) {
        C03PacketPlayer.C04PacketPlayerPosition playerPacket = new C03PacketPlayer.C04PacketPlayerPosition(x, mc.thePlayer.boundingBox.minY,y, z, true);
        mc.thePlayer.sendQueue.addToSendQueue(playerPacket);
        if (goingBack) {
            positionsBack.add(Vec3.createVectorHelper(x, y, z));
            return;
        }
        positions.add(Vec3.createVectorHelper(x, y, z));
    }

    @SuppressWarnings("unused")
    public static MovingObjectPosition rayTracePos(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid,
                                                   boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        float[] rots = getFacePosRemote(vec32, vec31);
        float yaw = rots[0];
        double angleA = Math.toRadians(JigsawReach.normalizeAngle(yaw));
        double angleB = Math.toRadians(JigsawReach.normalizeAngle(yaw) + 180);
        double size = 2.1;
        double size2 = 2.1;
        Vec3 left = Vec3.createVectorHelper(vec31.xCoord + Math.cos(angleA) * size, vec31.yCoord,
                vec31.zCoord + Math.sin(angleA) * size);
        Vec3 right = Vec3.createVectorHelper(vec31.xCoord + Math.cos(angleB) * size, vec31.yCoord,
                vec31.zCoord + Math.sin(angleB) * size);
        Vec3 left2 = Vec3.createVectorHelper(vec32.xCoord + Math.cos(angleA) * size, vec32.yCoord,
                vec32.zCoord + Math.sin(angleA) * size);
        Vec3 right2 = Vec3.createVectorHelper(vec32.xCoord + Math.cos(angleB) * size, vec32.yCoord,
                vec32.zCoord + Math.sin(angleB) * size);
        Vec3 leftA = Vec3.createVectorHelper(vec31.xCoord + Math.cos(angleA) * size2, vec31.yCoord,
                vec31.zCoord + Math.sin(angleA) * size2);
        Vec3 rightA = Vec3.createVectorHelper(vec31.xCoord + Math.cos(angleB) * size2, vec31.yCoord,
                vec31.zCoord + Math.sin(angleB) * size2);
        Vec3 left2A = Vec3.createVectorHelper(vec32.xCoord + Math.cos(angleA) * size2, vec32.yCoord,
                vec32.zCoord + Math.sin(angleA) * size2);
        Vec3 right2A = Vec3.createVectorHelper(vec32.xCoord + Math.cos(angleB) * size2, vec32.yCoord,
                vec32.zCoord + Math.sin(angleB) * size2);
        if (false) {
            MovingObjectPosition trace2 = mc.theWorld.func_147447_a(vec31, vec32, stopOnLiquid,
                    ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
            return trace2;
        }
         MovingObjectPosition trace4 = mc.theWorld.func_147447_a(leftA,
         left2A, stopOnLiquid, ignoreBlockWithoutBoundingBox,
         returnLastUncollidableBlock);
        MovingObjectPosition trace1 = mc.theWorld.func_147447_a(left, left2, stopOnLiquid,
                ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace2 = mc.theWorld.func_147447_a(vec31, vec32, stopOnLiquid,
                ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace3 = mc.theWorld.func_147447_a(right, right2, stopOnLiquid,
                ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
         MovingObjectPosition trace5 = mc.theWorld.func_147447_a(rightA,
         right2A, stopOnLiquid, ignoreBlockWithoutBoundingBox,
         returnLastUncollidableBlock);

        if (trace2 != null || trace1 != null || trace3 != null || trace4 != null || trace5 != null) {
            if (returnLastUncollidableBlock) {
                if (trace5 != null && (JigsawReach.getBlock(getBlockPosFrom(trace5)).getMaterial() != Material.air
                        || trace5.entityHit != null)) {
                    // positions.add(BlockTools.getVec3(trace3.getBlockPos()));
                    return trace5;
                }
                if (trace4 != null && (JigsawReach.getBlock(getBlockPosFrom(trace4)).getMaterial() != Material.air
                        || trace4.entityHit != null)) {
                    // positions.add(BlockTools.getVec3(trace3.getBlockPos()));
                    return trace4;
                }
                if (trace3 != null && (JigsawReach.getBlock(getBlockPosFrom(trace3)).getMaterial() != Material.air
                        || trace3.entityHit != null)) {
                    // positions.add(BlockTools.getVec3(trace3.getBlockPos()));
                    return trace3;
                }
                if (trace1 != null && (JigsawReach.getBlock(getBlockPosFrom(trace1)).getMaterial() != Material.air
                        || trace1.entityHit != null)) {
                    // positions.add(BlockTools.getVec3(trace1.getBlockPos()));
                    return trace1;
                }
                if (trace2 != null && (JigsawReach.getBlock(getBlockPosFrom(trace2)).getMaterial() != Material.air
                        || trace2.entityHit != null)) {
                    // positions.add(BlockTools.getVec3(trace2.getBlockPos()));
                    return trace2;
                }
            } else {
                if (trace5 != null) {
                    return trace5;
                }
                if (trace4 != null) {
                    return trace4;
                }
                if (trace3 != null) {
                    // positions.add(BlockTools.getVec3(trace3.getBlockPos()));
                    return trace3;
                }
                if (trace1 != null) {
                    // positions.add(BlockTools.getVec3(trace1.getBlockPos()));
                    return trace1;
                }
                if (trace2 != null) {
//                     positions.add(BlockTools.getVec3(trace2.getBlockPos()));
                    return trace2;
                }
            }
        }
        if (trace2 == null) {
            if (trace3 == null) {
                if (trace1 == null) {
                    if (trace5 == null) {
                        if (trace4 == null) {
                            return null;
                        }
                        return trace4;
                    }
                    return trace5;
                }
                return trace1;
            }
            return trace3;
        }
        return trace2;
    }

    public static boolean rayTraceWide(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox,
                                       boolean returnLastUncollidableBlock) {
        float yaw = getFacePosRemote(vec32, vec31)[0];
        yaw = JigsawReach.normalizeAngle(yaw);
        yaw += 180;
        yaw = MathHelper.wrapAngleTo180_float(yaw);
        double angleA = Math.toRadians(yaw);
        double angleB = Math.toRadians(yaw + 180);
        double size = 2.1;
        double size2 = 2.1;
        Vec3 left = Vec3.createVectorHelper(vec31.xCoord + Math.cos(angleA) * size, vec31.yCoord,
                vec31.zCoord + Math.sin(angleA) * size);
        Vec3 right = Vec3.createVectorHelper(vec31.xCoord + Math.cos(angleB) * size, vec31.yCoord,
                vec31.zCoord + Math.sin(angleB) * size);
        Vec3 left2 = Vec3.createVectorHelper(vec32.xCoord + Math.cos(angleA) * size, vec32.yCoord,
                vec32.zCoord + Math.sin(angleA) * size);
        Vec3 right2 = Vec3.createVectorHelper(vec32.xCoord + Math.cos(angleB) * size, vec32.yCoord,
                vec32.zCoord + Math.sin(angleB) * size);
        Vec3 leftA = Vec3.createVectorHelper(vec31.xCoord + Math.cos(angleA) * size2, vec31.yCoord,
                vec31.zCoord + Math.sin(angleA) * size2);
        Vec3 rightA = Vec3.createVectorHelper(vec31.xCoord + Math.cos(angleB) * size2, vec31.yCoord,
                vec31.zCoord + Math.sin(angleB) * size2);
        Vec3 left2A = Vec3.createVectorHelper(vec32.xCoord + Math.cos(angleA) * size2, vec32.yCoord,
                vec32.zCoord + Math.sin(angleA) * size2);
        Vec3 right2A = Vec3.createVectorHelper(vec32.xCoord + Math.cos(angleB) * size2, vec32.yCoord,
                vec32.zCoord + Math.sin(angleB) * size2);
        // MovingObjectPosition trace4 = mc.theWorld.func_147447_a(leftA,
        // left2A, stopOnLiquid, ignoreBlockWithoutBoundingBox,
        // returnLastUncollidableBlock);
        MovingObjectPosition trace1 = mc.theWorld.func_147447_a(left, left2, stopOnLiquid,
                ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace2 = mc.theWorld.func_147447_a(vec31, vec32, stopOnLiquid,
                ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        MovingObjectPosition trace3 = mc.theWorld.func_147447_a(right, right2, stopOnLiquid,
                ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
        // MovingObjectPosition trace5 = mc.theWorld.func_147447_a(rightA,
        // right2A, stopOnLiquid, ignoreBlockWithoutBoundingBox,
        // returnLastUncollidableBlock);
        MovingObjectPosition trace4 = null;
        MovingObjectPosition trace5 = null;
        if (returnLastUncollidableBlock) {
            return (trace1 != null && JigsawReach.getBlock(getBlockPosFrom(trace1)).getMaterial() != Material.air)
                    || (trace2 != null && JigsawReach.getBlock(getBlockPosFrom(trace2)).getMaterial() != Material.air)
                    || (trace3 != null && JigsawReach.getBlock(getBlockPosFrom(trace3)).getMaterial() != Material.air)
                    || (trace4 != null && JigsawReach.getBlock(getBlockPosFrom(trace4)).getMaterial() != Material.air)
                    || (trace5 != null && JigsawReach.getBlock(getBlockPosFrom(trace5)).getMaterial() != Material.air);
        } else {
            return trace1 != null || trace2 != null || trace3 != null || trace5 != null || trace4 != null;
        }

    }


    public static boolean isNotItem(Object o) {
        if (!(o instanceof EntityLivingBase)) {
            return false;
        }
        return true;
    }

    public static void faceEntity(Entity en) {
        facePos(Vec3.createVectorHelper(en.posX - 0.5, en.posY + (en.getEyeHeight() - en.height / 1.5), en.posZ - 0.5));

    }

    public static BlockPos getBlockPosFrom(MovingObjectPosition movingObjectPosition){
        if (movingObjectPosition.blockX != 0 && movingObjectPosition.blockY != 0 && movingObjectPosition.blockZ != 0){
            return new BlockPos(movingObjectPosition.blockX,movingObjectPosition.blockY,movingObjectPosition.blockZ);
        }else{
            return null;
        }
    }

    public static void faceBlock(BlockPos blockPos) {
        facePos(getVec3(blockPos));
    }

    public static Vec3 getVec3(BlockPos blockPos) {
        return Vec3.createVectorHelper(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public static BlockPos getBlockPos(Vec3 vec) {
        return new BlockPos(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public static void facePos(Vec3 vec) {
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


    /**
     *
     *
     * @return index 0 = yaw | index 1 = pitch
     */
    public static float[] getFacePosRemote(Vec3 src, Vec3 dest) {
        double diffX = dest.xCoord - src.xCoord;
        double diffY = dest.yCoord - (src.yCoord);
        double diffZ = dest.zCoord - src.zCoord;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] {MathHelper.wrapAngleTo180_float(yaw),
                MathHelper.wrapAngleTo180_float(pitch) };
    }



    public static float getPlayerBlockDistance(double posX, double posY, double posZ) {
        float xDiff = (float) (Minecraft.getMinecraft().thePlayer.posX - posX);
        float yDiff = (float) (Minecraft.getMinecraft().thePlayer.posY - posY);
        float zDiff = (float) (Minecraft.getMinecraft().thePlayer.posZ - posZ);
        return getBlockDistance(xDiff, yDiff, zDiff);
    }

    public static float getBlockDistance(float xDiff, float yDiff, float zDiff) {
        return MathHelper.sqrt_float(
                (xDiff - 0.5F) * (xDiff - 0.5F) + (yDiff - 0.5F) * (yDiff - 0.5F) + (zDiff - 0.5F) * (zDiff - 0.5F));
    }



    private final static float limitAngleChange(final float current, final float intended, final float maxChange) {
        float change = intended - current;
        if (change > maxChange)
            change = maxChange;
        else if (change < -maxChange)
            change = -maxChange;
        return current + change;
    }

    public static double normalizeAngle(double angle) {
        return (angle + 360) % 360;
    }

    public static float normalizeAngle(float angle) {
        return (angle + 360) % 360;
    }

    public static boolean isBlockPosAir(BlockPos blockPos) {
        return getBlock(blockPos).getMaterial() == Material.air;
    }

    public static Block getBlockRelativeToEntity(Entity en, double d) {
        return getBlock(new BlockPos(en.posX, en.posY + d, en.posZ));
    }

    public static BlockPos getBlockPosRelativeToEntity(Entity en, double d) {
        return new BlockPos(en.posX, en.posY + d, en.posZ);
    }

    public static Block getBlock(BlockPos pos) {
        return mc.theWorld.getBlock(pos.x,pos.y,pos.z);
    }

    private static Vec3 lastLoc = null;

    public static Vec3 getLastGroundLocation() {
        return lastLoc;

    }

    public static void updateLastGroundLocation() {
        if(mc.thePlayer.onGround) {
            lastLoc = Vec3.createVectorHelper(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        }
    }


}
