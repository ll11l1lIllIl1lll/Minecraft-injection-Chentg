package cn.snowflake.rose.utils.path;


import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.math.Vec3Util;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.Comparator;

public class TPUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

   // public static ArrayList<Vec3Util> computePath(Vec3Util from, Vec3Util to, double dashDistance, boolean isTeleport) {
   //            if (!canPassThrow(new BlockPos(from.toVec3()))) {
   //                from = from.addVector(0, 1, 0);
   //            }
   //        TPUtil$1 pathfinder = new TPUtil$1(from, to);
   //        pathfinder.compute();
   //
   //        int i = 0;
   //        Vec3Util lastLoc = null;
   //        Vec3Util lastDashLoc = null;
   //        ArrayList<Vec3Util> path = new ArrayList<>();
   //        ArrayList<Vec3Util> pathFinderPath = pathfinder.getPath();
   //        for (Vec3Util pathElm : pathFinderPath) {
   //            if (i == 0 || i == pathFinderPath.size() - 1) {
   //                if (lastLoc != null) {
   //                    path.add(lastLoc.addVector(0.5, 0, 0.5));
   //                }
   //                path.add(pathElm.addVector(0.5, 0, 0.5));
   //                lastDashLoc = pathElm;
   //            } else {
   //                boolean canContinue = true;
   //                if (pathElm.squareDistanceTo(lastDashLoc) > (isTeleport ? dashDistance : (dashDistance * dashDistance))) {
   //                    canContinue = false;
   //                } else {
   //                    double smallX = Math.min(lastDashLoc.x, pathElm.x);
   //                    double smallY = Math.min(lastDashLoc.y, pathElm.y);
   //                    double smallZ = Math.min(lastDashLoc.z, pathElm.z);
   //                    double bigX = Math.max(lastDashLoc.x, pathElm.x);
   //                    double bigY = Math.max(lastDashLoc.y, pathElm.y);
   //                    double bigZ = Math.max(lastDashLoc.z, pathElm.z);
   //                    cordsLoop:
   //                    for (int x = (int) smallX; x <= bigX; x++) {
   //                        for (int y = (int) smallY; y <= bigY; y++) {
   //                            for (int z = (int) smallZ; z <= bigZ; z++) {
   //                                if (!TPUtil$1.checkPositionValidity(x, y, z)) {
   //                                    canContinue = false;
   //                                    break cordsLoop;
   //                                }
   //                            }
   //                        }
   //                    }
   //                }
   //                if (!canContinue) {
   //                    path.add(lastLoc.addVector(0.5, 0, 0.5));
   //                    lastDashLoc = lastLoc;
   //                }
   //            }
   //            lastLoc = pathElm;
   //            i++;
   //        }
   //        return path;
   //    }
    private static boolean canPassThrow(BlockPos pos) {
        Block block = mc.theWorld.getBlock(pos.getX(), pos.getY(), pos.getZ());
        return (block.getMaterial() != Material.air && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine && block != Blocks.ladder && block != Blocks.water && block != Blocks.flowing_water && block != Blocks.wall_sign && block != Blocks.standing_sign);
    }

    public static ArrayList<Vec3Util> computePath(Vec3Util from, Vec3Util to) {
        if (!canPassThrow(new BlockPos(from.toVec3()))) {
            from = from.addVector(0, 1, 0);
        }
        TPUtil$1 pathfinder = new TPUtil$1(from, to);
        pathfinder.compute();

        int i = 0;
        Vec3Util lastLoc = null;
        Vec3Util lastDashLoc = null;
        ArrayList<Vec3Util> path = new ArrayList<>();
        ArrayList<Vec3Util> pathFinderPath = pathfinder.getPath();
        for (Vec3Util pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0, 0.5));
                lastDashLoc = pathElm;
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > 100) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.x, pathElm.x);
                    double smallY = Math.min(lastDashLoc.y, pathElm.y);
                    double smallZ = Math.min(lastDashLoc.z, pathElm.z);
                    double bigX = Math.max(lastDashLoc.x, pathElm.x);
                    double bigY = Math.max(lastDashLoc.y, pathElm.y);
                    double bigZ = Math.max(lastDashLoc.z, pathElm.z);
                    cordsLoop:
                    for (int x = (int) smallX; x <= bigX; x++) {
                        for (int y = (int) smallY; y <= bigY; y++) {
                            for (int z = (int) smallZ; z <= bigZ; z++) {
                                if (!TPUtil$1.checkPositionValidity(x, y, z)) {
                                    canContinue = false;
                                    break cordsLoop;
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }

    public static class TPUtil$1 {
        private final Vec3Util startVec3;
        private final Vec3Util endVec3;
        private ArrayList<Vec3Util> path = new ArrayList<>();
        private final ArrayList<Hub> hubs = new ArrayList<>();
        private final ArrayList<Hub> hubsToWork = new ArrayList<>();

        private static final Vec3Util[] flatCardinalDirections = {
                new Vec3Util(1, 0, 0),
                new Vec3Util(-1, 0, 0),
                new Vec3Util(0, 0, 1),
                new Vec3Util(0, 0, -1)
        };

        TPUtil$1(Vec3Util startVec3, Vec3Util endVec3) {
            this.startVec3 = startVec3.addVector(0, 0, 0);
            this.endVec3 = endVec3.addVector(0, 0, 0);
        }

        public ArrayList<Vec3Util> getPath() {
            return path;
        }

        void compute() {
            path.clear();
            hubsToWork.clear();
            ArrayList<Vec3Util> initPath = new ArrayList<>();
            initPath.add(startVec3);
            hubsToWork.add(new Hub(startVec3, null, initPath, startVec3.squareDistanceTo(endVec3), 0, 0));
            search:
            for (int i = 0; i < 1000; i++) {
                hubsToWork.sort(new CompareHub());
                int j = 0;
                if (hubsToWork.size() == 0) {
                    break;
                }
                for (Hub hub : new ArrayList<>(hubsToWork)) {
                    j++;
                    if (j > 4) {
                        break;
                    } else {
                        hubsToWork.remove(hub);
                        hubs.add(hub);

                        for (Vec3Util direction : flatCardinalDirections) {
                            Vec3Util loc = hub.getLoc().add(direction);
                            if (checkPositionValidity(loc)) {
                                if (addHub(hub, loc)) {
                                    break search;
                                }
                            }
                        }

                        Vec3Util loc1 = hub.getLoc().addVector(0, 1, 0);
                        if (checkPositionValidity(loc1)) {
                            if (addHub(hub, loc1)) {
                                break search;
                            }
                        }

                        Vec3Util loc2 = hub.getLoc().addVector(0, -1, 0);
                        if (checkPositionValidity(loc2)) {
                            if (addHub(hub, loc2)) {
                                break search;
                            }
                        }
                    }
                }
            }
            hubs.sort(new CompareHub());
            path = hubs.get(0).getPath();
        }

        static boolean checkPositionValidity(Vec3Util loc) {
            return checkPositionValidity((int) loc.x, (int) loc.y, (int) loc.z);
        }

        static boolean checkPositionValidity(int x, int y, int z) {
            BlockPos block1 = new BlockPos(x, y, z);
            BlockPos block2 = new BlockPos(x, y + 1, z);
            BlockPos block3 = new BlockPos(x, y - 1, z);
            return isNotBlockSolid(block1) && isNotBlockSolid(block2) && isSafeToWalkOn(block3);
        }

        private static boolean isNotBlockSolid(BlockPos block) {
            return !(mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ()).getMaterial().isSolid()||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockSlab || mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockStairs ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockCactus ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockChest ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())  instanceof BlockEnderChest ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockSkull ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())  instanceof BlockPane ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockFence ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockWall ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockGlass ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockPistonBase ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockPistonExtension ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockPistonMoving ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockStainedGlass ||
                    mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ())instanceof BlockTrapDoor);
        }

        private static boolean isSafeToWalkOn(BlockPos block) {
            return !(mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockFence) &&
                    !(mc.theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockWall);
        }

        Hub isHubExisting(Vec3Util loc) {
            for (Hub hub : hubs) {
                if (hub.getLoc().x == loc.x && hub.getLoc().y == loc.y && hub.getLoc().z == loc.z) {
                    return hub;
                }
            }
            for (Hub hub : hubsToWork) {
                if (hub.getLoc().x == loc.x && hub.getLoc().y == loc.y && hub.getLoc().z == loc.z) {
                    return hub;
                }
            }
            return null;
        }

        boolean addHub(Hub parent, Vec3Util loc) {
            Hub existingHub = isHubExisting(loc);
            double totalCost = 0;
            if (parent != null) {
                totalCost += parent.getTotalCost();
            }
            assert parent != null;
            if (existingHub == null) {
                double minDistanceSquared = 9;
                if (loc.x == endVec3.x && loc.y == endVec3.y && loc.z == endVec3.z || loc.squareDistanceTo(endVec3) <= minDistanceSquared) {
                    path.clear();
                    path = parent.getPath();
                    path.add(loc);
                    return true;
                } else {
                    ArrayList<Vec3Util> path = new ArrayList<>(parent.getPath());
                    path.add(loc);
                    hubsToWork.add(new Hub(loc, parent, path, loc.squareDistanceTo(endVec3), 0, totalCost));
                }
            } else if (existingHub.getCost() > (double) 0) {
                ArrayList<Vec3Util> path = new ArrayList<>(parent.getPath());
                path.add(loc);
                existingHub.setLoc(loc);
                existingHub.setParent(parent);
                existingHub.setPath(path);
                existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(endVec3));
                existingHub.setCost(0);
                existingHub.setTotalCost(totalCost);
            }
            return false;
        }

        private static class Hub {
            private Vec3Util loc;
            private Hub parent;
            private ArrayList<Vec3Util> path;
            private double squareDistanceToFromTarget;
            private double cost;
            private double totalCost;

            Hub(Vec3Util loc, Hub parent, ArrayList<Vec3Util> path, double squareDistanceToFromTarget, double cost, double totalCost) {
                this.loc = loc;
                this.parent = parent;
                this.path = path;
                this.squareDistanceToFromTarget = squareDistanceToFromTarget;
                this.cost = cost;
                this.totalCost = totalCost;
            }

            public Vec3Util getLoc() {
                return loc;
            }

            public Hub getParent() {
                return parent;
            }

            public ArrayList<Vec3Util> getPath() {
                return path;
            }

            double getSquareDistanceToFromTarget() {
                return squareDistanceToFromTarget;
            }

            public double getCost() {
                return cost;
            }

            public void setLoc(Vec3Util loc) {
                this.loc = loc;
            }

            public void setParent(Hub parent) {
                this.parent = parent;
            }

            public void setPath(ArrayList<Vec3Util> path) {
                this.path = path;
            }

            void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
                this.squareDistanceToFromTarget = squareDistanceToFromTarget;
            }

            public void setCost(double cost) {
                this.cost = cost;
            }

            double getTotalCost() {
                return totalCost;
            }

            void setTotalCost(double totalCost) {
                this.totalCost = totalCost;
            }
        }

        public static class CompareHub implements Comparator<Hub> {
            @Override
            public int compare(Hub o1, Hub o2) {
                return (int) ((o1.getSquareDistanceToFromTarget() + o1.getTotalCost()) - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
            }
        }
    }


}

