package cn.snowflake.rose.mod.mods.FORGE.thaumcraft;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventKey;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.injection.ClientLoader;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Method;
import java.util.List;

public class ForceScanned extends Module {

    public Value<Double> Scan_Number = new Value<Double>("ForceScanned_Scan Number", 1.0, 1d, 999.0, 1);

    public ForceScanned() {
        super("ForceScanned", "Force Scanned", Category.FORGE);
        try {
            Class.forName("thaumcraft.api.aspects.Aspect");
            Class.forName("thaumcraft.common.Thaumcraft");
        } catch (Exception e) {
            this.setWorking(false);
        }
    }

    @Override
    public String getDescription() {
        return "无需魔导透镜强制扫描(鼠标移动到物品小键盘0)!";
    }

    public boolean NeiMod() {
        try {
            Class.forName("codechicken.nei.guihook.GuiContainerManager");
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @EventTarget
    public void onKey(EventKey ek) {
        if (ek.getKey() == Keyboard.KEY_NUMPAD0) {

            if (mc.currentScreen == null){
                Entity pointedEntity = getPointedEntity(mc.thePlayer.worldObj, mc.thePlayer, 0.5D, 10.0D, 0.0F, true);
                if (pointedEntity != null) {
                    for (int i = 0; i < (1 * Scan_Number.getValueState()); i++)
                        doScanned(0,0,2,pointedEntity.getEntityId(),"");
                    Client.instance.getNotificationManager().addNotification(this,"doScanned ["+pointedEntity.getEntityId()+"] !", Notification.Type.SUCCESS);
                }else {
                    if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK ) {
                        MovingObjectPosition mop = mc.objectMouseOver;
                        Block block = mc.theWorld.getBlock(mop.blockX, mop.blockY, mop.blockZ);
                        TileEntity tile = mc.theWorld.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
                        try {
                            if(tile != null && Class.forName("thaumcraft.common.tiles.TileNode").isInstance(tile)){
                                String node = "NODE" + ":"+tile.xCoord+ ":"+tile.yCoord+ ":"+tile.zCoord;
                                node = "NODE"+Class.forName("thaumcraft.common.tiles.TileNode").getMethod("getId").invoke(tile);
                                for (int i = 0; i < (1 * Scan_Number.getValueState()); i++)
                                    doScanned(0,0,3,0,node);
                                Client.instance.getNotificationManager().addNotification(this,"doScanned [Airy] !", Notification.Type.SUCCESS);
                            }else  if (block != Blocks.air) {
                                int md = block.getDamageValue(mc.theWorld, mop.blockX, mop.blockY, mop.blockZ);
                                ItemStack is = getPickBlock(block,mop, mc.theWorld,mop.blockX, mop.blockY, mop.blockZ);
                                for (int i = 0; i < (1 * Scan_Number.getValueState()); i++)
                                    doScanned(Item.getIdFromItem(is.getItem()),md,1,0,"");
                                Client.instance.getNotificationManager().addNotification(this,"doScanned ["+Item.getIdFromItem(is.getItem())+"/"+ md +"] !", Notification.Type.SUCCESS);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                        MovingObjectPosition mop = mc.objectMouseOver;
                        if(mop.entityHit != null){
                            Entity ent = mop.entityHit;
                            for (int i = 0; i < (1 * Scan_Number.getValueState()); i++)
                                doScanned(0,0,2,ent.getEntityId(),"");
                            Client.instance.getNotificationManager().addNotification(this,"doScanned ["+ent.getCommandSenderName() +"] !", Notification.Type.SUCCESS);
                        }
                    }
                }
            }

            if(NeiMod() && mc.currentScreen != null){
                try {
                    GuiContainer container = mc.currentScreen instanceof GuiContainer ? ((GuiContainer) mc.currentScreen) : null;
                    if (container == null) {
                        return;
                    }
                    Object checkItem = Class.forName("codechicken.nei.guihook.GuiContainerManager")
                            .getDeclaredMethod("getStackMouseOver", GuiContainer.class)
                            .invoke(null, container);
                    if (checkItem instanceof ItemStack) {
                        ItemStack item = (ItemStack) checkItem;
                        for (int i = 0; i < (1 * Scan_Number.getValueState()); i++)
                            doScanned(Item.getIdFromItem(item.getItem()),item.getItemDamage(),1,0,"");
                        Client.instance.getNotificationManager().addNotification(this,"doScanned ["+net.minecraft.item.Item.getIdFromItem(item.getItem())+"/"+ item.getItemDamage() +"] !", Notification.Type.SUCCESS);
                    }
                } catch (Exception ignored) {
                    Client.instance.getNotificationManager().addNotification(this, "\247cError", Notification.Type.ERROR);
                }
            }else {
                GuiScreen screen = mc.currentScreen;
                ItemStack item = null;
                if (screen instanceof GuiContainer) {
                    try {
                        ScaledResolution get = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
                        int mouseX = Mouse.getX() / get.getScaleFactor();
                        int mouseY = Mouse.getY() / get.getScaleFactor();
                        GuiContainer container = (GuiContainer) screen;
                        Method isMouseOverSlot = GuiContainer.class.getDeclaredMethod(ClientLoader.runtimeObfuscationEnabled ? "func_146981_a" : "isMouseOverSlot", Slot.class, Integer.TYPE, Integer.TYPE);
                        isMouseOverSlot.setAccessible(true);
                        for (int i = 0; i < container.inventorySlots.inventorySlots.size(); i++) {
                            //noinspection JavaReflectionInvocation
                            if ((Boolean) isMouseOverSlot.invoke(container, container.inventorySlots.inventorySlots.get(i), mouseX, get.getScaledHeight() - mouseY)) {
                                item = container.inventorySlots.inventorySlots.get(i) == null ? null : ((Slot) container.inventorySlots.inventorySlots.get(i)).getStack();
                            }
                        }
                    } catch (Exception ex) {
                        Client.instance.getNotificationManager().addNotification(this, "\247cError", Notification.Type.ERROR);
                    }
                    if (item == null) {
                        return;
                    }
                    for (int i = 0; i < (1 * Scan_Number.getValueState()); i++)
                        doScanned(Item.getIdFromItem(item.getItem()),item.getItemDamage(),1,0,"");
                    Client.instance.getNotificationManager().addNotification(this,"doScanned ["+Item.getIdFromItem(item.getItem())+"/"+ item.getItemDamage() +"] !", Notification.Type.SUCCESS);
                }
            }
        }

    }


    public static Entity getPointedEntity(World world, Entity entityplayer, double minrange, double range, float padding, boolean nonCollide) {
        Entity pointedEntity = null;
        Vec3 vec3d = Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + (double)entityplayer.getEyeHeight(), entityplayer.posZ);
        Vec3 vec3d1 = entityplayer.getLookVec();
        Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * range, vec3d1.yCoord * range, vec3d1.zCoord * range);
        List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.addCoord(vec3d1.xCoord * range, vec3d1.yCoord * range, vec3d1.zCoord * range).expand((double)padding, (double)padding, (double)padding));
        double d2 = 0.0D;

        for(int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity)list.get(i);
            if ((double)entity.getDistanceToEntity(entityplayer) >= minrange && (entity.canBeCollidedWith() || nonCollide) && world.func_147447_a(Vec3.createVectorHelper(entityplayer.posX, entityplayer.posY + (double)entityplayer.getEyeHeight(), entityplayer.posZ), Vec3.createVectorHelper(entity.posX, entity.posY + (double)entity.getEyeHeight(), entity.posZ), false, true, false) == null) {
                float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
                AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
                if (axisalignedbb.isVecInside(vec3d)) {
                    if (0.0D < d2 || d2 == 0.0D) {
                        pointedEntity = entity;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
                    if (d3 < d2 || d2 == 0.0D) {
                        pointedEntity = entity;
                        d2 = d3;
                    }
                }
            }
        }

        return pointedEntity;
    }




    private void doScanned(int ItemId ,int Itemdmg ,int Type,int entityId,String Inode) {
        int playerId = mc.thePlayer.getEntityId();
        int dimensionId = mc.thePlayer.dimension;
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(12);
        buf.writeInt(playerId);
        buf.writeInt(dimensionId);
        buf.writeByte(Type);
        buf.writeInt(ItemId);
        buf.writeInt(Itemdmg);
        buf.writeInt(entityId);
        ByteBufUtils.writeUTF8String(buf, Inode);
        ByteBufUtils.writeUTF8String(buf, "@");
        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumcraft", buf);
        mc.thePlayer.sendQueue.addToSendQueue(packet);
    }

    public ItemStack getPickBlock(Block block,MovingObjectPosition target, World world, int x, int y, int z)
    {
        Item item = block.getItem(world, x, y, z);

        if (item == null)
        {
            return null;
        }

        Block block2 = item instanceof ItemBlock && !block.isFlowerPot() ? Block.getBlockFromItem(item) : block;
        return new ItemStack(item, 1, block2.getDamageValue(world, x, y, z));
    }

}
