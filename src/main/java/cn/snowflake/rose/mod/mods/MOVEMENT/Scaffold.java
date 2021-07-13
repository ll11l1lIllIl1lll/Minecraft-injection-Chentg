package cn.snowflake.rose.mod.mods.MOVEMENT;


import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.math.Vec3Util;
import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.mcutil.EnumFacing;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

public class Scaffold
        extends Module {
    public static boolean d;
    private BlockData blockData;

    private timeHelper timer2 = new timeHelper();
    private Value<Boolean> tower = new Value<Boolean>("Scaffold_Tower", true);
    private Value<Boolean> noSwing = new Value<Boolean>("Scaffold_NoSwing", true);
    public static Value<Boolean> down = new Value<Boolean>("Scaffold_Down", true);

    private final List<Block> blacklisted = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.ender_chest, Blocks.yellow_flower, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.crafting_table, Blocks.snow_layer, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.cactus, Blocks.lever, Blocks.activator_rail, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.furnace, Blocks.ladder,  Blocks.trapdoor, Blocks.tripwire_hook, Blocks.hopper,  Blocks.dispenser, Blocks.sapling, Blocks.tallgrass, Blocks.deadbush, Blocks.web, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.nether_brick_fence, Blocks.vine, Blocks.double_plant, Blocks.flower_pot, Blocks.beacon, Blocks.pumpkin, Blocks.lit_pumpkin);
    public static List<Block> blacklistedBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.ender_chest, Blocks.enchanting_table, Blocks.stone_button, Blocks.wooden_button, Blocks.crafting_table, Blocks.beacon);



    public Scaffold() {
        super("Scaffold","Scaffold", Category.MOVEMENT);
        setChinesename("\u81ea\u52a8\u642d\u8def");
    }

    @Override
    public String getDescription() {
        return "自动搭路!";
    }

    @EventTarget
    public void onPre(EventMotion event) {
        if (event.isPre()) {
            double x = mc.thePlayer.posX;
            double y = mc.thePlayer.posY - 2.5;
            double z = mc.thePlayer.posZ;
            BlockPos blockBelow = new BlockPos(x, y, z);
            if (mc.thePlayer != null) {
                this.blockData = this.getBlockData(blockBelow);
                if (this.blockData == null) {
                    this.blockData = this.getBlockData(blockBelow.down(1));
                }
                blockBelow = new BlockPos(x, y, z);
                if (this.mc.theWorld.getBlock(blockBelow.getX(), blockBelow.getY(), blockBelow.getZ()) == Blocks.air) {
                    if (this.blockData != null) {
                        float[] rot = this.getRotationsBlock(BlockData.position, BlockData.face);
                        event.pitch = rot[1];
                        event.yaw = rot[0];
                        mc.thePlayer.rotationYawHead = rot[0];
                    }
                    if (this.tower.getValueState().booleanValue()) {
                         if (!this.isMoving2() && this.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                            mc.thePlayer.motionX = 0.0;
                            mc.thePlayer.motionZ = 0.0;
                            mc.thePlayer.jumpMovementFactor = 0.0f;
                            blockBelow = new BlockPos(x, y, z);
                            if (this.mc.theWorld.getBlock(blockBelow.x,blockBelow.y,blockBelow.z) == Blocks.air && this.blockData != null) {
                                mc.thePlayer.motionY = 0.4196;
                                mc.thePlayer.motionX *= 0.75;
                                mc.thePlayer.motionZ *= 0.75;
                            }
                        }
                    }
                }
            }
        }else if (event.getEventType() == EventType.POST){
            int i;
            for (i = 36; i < 45; ++i) {
                ItemStack is;
                Item item;
                if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((item = (is = mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem()) instanceof ItemBlock) || this.blacklisted.contains(item) || item.getUnlocalizedName().toLowerCase().contains("chest") || item.getUnlocalizedName().toLowerCase().contains("torch") || this.blockData == null) continue;
                int currentItem = mc.thePlayer.inventory.currentItem;
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i - 36));
                mc.thePlayer.inventory.currentItem = i - 36;
                mc.playerController.updateController();
                try {
                    mc.playerController.onPlayerRightClick(
                            mc.thePlayer,//entitylivingbase
                            this.mc.theWorld, mc.thePlayer.getHeldItem(),//itemstack
                            BlockData.position.x ,//x
                            BlockData.position.y,//y
                            BlockData.position.z,//z
                            BlockData.face.getIndex(),//enumface
                            new Vec3Util(blockData.getPosition()).addVector(0.5, 0.5, 0.5).add(new Vec3Util(this.blockData.getFacing().getDirectionVec())).scale(0.5).toVec3());

                    if (this.noSwing.getValueState()) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    } else {
                        mc.thePlayer.swingItem();
                    }
                }
                catch (Exception exception) {
                    ;
                }
                mc.thePlayer.inventory.currentItem = currentItem;
                mc.playerController.updateController();
                return;
            }
            if (this.invCheck()) {
                for (i = 9; i < 36; ++i) {
                    Item item;
                    if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((item = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) instanceof ItemBlock) || this.blacklisted.contains(item)) continue;
                    this.swap(i, 7);
                    break;
                }
            }
        }
    }

    public boolean isOnGround(double height) {
        if (!this.mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0, - height, 0.0)).isEmpty()) {
            return true;
        }
        return false;
    }


    public boolean isMoving2() {
        return mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
    }

    public float[] getRotationsBlock(BlockPos block, EnumFacing face) {
        double x = (double)block.getX() + 0.5 - mc.thePlayer.posX + (double)face.getFrontOffsetX() / 2.0;
        double z = (double)block.getZ() + 0.5 - mc.thePlayer.posZ + (double)face.getFrontOffsetZ() / 2.0;
        double y = (double)block.getY() + 0.5;
        double d1 = mc.thePlayer.posY - 1 + (double)mc.thePlayer.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(Math.atan2(d1, d3) * 180.0 / 3.141592653589793);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[]{yaw, pitch};
    }




    protected void swap(int slot, int hotbarNum) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
    }

    private boolean invCheck() {
        for (int i = 36; i < 45; ++i) {
            Item item;
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((item = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) instanceof ItemBlock) || this.blacklisted.contains((item))) continue;
            return false;
        }
        return true;
    }



    private BlockData getBlockData(BlockPos pos) {
        if (!blacklistedBlocks.contains(getBlock(pos.add(0, -1, 0)))) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!blacklistedBlocks.contains(getBlock(pos.add(-1, 0, 0)))) {
            return new BlockData(pos.add(-1, 0, 0), Keyboard.isKeyDown(42) && mc.thePlayer.onGround && mc.thePlayer.fallDistance == 0.0f && getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ)) == Blocks.air ? EnumFacing.DOWN : EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(getBlock(pos.add(1, 0, 0)))) {
            return new BlockData(pos.add(1, 0, 0), Keyboard.isKeyDown(42) && mc.thePlayer.onGround && mc.thePlayer.fallDistance == 0.0f && getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ)) == Blocks.air ? EnumFacing.DOWN : EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(getBlock(pos.add(0, 0, -1)))) {
            return new BlockData(pos.add(0, 0, -1), Keyboard.isKeyDown(42) && mc.thePlayer.onGround && mc.thePlayer.fallDistance == 0.0f && getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ)) == Blocks.air ? EnumFacing.DOWN : EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(getBlock(pos.add(0, 0, 1)))) {
            return new BlockData(pos.add(0, 0, 1), Keyboard.isKeyDown(42) && mc.thePlayer.onGround && mc.thePlayer.fallDistance == 0.0f && getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ)) == Blocks.air ? EnumFacing.DOWN : EnumFacing.NORTH);
        }
        BlockPos add = pos.add(-1, 0, 0);
        if (!blacklistedBlocks.contains(getBlock(add.add(-1, 0, 0)))) {
            return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(getBlock(add.add(1, 0, 0)))) {
            return new BlockData(add.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(getBlock(add.add(0, 0, -1)))) {
            return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(getBlock(add.add(0, 0, 1)))) {
            return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add2 = pos.add(1, 0, 0);
        if (!blacklistedBlocks.contains(getBlock(add2.add(-1, 0, 0)))) {
            return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(getBlock(add2.add(1, 0, 0)))) {
            return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(getBlock(add2.add(0, 0, -1)))) {
            return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(getBlock(add2.add(0, 0, 1)))) {
            return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add3 = pos.add(0, 0, -1);
        if (!blacklistedBlocks.contains(getBlock(add3.add(-1, 0, 0)))) {
            return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(getBlock(add3.add(1, 0, 0)))) {
            return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(getBlock(add3.add(0, 0, -1)))) {
            return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(getBlock(add3.add(0, 0, 1)))) {
            return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add4 = pos.add(0, 0, 1);
        if (!blacklistedBlocks.contains(getBlock(add4.add(-1, 0, 0)))) {
            return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(getBlock(add4.add(1, 0, 0)))) {
            return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(getBlock(add4.add(0, 0, -1)))) {
            return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(getBlock(add4.add(0, 0, 1)))) {
            return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }

    public Block getBlock(BlockPos blockPos){
        return this.mc.theWorld.getBlock(blockPos.x,blockPos.y,blockPos.z);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        d = true;
        this.timer2.reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        d = false;
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
    }


    public class timeHelper {
        private long prevMS = 0L;

        public boolean delay(float milliSec) {
            return (float)(this.getTime() - this.prevMS) >= milliSec;
        }

        public void reset() {
            this.prevMS = this.getTime();
        }

        public long getTime() {
            return System.nanoTime() / 1000000L;
        }

        public long getDifference() {
            return this.getTime() - this.prevMS;
        }

        public void setDifference(long difference) {
            this.prevMS = this.getTime() - difference;
        }
    }

    private static class BlockData {
        public static BlockPos position;
        public static EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            BlockData.position = position;
            BlockData.face = face;
        }

        private BlockPos getPosition() {
            return position;
        }

        private EnumFacing getFacing() {
            return face;
        }

    }

}



