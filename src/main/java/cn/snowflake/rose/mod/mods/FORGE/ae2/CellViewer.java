package cn.snowflake.rose.mod.mods.FORGE.ae2;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventKey;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.snowflake.rose.notification.Notification;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.injection.ClientLoader;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class CellViewer extends Module {

    public CellViewer(){
        super("CellViewer","Cell Viewer", Category.FORGE);
        try {
            Class.forName("appeng.items.storage.ItemBasicStorageCell");
        } catch (Exception e) {
            setWorking(false);
        }
    }

    @Override
    public String getDescription() {
        return "查看存储元件(鼠标移动到存储元件按小键盘0)!";
    }


    @EventTarget
    public void onKey(EventKey ek) {
        try {
            if (ek.getKey() == Keyboard.KEY_NUMPAD0) {
                GuiScreen screen = mc.currentScreen;
                ItemStack cell = null;
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
                                cell = container.inventorySlots.inventorySlots.get(i) == null ? null : ((Slot) container.inventorySlots.inventorySlots.get(i)).getStack();
                            }
                        }
                    } catch (Exception ex) {
                        Client.instance.getNotificationManager().addNotification(this,"\247cError", Notification.Type.ERROR);
                    }
                }
                if (cell == null) {
                    return;
                }
                if (!(Class.forName("appeng.items.storage.ItemBasicStorageCell").isInstance(cell.getItem()))) {
                    Client.instance.getNotificationManager().addNotification(this,"\247cNot a cell", Notification.Type.ERROR);
                    return;
                }
                NBTTagCompound tag = cell.stackTagCompound;
                if (tag == null) {
                    Client.instance.getNotificationManager().addNotification(this,"\247Cell is empty (new)", Notification.Type.INFO);
                    return;
                }
                try {
                    ArrayList<ItemStack> stacks = new ArrayList<>();
                    int count = tag.getShort("it");
                    for (int i = 0; i < count; i++) {
                        ItemStack stack = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("#" + i));
                        stack.stackSize = (int) tag.getCompoundTag("#" + i).getLong("Cnt");
                        if (stack.stackTagCompound == null) {
                            stack.stackTagCompound = new NBTTagCompound();
                            stack.stackTagCompound.setString("render-cellviewer", "ok");
                        }
                        stacks.add(stack);
                    }
                    mc.displayGuiScreen(new CellViewerGui(new CellViewerContainer(stacks.toArray(new ItemStack[stacks.size()]), cell.getDisplayName())));
                } catch (Exception e) {
                    Client.instance.getNotificationManager().addNotification(this,"\247bError", Notification.Type.ERROR);
                }
            }
        } catch (Exception ignored) {

        }
    }


    private class CellViewerGui extends GuiContainer {

        private final CellViewerContainer container;
        private GuiButton buttonLeft;
        private GuiButton buttonRight;

        public CellViewerGui(CellViewerContainer container) {
            super(container);
            CellViewerGui.itemRender = new CellViewerRenderItem();
            this.container = container;
            this.xSize = 256;
            this.ySize = 256;

        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            int startX = (this.width - this.xSize) / 2;
            int startY = (this.height - this.ySize) / 2;
            drawBack(startX, startY, xSize, ySize);
            int x = 0;
            int y = 0;
            for (Slot get : container.slots.get(container.currentPage)) {
                drawSlotBack(startX + 11 + x * 18, startY + 17 + y * 18);
                x++;
                y += x / 13;
                x %= 13;
            }
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int p1, int p2) {
            mc.fontRenderer.drawString(container.containerName + " - " + String.valueOf(container.inventorySlots.size()) + " slots", 12, 6,getColor(64, 64, 64));
            mc.fontRenderer.drawString("Page " + String.valueOf(container.currentPage + 1), 128 - mc.fontRenderer.getStringWidth("Page " + String.valueOf(container.currentPage + 1)) / 2, 230, getColor(64, 64, 64));
        }

        @SuppressWarnings("unchecked")
        @Override
        public void initGui() {
            super.initGui();
            int startX = (this.width - this.xSize) / 2;
            int startY = (this.height - this.ySize) / 2;
            buttonLeft = new GuiButton(1, startX + 20, startY + 224, 20, 20, "<");
            buttonLeft.enabled = container.currentPage > 0;
            this.buttonList.add(buttonLeft);
            buttonRight = new GuiButton(2, startX + 216, startY + 224, 20, 20, ">");
            buttonRight.enabled = container.currentPage < (container.slots.size() - 1);
            this.buttonList.add(buttonRight);
        }

        @Override
        protected void actionPerformed(GuiButton b) {
            if (b.id == 1) {
                container.setPage(container.currentPage - 1);
                buttonLeft.enabled = container.currentPage > 0;
                buttonRight.enabled = container.currentPage < (container.slots.size() - 1);
            }
            if (b.id == 2) {
                container.setPage(container.currentPage + 1);
                buttonLeft.enabled = container.currentPage > 0;
                buttonRight.enabled = container.currentPage < (container.slots.size() - 1);
            }
        }

        @Override
        protected void handleMouseClick(Slot p_146984_1_, int p_146984_2_, int p_146984_3_, int p_146984_4_) {

        }

    }

    private class CellViewerContainer extends Container {

        public int currentPage = 0;

        public ItemStack[] inventory;

        public ArrayList<ArrayList<Slot>> slots = new ArrayList<>();

        public String containerName;

        public CellViewerContainer(ItemStack[] inventory, String containerName) {
            this.containerName = containerName;
            this.inventory = inventory;
            int x = 0;
            int y = 0;
            int page = 0;
            for (int i = 0; i < inventory.length; i++) {
                if (slots.size() == page) {
                    slots.add(new ArrayList<>());
                }
                Slot slot = new CellViewerSlot(inventory[i], i, page == currentPage ? 12 + x * 18 : -2000, page == currentPage ? 18 + y * 18 : -2000);
                slots.get(page).add(slot);
                this.addSlotToContainer(slot);
                this.putStackInSlot(i, inventory[i]);
                x++;
                y += x / 13;
                x %= 13;
                page += y / 11;
                y %= 11;
            }
            if (slots.isEmpty()) {
                slots.add(new ArrayList<>());
            }
        }

        @Override
        public void putStackInSlot(int p_75141_1_, ItemStack p_75141_2_) {
        }

        public void setPage(int pageId) {
            if (pageId < 0 || pageId >= slots.size()) {
                return;
            }
            slots.get(currentPage).stream().map((s) -> {
                s.xDisplayPosition = -2000;
                return s;
            }).forEachOrdered((s) -> {
                s.yDisplayPosition = -2000;
            });
            currentPage = pageId;
            int x = 0;
            int y = 0;
            for (int i = 0; i < slots.get(currentPage).size(); i++) {
                slots.get(currentPage).get(i).xDisplayPosition = 12 + x * 18;
                slots.get(currentPage).get(i).yDisplayPosition = 18 + y * 18;
                x++;
                y += x / 13;
                x %= 13;
            }
        }

        @Override
        public boolean canInteractWith(EntityPlayer p_75145_1_) {
            return true;
        }

    }

    private class CellViewerSlot extends Slot {

        private final ItemStack is;

        public CellViewerSlot(ItemStack is, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(null, p_i1824_2_, p_i1824_3_, p_i1824_4_);
            this.is = is;
        }

        public boolean isValidItem(ItemStack is) {
            return true;
        }

        @Override
        public ItemStack getStack() {
            return is;
        }

        @Override
        public void putStack(ItemStack p_75215_1_) {

        }

        @Override
        public void onSlotChanged() {

        }

        @Override
        public int getSlotStackLimit() {
            return is.getMaxStackSize();
        }

        /**
         * Decrease the size of the stack in slot (first int arg) by the amount
         * of the second int arg. Returns the new stack.
         */
        @Override
        public ItemStack decrStackSize(int p_75209_1_) {
            return is;
        }

        @Override
        public boolean isSlotInInventory(IInventory p_75217_1_, int p_75217_2_) {
            return true;
        }
    }

    private class CellViewerRenderItem extends RenderItem {

        @Override
        public void renderItemOverlayIntoGUI(FontRenderer p_94148_1_, TextureManager p_94148_2_, ItemStack p_94148_3_, int p_94148_4_, int p_94148_5_, String p_94148_6_) {
            if (p_94148_3_ != null) {
                boolean renderSmall = p_94148_3_.stackTagCompound != null && "ok".equals(p_94148_3_.stackTagCompound.getString("render-cellviewer"));

                if (p_94148_3_.isItemDamaged()) {
                    double health = (double)p_94148_3_.getItemDamageForDisplay() / (double)p_94148_3_.getMaxDamage();
                    int j1 = (int) Math.round(13.0D - health * 13.0D);
                    int k = (int) Math.round(255.0D - health * 255.0D);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    GL11.glDisable(GL11.GL_BLEND);
                    Tessellator tessellator = Tessellator.instance;
                    int l = 255 - k << 16 | k << 8;
                    int i1 = (255 - k) / 4 << 16 | 16128;
                    this.renderQuad(tessellator, p_94148_4_ + 2, p_94148_5_ + 13, 13, 2, 0);
                    this.renderQuad(tessellator, p_94148_4_ + 2, p_94148_5_ + 13, 12, 1, i1);
                    this.renderQuad(tessellator, p_94148_4_ + 2, p_94148_5_ + 13, j1, 1, l);
                    //GL11.glEnable(GL11.GL_BLEND); // Forge: Disable Bled because it screws with a lot of things down the line.
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }

                if (p_94148_3_.stackSize > 1 || p_94148_6_ != null || renderSmall) {
                    String s1 = p_94148_6_ == null ? String.valueOf(p_94148_3_.stackSize) : p_94148_6_;
                    if (renderSmall) {
                        s1 = getStringOfNum(p_94148_3_.stackSize);
                    }
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glDisable(GL11.GL_BLEND);
                    if (renderSmall) {
                        GL11.glScalef(.5f, .5f, .5f);
                    }
                    int textX = p_94148_4_ + 19 - 2 - p_94148_1_.getStringWidth(s1);
                    int textY = p_94148_5_ + 6 + 3;
                    if (renderSmall) {
                        textX = p_94148_4_ * 2 + 32 - p_94148_1_.getStringWidth(s1) - 2;
                        textY = p_94148_5_ * 2 + 23;
                    }
                    p_94148_1_.drawStringWithShadow(s1, textX, textY, 16777215);
                    if (renderSmall) {
                        GL11.glScalef(2f, 2f, 2f);
                    }
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                }
            }
        }

        private String getStringOfNum(int num) {
            if (num < 10000) {
                return String.valueOf(num);
            }
            num /= 1000;
            if (num < 1000) {
                return String.valueOf(num) + "K";
            }
            num /= 1000;
            if (num < 1000) {
                return String.valueOf(num) + "M";
            }
            num /= 1000;
            return String.valueOf(num) + "B";
        }

        private void renderQuad(Tessellator p_77017_1_, int p_77017_2_, int p_77017_3_, int p_77017_4_, int p_77017_5_, int p_77017_6_) {
            p_77017_1_.startDrawingQuads();
            p_77017_1_.setColorOpaque_I(p_77017_6_);
            p_77017_1_.addVertex((p_77017_2_), (p_77017_3_), 0.0D);
            p_77017_1_.addVertex((p_77017_2_), (p_77017_3_ + p_77017_5_), 0.0D);
            p_77017_1_.addVertex((p_77017_2_ + p_77017_4_), (p_77017_3_ + p_77017_5_), 0.0D);
            p_77017_1_.addVertex((p_77017_2_ + p_77017_4_), (p_77017_3_), 0.0D);
            p_77017_1_.draw();
        }
    }

    public static void drawBack(int x, int y, int sX, int sY) {
        sX = Math.max(sX, 8);
        sY = Math.max(sY, 8);
        drawRect(x + 3, y + 3, x + sX - 3, y + sY - 3, getColor(198, 198, 198));
        drawRect(x + 3, y + 3, x + 4, y + 4, getColor(255, 255, 255));
        drawRect(x + sX - 4, y + sY - 4, x + sX - 3, y + sY - 3, getColor(85, 85, 85));
        drawRect(x + 1, y + 2, x + 3, y + sY - 3, getColor(255, 255, 255));
        drawRect(x + 2, y + 1, x + sX - 3, y + 3, getColor(255, 255, 255));
        drawRect(x + 2, y + sY - 3, x + 3, y + sY - 2, getColor(198, 198, 198));
        drawRect(x + sX - 3, y + 2, x + sX - 2, y + 3, getColor(198, 198, 198));
        drawRect(x + 3, y + sY - 3, x + sX - 2, y + sY - 1, getColor(85, 85, 85));
        drawRect(x + sX - 3, y + 3, x + sX - 1, y + sY - 2, getColor(85, 85, 85));

        drawRect(x + 2, y, x + sX - 3, y + 1, getColor(0, 0, 0));
        drawRect(x, y + 2, x + 1, y + sY - 3, getColor(0, 0, 0));
        drawRect(x + 1, y + 1, x + 2, y + 2, getColor(0, 0, 0));

        drawRect(x + sX - 1, y + 3, x + sX, y + sY - 2, getColor(0, 0, 0));
        drawRect(x + 3, y + sY - 1, x + sX - 2, y + sY, getColor(0, 0, 0));
        drawRect(x + sX - 2, y + sY - 2, x + sX - 1, y + sY - 1, getColor(0, 0, 0));

        drawRect(x + 1, y + sY - 3, x + 2, y + sY - 2, getColor(0, 0, 0));
        drawRect(x + 2, y + sY - 2, x + 3, y + sY - 1, getColor(0, 0, 0));

        drawRect(x + sX - 3, y + 1, x + sX - 2, y + 2, getColor(0, 0, 0));
        drawRect(x + sX - 2, y + 2, x + sX - 1, y + 3, getColor(0, 0, 0));
    }

    public static int getColor(int r, int g, int b) {
        return 255 << 24 | r << 16 | g << 8 | b;
    }
    public static void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor) {

        float alpha = (paramColor >> 24 & 255) / 255.0f;
        float red = (paramColor >> 16 & 255) / 255.0f;
        float green = (paramColor >> 8 & 255) / 255.0f;
        float blue = (paramColor & 255) / 255.0f;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d(paramXEnd, paramYStart);
        GL11.glVertex2d(paramXStart, paramYStart);
        GL11.glVertex2d(paramXStart, paramYEnd);
        GL11.glVertex2d(paramXEnd, paramYEnd);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
    public static void drawSlotBack(float x, float y) {
        drawSlotBack(x, y, 18, 18);
    }
    public static void drawSlotBack(float x, float y, float sx, float sy) {
        drawRect(x, y, x + sx, y + sy, getColor(139, 139, 139));
        drawRect(x, y, x + sx, y + 1, getColor(55, 55, 55));
        drawRect(x, y, x + 1, y + sy, getColor(55, 55, 55));
        drawRect(x + sx - 1, y, x + sx, y + sy, getColor(255, 255, 255));
        drawRect(x, y + sy - 1, x + sx, y + sy, getColor(255, 255, 255));
        drawRect(x + sx - 1, y, x + sx, y + 1, getColor(139, 139, 139));
        drawRect(x, y + sy - 1, x + 1, y + sy, getColor(139, 139, 139));
    }
}
