package cn.snowflake.rose.ui.skeet.ui;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.mod.mods.RENDER.ClickGui;
import cn.snowflake.rose.ui.skeet.SkeetClickGui;
import cn.snowflake.rose.ui.skeet.components.Button;
import cn.snowflake.rose.ui.skeet.components.Checkbox;
import cn.snowflake.rose.ui.skeet.components.*;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.mcutil.GlStateManager;
import cn.snowflake.rose.utils.render.ColorValue;
import cn.snowflake.rose.utils.render.Colors;
import cn.snowflake.rose.utils.render.Opacity;
import cn.snowflake.rose.utils.render.RenderUtil;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class SkeetMenu extends UI {
    public Opacity opacity = new Opacity(255);
    private Minecraft mc = Minecraft.getMinecraft();
    private static int GLwheel = 0;
    
    @Override
    public void mainConstructor(SkeetClickGui p0) {
    	
    }
    
    @Override
    public void mainPanelDraw(MainPanel panel, int p0, int p1) {
        RenderUtil.rectangleBordered((double)((double)(panel.x + panel.dragX) - 0.3), (double)((double)(panel.y + panel.dragY) - 0.3), (double)((double)(panel.x + 340.0f + panel.dragX) + 0.5), (double)((double)(panel.y + 310.0f + panel.dragY) + 0.3), (double)0.5, (int) Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangleBordered((double)(panel.x + panel.dragX), (double)(panel.y + panel.dragY), (double)(panel.x + 340.0f + panel.dragX), (double)(panel.y + 310.0f + panel.dragY), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)60, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangleBordered((double)(panel.x + panel.dragX + 2.0f), (double)(panel.y + panel.dragY + 2.0f), (double)(panel.x + 340.0f + panel.dragX - 2.0f), (double)(panel.y + 310.0f + panel.dragY - 2.0f), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)60, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangleBordered((double)((double)(panel.x + panel.dragX) + 0.6), (double)((double)(panel.y + panel.dragY) + 0.6), (double)((double)(panel.x + 340.0f + panel.dragX) - 0.5), (double)((double)(panel.y + 310.0f + panel.dragY) - 0.6), (double)1.3, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)40, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangleBordered((double)((double)(panel.x + panel.dragX) + 2.5), (double)((double)(panel.y + panel.dragY) + 2.5), (double)((double)(panel.x + 340.0f + panel.dragX) - 2.5), (double)((double)(panel.y + 310.0f + panel.dragY) - 2.5), (double)0.5, (int)Colors.getColor((int)22, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)22, (int)((int)this.opacity.getOpacity())));
        RenderUtil.drawGradientSideways((double)(panel.x + panel.dragX + 3.0f), (double)(panel.y + panel.dragY + 3.0f), (double)(panel.x + 178.0f + panel.dragX - 3.0f), (double)(panel.dragY + panel.y + 4.0f), (int)Colors.getColor((int)55, (int)177, (int)218, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)204, (int)77, (int)198, (int)((int)this.opacity.getOpacity())));
        RenderUtil.drawGradientSideways((double)(panel.x + panel.dragX + 175.0f), (double)(panel.y + panel.dragY + 3.0f), (double)(panel.x + 340.0f + panel.dragX - 3.0f), (double)(panel.dragY + panel.y + 4.0f), (int)Colors.getColor((int)204, (int)77, (int)198, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)204, (int)227, (int)53, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangle((double)(panel.x + panel.dragX + 3.0f), (double)((double)(panel.y + panel.dragY) + 3.3), (double)(panel.x + 340.0f + panel.dragX - 3.0f), (double)(panel.dragY + panel.y + 4.0f), (int)Colors.getColor((int)0));
        RenderUtil.drawGradient((double)-1.0, (double)-1.0, (double)-1.0, (double)-1.0, (int)Colors.getColor((int)255, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)255, (int)((int)this.opacity.getOpacity())));
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
//        this.mc.getTextureManager().bindTexture(this.texture);
        GlStateManager.translate((double)((double)(panel.x + panel.dragX) + 2.5), (double)(panel.dragY + panel.y + 3.0f), (double)0.0);
//        mc.ingameGUI.drawTexturedModelRectFromIcon((int)1.0, (int)1.0, 0.0f, 0.0f,  (int)333.5,  (int)303.0, 325.0f, 275.0f);
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.popMatrix();
        float y = 15.0f;
        for (int i = 0; i <= panel.typeButton.size(); ++i) {
            if (i > panel.typeButton.size() - 1 || !((CategoryButton)panel.typeButton.get((int)i)).categoryPanel.visible || i <= 0) continue;
            y = 15 + i * 40;
        }
        GlStateManager.pushMatrix();
        this.prepareScissorBox(panel.x + panel.dragX + 3.0f, panel.y + panel.dragY + 4.5f, panel.x + panel.dragX + 40.0f, panel.y + panel.dragY + y + 1.0f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.rectangleBordered((double)(panel.x + panel.dragX + 2.0f), (double)(panel.y + panel.dragY + 3.0f), (double)(panel.x + panel.dragX + 40.0f), (double)(panel.y + panel.dragY + y), (double)0.5, (int)Colors.getColor((int)0, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)48, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangle((double)(panel.x + panel.dragX + 3.0f), (double)(panel.y + panel.dragY + 4.0f), (double)(panel.x + panel.dragX + 39.0f), (double)(panel.y + panel.dragY + y - 1.0f), (int)Colors.getColor((int)12, (int)((int)this.opacity.getOpacity())));
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.prepareScissorBox(panel.x + panel.dragX + 3.0f, panel.y + panel.dragY + y + 40.0f, panel.x + panel.dragX + 40.0f, panel.y + panel.dragY + 308.0f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.rectangleBordered((double)(panel.x + panel.dragX + 2.0f), (double)(panel.y + panel.dragY + y + 40.0f), (double)(panel.x + panel.dragX + 40.0f), (double)(panel.y + panel.dragY + 308.0f), (double)0.5, (int)Colors.getColor((int)0, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)48, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangle((double)(panel.x + panel.dragX + 3.0f), (double)(panel.y + panel.dragY + y + 41.0f), (double)(panel.x + panel.dragX + 39.0f), (double)((double)(panel.y + panel.dragY) + 307.5), (int)Colors.getColor((int)12, (int)((int)this.opacity.getOpacity())));
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
            for (SLButton button : panel.slButtons) {
                button.draw((float)p0, (float)p1);
            }
            for (CategoryButton button : panel.typeButton) {
                button.draw((float)p0, (float)p1);
            }
            ScaledResolution rs = new ScaledResolution(this.mc,this.mc.displayWidth,this.mc.displayHeight);
            if (panel.dragging) {
                panel.dragX = (int) ((float)p0 - panel.lastDragX);
                panel.dragY = (int) ((float)p1 - panel.lastDragY);
            }
            if (panel.dragX > (float)(rs.getScaledWidth() - 402)) {
                panel.dragX = rs.getScaledWidth() - 402;
            }
            if (panel.dragX < -48.0f) {
                panel.dragX = (int) -48.0f;
            }
            if (panel.dragY > (float)(rs.getScaledHeight() - 362)) {
                panel.dragY = rs.getScaledHeight() - 362;
            }
            if (panel.dragY < -48.0f) {
                panel.dragY = (int) -48.0f;
            }
    }

    private void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution rs = new ScaledResolution(this.mc,this.mc.displayWidth,this.mc.displayHeight);
        int factor = rs.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((rs.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
    
    @Override
    public void mainPanelKeyPress(MainPanel panel, int key) {
        panel.typeButton.forEach(o -> o.categoryPanel.buttons.forEach(b -> b.keyPressed(key)));
    }
    
    @Override
    public void panelConstructor(MainPanel mainPanel, float x, float y) {
        int y1 = 15;
        for (Category types : Category.values()) {
            mainPanel.typeButton.add((CategoryButton)new CategoryButton(mainPanel, types.name(), x + 3.0f, y + (float)y1));
            y += 40.0f;
        }
        mainPanel.typeButton.add((CategoryButton)new CategoryButton(mainPanel, "Title", x + 3.0f, y + (float)y1));
        ((CategoryButton)mainPanel.typeButton.get((int)0)).enabled = true;
        ((CategoryButton)mainPanel.typeButton.get((int)0)).categoryPanel.visible = true;
    }
    
    @Override
    public void panelMouseClicked(MainPanel mainPanel, int x, int y, int z) {
        if (this.opacity.getOpacity() < 220.0f) {
            return;
        }
        if ((float)x >= mainPanel.x + mainPanel.dragX && (float)y >= mainPanel.dragY + mainPanel.y && (float)x <= mainPanel.dragX + mainPanel.x + 400.0f && (float)y <= mainPanel.dragY + mainPanel.y + 12.0f && z == 0) {
            mainPanel.dragging = true;
            mainPanel.lastDragX = (float)x - mainPanel.dragX;
            mainPanel.lastDragY = (float)y - mainPanel.dragY;
        }
        mainPanel.typeButton.forEach(c -> {
            c.mouseClicked(x, y, z);
            c.categoryPanel.mouseClicked(x, y, z);
        });
        mainPanel.slButtons.forEach(slButton -> slButton.mouseClicked(x, y, z));
    }
    
    @Override
    public void panelMouseMovedOrUp(MainPanel mainPanel, int x, int y, int z) {
        if (this.opacity.getOpacity() < 220.0f) {
            return;
        }
        if (z == 0) {
            mainPanel.dragging = false;
        }
        for (CategoryButton button : mainPanel.typeButton) {
            button.mouseReleased(x, y, z);
        }
    }
    
    @Override
    public void categoryButtonConstructor(CategoryButton p0, MainPanel p1) {
        p0.categoryPanel = new CategoryPanel(p0.name, p0, 0.0f, 0.0f);
    }
    
    @Override
    public void categoryButtonMouseClicked(CategoryButton p0, MainPanel p1, int p2, int p3, int p4) {
        if ((float)p2 >= p0.x + p1.dragX && (float)p3 >= p1.dragY + p0.y && (float)p2 <= p1.dragX + p0.x + 40.0f && (float)p3 <= p1.dragY + p0.y + 40.0f && p4 == 0) {
            for (CategoryButton button : p1.typeButton) {
                if (button == p0) {
                    p0.enabled = true;
                    p0.categoryPanel.visible = true;
                    continue;
                }
                button.enabled = false;
                button.categoryPanel.visible = false;
            }
        }
    }
    
    @Override
    public void categoryButtonDraw(CategoryButton p0, float p2, float p3) {
        int color = p0.enabled ? Colors.getColor((int)210, (int)((int)this.opacity.getOpacity())) : Colors.getColor((int)91, (int)((int)this.opacity.getOpacity()));
        if (p2 >= p0.x + p0.panel.dragX && p3 >= p0.y + p0.panel.dragY && p2 <= p0.x + p0.panel.dragX + 40.0f && p3 <= p0.y + p0.panel.dragY + 40.0f && !p0.enabled) {
            color = Colors.getColor((int)165);
        }
        if (p0.name.equalsIgnoreCase("Combat")) {
            Client.cheaticons.drawCenteredString("D", p0.x + 19.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Player")) {
            Client.cheaticons.drawCenteredString("B", p0.x + 18.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Movement")) {
            Client.cheaticons.drawCenteredString("A", p0.x + 19.0f + p0.panel.dragX, p0.y + 22.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Forge")) {
            Client.cheaticons.drawCenteredString("E", p0.x + 18.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("World")) {
            Client.cheaticons.drawCenteredString("G", p0.x + 18.5f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if (p0.name.equalsIgnoreCase("Render")) {
            Client.cheaticons.drawCenteredString("C", p0.x + 19.0f + p0.panel.dragX, p0.y + 20.0f + p0.panel.dragY, color);
        } else if(p0.name.equalsIgnoreCase("Title")) {
            Client.cheaticons.drawStringWithShadow("F", p0.x + 8.0f + p0.panel.dragX, p0.y + 13.0f + p0.panel.dragY, color);
        }
        //Client.cheaticons.drawStringWithShadow(Character.toString(p0.name.charAt(0)), p0.x + 12.0f + p0.panel.dragX, p0.y + 13.0f + p0.panel.dragY, color);
        if (p0.enabled) {
            p0.categoryPanel.draw(p2, p3);
        }
    }

    @Override
    public void categoryPanelConstructor(CategoryPanel categoryPanel, CategoryButton categoryButton, float x, float y) {
        float noSets;
        float biggestY;
        float xOff = 50.0f + categoryButton.panel.x;
        float yOff = 15.0f + categoryButton.panel.y;
        if (categoryButton.name.equalsIgnoreCase("COMBAT")) {
            biggestY = 34.0f; // 34
            noSets = 0.0f;
            ArrayList<Module> modslist = ModManager.modList;
            modslist.sort(Comparator.comparingInt(Module::getValueSize).reversed());
            for (int i = 0;i < modslist.size(); i++) {
                Module module = modslist.get(i);
                if (module.getCategory() != Category.COMBAT) continue;
                y = 20.0f;
                if (module.getName().equalsIgnoreCase("Velocity")){
                    yOff -= yOff % 5;
                }
                if (!module.openValues) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5f, yOff + 10.0f, module));
                    float x1 = 0.5f;
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	String sname = val.getValueName().split("_")[1];
                            if (val.isValueBoolean) {
                            	categoryPanel.checkboxes.add(new Checkbox(categoryPanel, sname, xOff + x1, yOff + y, val));
                                if ((x1 += 44.0f) != 88.5f) continue;
                                x1 = 0.5f;
                                y += 10.0f;
                            }
                    	}
                    }
                    if (x1 == 44.5f) {
                        y += 10.0f;
                    }
                    x1 = 0.5f;
                    int tY = 0;
                    ArrayList<Value> sliders = new ArrayList<Value>();
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueDouble) {
                                sliders.add(val);
                            }
                    	}
                    }
                    sliders.sort(Comparator.comparing(Value::getValueName));
                    for (Value val : sliders) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, val));
                        tY = 12;
                        if ((x1 += 44.0f) != 88.5f) continue;
                        tY = 0;
                        x1 = 0.5f;
                        y += 12.0f;
                    }
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                        		if (x1 == 44.5f) {
                                    y += 14.0f;
                                }
                                x1 = 0.5f;
                        	}  
                    	}  
                    }
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                    		if (val.isValueMode) {
                           		categoryPanel.dropdownBoxes.add(new DropdownBox(val, xOff + x1, yOff + y + 4.0f, categoryPanel));
                                tY = 17;
                                if ((x1 += 44.0f) == 88.5f) {
                                    y += 17.0f;
                                    tY = 0;
                                    x1 = 0.5f;
                                }
                    		}
                    	}
                    }
                    float ySize = (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f;
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, ySize));
                    xOff += 95.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    if (noSets >= 240.0f) {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets - 240.0f, 345.0f, module));
                    } else {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    }
                    noSets += 40.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 310.0f)) continue;
                xOff = 50.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY; //       yOff += y;
            }
        }
        if (categoryButton.name == "PLAYER") {
            biggestY = 34.0f;
            noSets = 0.0f;
            for (Module module : ModManager.modList) {
                if (module.getCategory() != Category.PLAYER) continue;
                y = 20.0f;
                if (!module.openValues) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5f, yOff + 10.0f, module));
                    float x1 = 0.5f;
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	String sname = val.getValueName().split("_")[1];
                        	if (val.isValueBoolean) {
                        		categoryPanel.checkboxes.add(new Checkbox(categoryPanel, sname, xOff + x1, yOff + y, val));
                                if ((x1 += 44.0f) != 88.5f) continue;
                                x1 = 0.5f;
                                y += 10.0f;
                        	}
                    	}
                    }
                    if (x1 == 44.5f) {
                        y += 10.0f;
                    }
                    x1 = 0.5f;
                    int tY = 0;
                    ArrayList<Value> sliders = new ArrayList<Value>();
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                           	if (val.isValueDouble) {
                        		sliders.add(val);
                        	}
                    	}
                    }
                    sliders.sort(Comparator.comparing(Value::getValueName));
                    for (Value<?> setting : sliders) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 44.0f) != 88.5f) continue;
                        tY = 0;
                        x1 = 0.5f;
                        y += 12.0f;
                    }
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                                if (x1 == 44.5f) {
                                    y += 14.0f;
                                }
                                x1 = 0.5f;
                        	}
                    	}
                    }
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                        		categoryPanel.dropdownBoxes.add(new DropdownBox(val, xOff + x1, yOff + y + 4.0f, categoryPanel));
                                tY = 17;
                                if ((x1 += 44.0f) == 88.5f) {
                                    y += 17.0f;
                                    tY = 0;
                                    x1 = 0.5f;
                                }
                        	}
                    	}
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 95.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    if (noSets >= 240.0f) {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets - 240.0f, 345.0f, module));
                    } else {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    }
                    noSets += 40.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 310.0f)) continue;
                xOff = 50.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name == "MOVEMENT") {
            biggestY = 34.0f;
            noSets = 0.0f;
            for (Module module : ModManager.modList) {
                if (module.getCategory() != Category.MOVEMENT) continue;
                y = 20.0f;
                if (!module.openValues) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5f, yOff + 10.0f, module));
                    float x1 = 0.5f;
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	String sname = val.getValueName().split("_")[1];
                        	if (val.isValueBoolean) {
                        		categoryPanel.checkboxes.add(new Checkbox(categoryPanel, sname, xOff + x1, yOff + y, val));
                                if ((x1 += 44.0f) != 88.5f) continue;
                                x1 = 0.5f;
                                y += 10.0f;
                        	}
                    	}
                    }
                    if (x1 == 44.5f) {
                        y += 10.0f;
                    }
                    x1 = 0.5f;
                    int tY = 0;
                    ArrayList<Value> sliders = new ArrayList();
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                         	if (val.isValueDouble) {
                       		 sliders.add(val);
                       	}  
                    	}
                    }
                    sliders.sort(Comparator.comparing(Value::getValueName));
                    for (Value<?> setting : sliders) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 44.0f) != 88.5f) continue;
                        tY = 0;
                        x1 = 0.5f;
                        y += 12.0f;
                    }
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                                if (x1 == 44.5f) {
                                    y += 14.0f;
                                }
                                x1 = 0.5f;
                        	}
                    	}
                    }
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                       		 categoryPanel.dropdownBoxes.add(new DropdownBox(val, xOff + x1, yOff + y + 4.0f, categoryPanel));
                                tY = 17;
                                if ((x1 += 44.0f) == 88.5f) {
                                    y += 17.0f;
                                    tY = 0;
                                    x1 = 0.5f;
                                }
                       	 }
                    	}
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 95.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    if (noSets >= 240.0f) {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets - 240.0f, 345.0f, module));
                    } else {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    }
                    noSets += 40.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 310.0f)) continue;
                xOff = 50.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name == "RENDER") {
            biggestY = 34.0f;
            noSets = 0.0f;
            for (Module module : ModManager.modList) {
                if (module.getCategory() != Category.RENDER) continue;
                y = 20.0f;
                if (!module.openValues) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5f, yOff + 10.0f, module));
                    float x1 = 0.5f;
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	String sname = val.getValueName().split("_")[1];
                        	if (val.isValueBoolean) {
                                categoryPanel.checkboxes.add(new Checkbox(categoryPanel, sname, xOff + x1, yOff + y, val));
                                if ((x1 += 44.0f) != 88.5f) continue;
                                x1 = 0.5f;
                                y += 10.0f;
                        	}
                    	}
                    }
                    if (x1 == 44.5f) {
                        y += 10.0f;
                    }
                    x1 = 0.5f;
                    int tY = 0;
                    ArrayList<Value> sliders = new ArrayList();
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueDouble) {
                        		sliders.add(val);
                        	}
                    	}
                    }
                    sliders.sort(Comparator.comparing(Value::getValueName));
                    for (Value setting : sliders) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 44.0f) != 88.5f) continue;
                        tY = 0;
                        x1 = 0.5f;
                        y += 12.0f;
                    }
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueBoolean) {
                                if (x1 == 44.5f) {
                                    y += 14.0f;
                                }
                                x1 = 0.5f;
                        	}
                    	}
                    }
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                                categoryPanel.dropdownBoxes.add(new DropdownBox(val, xOff + x1, yOff + y + 4.0f, categoryPanel));
                                tY = 17;
                                if ((x1 += 44.0f) == 88.5f) {
                                    y += 17.0f;
                                    tY = 0;
                                    x1 = 0.5f;
                                }
                        	}
                    	}
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 95.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    if (noSets >= 240.0f) {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets - 240.0f, 345.0f, module));
                    } else {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    }
                    noSets += 40.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 310.0f)) continue;
                xOff = 50.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name == "WORLD") {
            biggestY = 34.0f;
            noSets = 0.0f;
            for (Module module : ModManager.modList) {
                if (module.getCategory() != Category.WORLD) continue;
                y = 20.0f;
                if (!module.openValues) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5f, yOff + 10.0f, module));
                    float x1 = 0.5f;
                    for (Value val : Value.list) {
                    	String sname = val.getValueName().split("_")[1];
                    	if (val.isValueBoolean) {
                    		if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                                categoryPanel.checkboxes.add(new Checkbox(categoryPanel, sname, xOff + x1, yOff + y, val));
                                if ((x1 += 44.0f) != 88.5f) continue;
                                x1 = 0.5f;
                                y += 10.0f;
                    		}
                    	}
                    }
                    if (x1 == 44.5f) {
                        y += 10.0f;
                    }
                    x1 = 0.5f;
                    int tY = 0;
                    ArrayList<Value> sliders = new ArrayList();
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueDouble) {
                                sliders.add(val);
                        	}
                    	}
                    }
                    sliders.sort(Comparator.comparing(Value::getValueName));
                    for (Value setting : sliders) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, setting));
                        tY = 12;
                        if ((x1 += 44.0f) != 88.5f) continue;
                        tY = 0;
                        x1 = 0.5f;
                        y += 12.0f;
                    }
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                                if (x1 == 44.5f) {
                                    y += 14.0f;
                                }
                                x1 = 0.5f;
                        	}
                    	}
                    }
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                                categoryPanel.dropdownBoxes.add(new DropdownBox(val, xOff + x1, yOff + y + 4.0f, categoryPanel));
                                tY = 17;
                                if ((x1 += 44.0f) != 88.5f) continue;
                                y += 17.0f;
                                tY = 0;
                                x1 = 0.5f;
                        	}
                    	}
                    }
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f));
                    xOff += 95.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    if (noSets >= 240.0f) {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets - 240.0f, 345.0f, module));
                    } else {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    }
                    noSets += 40.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 310.0f)) continue;
                xOff = 50.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY;
            }
        }
        if (categoryButton.name.equalsIgnoreCase("FORGE")) {
            biggestY = 34.0f; // 34
            noSets = 0.0f;
            ArrayList<Module> modslist = ModManager.modList;

            modslist.sort(Comparator.comparingInt(Module::getValueSize).reversed());

            for (int i = 0;i < modslist.size(); i++) {
                Module module = modslist.get(i);
                if (module.getCategory() != Category.FORGE) continue;
                y = 20.0f;
                if (!module.openValues) {
                    categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5f, yOff + 10.0f, module));
                    float x1 = 0.5f;
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	String sname = val.getValueName().split("_")[1];
                            if (val.isValueBoolean) {
                            	categoryPanel.checkboxes.add(new Checkbox(categoryPanel, sname, xOff + x1, yOff + y, val));
                                if ((x1 += 44.0f) != 88.5f) continue;
                                x1 = 0.5f;
                                y += 10.0f;
                            }
                    	}
                    }
                    if (x1 == 44.5f) {
                        y += 10.0f;
                    }
                    x1 = 0.5f;
                    int tY = 0;
                    ArrayList<Value> sliders = new ArrayList<Value>();
                    for (Value val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueDouble) {
                                sliders.add(val);
                            }
                    	}
                    }
                    sliders.sort(Comparator.comparing(Value::getValueName));
                    for (Value val : sliders) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0f, yOff + y + 4.0f, val));
                        tY = 12;
                        if ((x1 += 44.0f) != 88.5f) continue;
                        tY = 0;
                        x1 = 0.5f;
                        y += 12.0f;
                    }
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                        	if (val.isValueMode) {
                        		if (x1 == 44.5f) {
                                    y += 14.0f;
                                }
                                x1 = 0.5f;
                        	}  
                    	}  
                    }
                    for (Value<?> val : Value.list) {
                    	if(val.getValueName().split("_")[0].equalsIgnoreCase(module.getName())) {
                    		if (val.isValueMode) {
                           		categoryPanel.dropdownBoxes.add(new DropdownBox(val, xOff + x1, yOff + y + 4.0f, categoryPanel));
                                tY = 17;
                                if ((x1 += 44.0f) == 88.5f) {
                                    y += 17.0f;
                                    tY = 0;
                                    x1 = 0.5f;
                                }
                    		}
                    	}
                    }
                    float ySize = (y += (float)tY) == 34.0f ? 40.0f : y - 11.0f;
                    categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, ySize));
                    xOff += 95.0f;
                    if (y >= biggestY) {
                        biggestY = y;
                    }
                } else {
                    if (noSets >= 240.0f) {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets - 240.0f, 345.0f, module));
                    } else {
                        categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0f + categoryButton.panel.x + noSets, 330.0f, module));
                    }
                    noSets += 40.0f;
                }
                if (!(xOff > 20.0f + categoryButton.panel.y + 310.0f)) continue;
                xOff = 50.0f + categoryButton.panel.x;
                yOff += y == 20.0f && biggestY == 20.0f ? 26.0f : biggestY; //       yOff += y;
            }
        }
    }
    
    @Override
    public void categoryPanelMouseClicked(CategoryPanel categoryPanel, int p1, int p2, int p3) {
        boolean active = false;
        for (DropdownBox db : categoryPanel.dropdownBoxes) {
            if (!db.active) continue;
            db.mouseClicked(p1, p2, p3);
            active = true;
            break;
        }
        if (!active) {
            categoryPanel.dropdownBoxes.forEach(o -> o.mouseClicked(p1, p2, p3));
            for (Button button : categoryPanel.buttons) {
                button.mouseClicked(p1, p2, p3);
            }
            for (Checkbox checkbox : categoryPanel.checkboxes) {
                checkbox.mouseClicked(p1, p2, p3);
            }
            for (Slider slider : categoryPanel.sliders) {
                slider.mouseClicked(p1, p2, p3);
            }
            for (ColorPreview cp : categoryPanel.colorPreviews) {
                for (HSVColorPicker slider : cp.sliders) {
                    slider.mouseClicked(p1, p2, p3);
                }
            }
        }
    }
    
	int wtf;
	int wtf2;
	int wheel;
    @Override
    public void categoryPanelDraw(CategoryPanel categoryPanel, float x, float y) {
        ScaledResolution sr = new ScaledResolution(this.mc,this.mc.displayWidth,this.mc.displayHeight);
    	/*boolean hovering = Mouse.isButtonDown(0) && Mouse.getX() <= Minecraft.getMinecraft().displayWidth / 10 - 73 && Mouse.getX() >= 0  
    			&& Mouse.getY() < Minecraft.getMinecraft().displayHeight / 2;
    	boolean hovering2 = Mouse.isButtonDown(0) && Mouse.getX() <= Minecraft.getMinecraft().displayWidth / 10 - 73 && Mouse.getX() >= 0  
    			&& Mouse.getY() > Minecraft.getMinecraft().displayHeight / 2;
    	 if (hovering) {
    		wtf-=2;
    		System.out.println("Down Hover");
    	 } else if (hovering2) {
     		wtf+=2;
    		System.out.println("Up Hover");
    	 }
    	 RenderUtil.drawRect(0, 0, 5, sr.getScaledHeight() / 2 , Colors.RED.c);
    	 RenderUtil.drawRect(0, sr.getScaledHeight() / 2, 5, sr.getScaledHeight() , Colors.BLUE.c);
    	 Client.fs.drawString("U", -0.5f, sr.getScaledHeight() / 2- 12, -1);
    	 Client.fs.drawString("P", -0.5f, sr.getScaledHeight() / 2- 6, -1);
    	 Client.fs.drawString("D", -0.5f, sr.getScaledHeight() / 2 + 2, -1);
    	 Client.fs.drawString("N", -0.5f, sr.getScaledHeight() / 2 + 8, -1);*/
    	 wheel = Mouse.getDWheel();
			if (wheel < 0) {
				wtf-=20;
			}
			if (wheel > 0) {
				wtf+=20;
			}

			if (wtf > 0)
				wtf=0;
			if (categoryPanel.categoryButton.name == "COMBAT") {
				if (wtf < -280) {
					wtf=-280;
				}
			} else if (categoryPanel.categoryButton.name == "MOVEMENT") {
				if (wtf < -180) {
					wtf=-180;
				}
			} else if (categoryPanel.categoryButton.name == "PLAYER") {
				if (wtf < -100) {
					wtf=-100;
				}
			} else if (categoryPanel.categoryButton.name == "WORLD") {
				if (wtf < 0) {
					wtf=0;
				}
			} else if (categoryPanel.categoryButton.name == "RENDER") {
				if (wtf < -1180) {
					wtf=-1180;
				}
			}
        if (categoryPanel.categoryButton.name.equalsIgnoreCase("Title")) {
            float xOff = 100.0f + categoryPanel.categoryButton.panel.dragX - 2.5f;
            float yOff = 62.0f + categoryPanel.categoryButton.panel.dragY;
            RenderUtil.rectangleBordered((double)xOff, (double)(yOff - 6.0f), (double)(xOff + 280.0f), (double)(yOff + 200.0f), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
            RenderUtil.rectangleBordered((double)((double)xOff + 0.5), (double)((double)yOff - 5.5), (double)((double)(xOff + 280.0f) - 0.5), (double)((double)(yOff + 200.0f) - 0.5), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)48, (int)((int)this.opacity.getOpacity())));
            RenderUtil.rectangle((double)(xOff + 1.0f), (double)(yOff - 5.0f), (double)(xOff + 279.0f), (double)(yOff + 200.0f - 1.0f), (int)Colors.getColor((int)17, (int)((int)this.opacity.getOpacity())));
            Client.fs.drawStringWithShadow("Info", xOff + 5.0f, yOff-7.0f, Colors.getColor((int)220, (int)((int)this.opacity.getOpacity())));
            Client.fs.drawStringWithShadow("Cheat By Snowflake", xOff + 8.0f, yOff + 2.0f, Colors.getColor((int)220, (int)((int)this.opacity.getOpacity())));
        }
        int scaleFactor = 1;
        boolean flag = Minecraft.getMinecraft().func_152349_b();//isUnicode
        int i2 = Minecraft.getMinecraft().gameSettings.guiScale;

        if (i2 == 0)
        {
            i2 = 1000;
        }

        while (scaleFactor < i2 && Minecraft.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Minecraft.getMinecraft().displayHeight / (scaleFactor + 1) >= 240)
        {
            ++scaleFactor;
        }

        if (flag && scaleFactor % 2 != 0 && scaleFactor != 1)
        {
            --scaleFactor;
        }
        int x1 = (int) categoryPanel.categoryButton.panel.dragX * scaleFactor + 181;
        int y1 = (int) -categoryPanel.categoryButton.panel.dragY * scaleFactor + 122;
        GL11.glPushMatrix();
        if (Minecraft.getMinecraft().displayHeight == 480) {
        	y1 = (int) -categoryPanel.categoryButton.panel.dragY * scaleFactor - 235;
        } else {
        	y1 = (int) -categoryPanel.categoryButton.panel.dragY * scaleFactor + 122;
        }
        GL11.glScissor(x1, y1, 593, 607); 
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        //RenderUtil.drawFullscreenImage(new ResourceLocation("Invalid/Konachan.com - 284693 sample.jpg"));
        GL11.glTranslated(0, wtf, 0);
        GLwheel = wtf;
        for (ColorPreview cp : categoryPanel.colorPreviews) {
            cp.draw(x, y);
        }
        for (GroupBox groupBox : categoryPanel.groupBoxes) {
            groupBox.draw(x, y);
        }
        for (Button button : categoryPanel.buttons) {
            button.draw(x, y);
        }
        for (Checkbox checkbox : categoryPanel.checkboxes) {
            checkbox.draw(x, y);
        }
        for (Slider slider : categoryPanel.sliders) {
            slider.draw(x, y);
        }
        ArrayList<DropdownBox> list = new ArrayList<DropdownBox>(categoryPanel.dropdownBoxes);
        Collections.reverse(list);
        for (DropdownBox db : list) {
            db.draw(x, y);
        }
        for (DropdownBox db : list) {
            if (!db.active) continue;
            for (DropdownButton b : db.buttons) {
                b.draw(x, y);
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }
    

    public void categoryPanelMouseMovedOrUp(CategoryPanel categoryPanel, int x, int y, int button) {
        for (Slider slider : categoryPanel.sliders) {
            slider.mouseReleased(x, y, button);
        }
        for (ColorPreview cp : categoryPanel.colorPreviews) {
            for (HSVColorPicker slider : cp.sliders) {
                slider.mouseReleased(x, y, button);
            }
        }
    }

    public void groupBoxConstructor(GroupBox groupBox, float x, float y) {
    }

    public void groupBoxMouseClicked(GroupBox groupBox, int p1, int p2, int p3) {
    }

    public void groupBoxDraw(GroupBox groupBox, float x, float y) {
        float xOff = groupBox.x + groupBox.categoryPanel.categoryButton.panel.dragX - 2.5f;
        float yOff = groupBox.y + groupBox.categoryPanel.categoryButton.panel.dragY + 10.0f;
        RenderUtil.rectangleBordered((double)xOff, (double)(yOff - 6.0f), (double)(xOff + 90.0f), (double)(yOff + groupBox.ySize), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangleBordered((double)((double)xOff + 0.5), (double)((double)yOff - 5.5), (double)((double)(xOff + 90.0f) - 0.5), (double)((double)(yOff + groupBox.ySize) - 0.5), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)48, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangle((double)(xOff + 1.0f), (double)(yOff - 5.0f), (double)(xOff + 89.0f), (double)(yOff + groupBox.ySize - 1.0f), (int)Colors.getColor((int)17, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangle((double)(xOff + 5.0f), (double)(yOff - 6.0f), (double)(xOff + mc.fontRenderer.FONT_HEIGHT + 5.0f), (double)(yOff - 4.0f), (int)Colors.getColor((int)17, (int)((int)this.opacity.getOpacity())));
    }

    public void groupBoxMouseMovedOrUp(GroupBox groupBox, int x, int y, int button) {
    }

    public void buttonContructor(Button p0, CategoryPanel panel) {
    }
    
    public void buttonMouseClicked(Button p0, int p2, int p3, int p4, CategoryPanel panel) {
        if (panel.categoryButton.enabled) {
            boolean hovering;
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY; //TODO
            int hoverneed = GLwheel;
            boolean bl = hovering = (float)p2 >= p0.x + xOff 
            		&& (float)p3 >= p0.y + yOff + hoverneed
            		&& (float)p2 <= p0.x + 35.0f + xOff 
            		&& (float)p3 <= p0.y + 6.0f + yOff + hoverneed;
            if (hovering) {
                if (p4 == 0) {
                    if (!p0.isBinding) {
                    	p0.module.set(!p0.module.isEnabled());
                    } else {
                        p0.isBinding = false;
                    }
                } else if (p4 == 1) {
                    if (p0.isBinding) {
                    	p0.module.setKey(Keyboard.getEventKey());
                        p0.isBinding = false;
                    } else {
                        p0.isBinding = true;
                    }
                }
            } else if (p0.isBinding) {
                p0.isBinding = false;
            }
        }
    }
    @Override
    public void buttonDraw(Button p0, float p2, float p3, CategoryPanel panel) {
        if (panel.categoryButton.enabled) {
            GlStateManager.pushMatrix();
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            RenderUtil.rectangle((double)((double)(p0.x + xOff) + 0.6), (double)((double)(p0.y + yOff) + 0.6), (double)((double)(p0.x + 6.0f + xOff) + -0.6), (double)((double)(p0.y + 6.0f + yOff) + -0.6), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
            RenderUtil.drawGradient((double)(p0.x + xOff + 1.0f), (double)(p0.y + yOff + 1.0f), (double)(p0.x + 6.0f + xOff + -1.0f), (double)(p0.y + 6.0f + yOff + -1.0f), (int)Colors.getColor((int)76, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)51, (int)((int)this.opacity.getOpacity())));
            p0.enabled = p0.module.isEnabled();
            int hoverneed = GLwheel;  //TODO
            boolean hovering = p2 >= p0.x + xOff && p3 >= p0.y + yOff + hoverneed && p2 <= p0.x + 35.0f + xOff && p3 <= p0.y + 6.0f + yOff + hoverneed;
            GlStateManager.pushMatrix();
            Client.fs.drawStringWithShadow(p0.module.getName(), p0.x + xOff + 3.0f, p0.y + 0.5f + yOff - 7.0f, Colors.getColor((int)220, (int)((int)this.opacity.getOpacity())));
            Client.fs.drawStringWithShadow("Enable", p0.x + 7.6f + xOff, p0.y + 1.0f + yOff, Colors.getColor((int)220, (int)((int)this.opacity.getOpacity())));
            String meme = p0.module.getKey() > 0 ? ("[" +Keyboard.getKeyName(p0.module.getKey())+ "]") : "[-]";
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(p0.x + xOff + 28.0f), (float)(p0.y + 1.0f + yOff), (float)0.0f);
            Client.fs.drawStringWithShadow(meme, 0.0f, 0.0f, p0.isBinding ? Colors.getColor((int)216, (int)56, (int)56, (int)((int)this.opacity.getOpacity())) : Colors.getColor((int)75, (int)((int)this.opacity.getOpacity())));
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            if (p0.enabled) {
            	RenderUtil.drawGradient((double)(p0.x + xOff + 1.0f), (double)(p0.y + yOff + 1.0f), (double)(p0.x + xOff + 5.0f), (double)(p0.y + yOff + 5.0f), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), (int)((int)this.opacity.getOpacity())), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), (int)120));
            }
            if (hovering && !p0.enabled) {
            	RenderUtil.rectangle((double)(p0.x + xOff + 1.0f), (double)(p0.y + yOff + 1.0f), (double)(p0.x + xOff + 5.0f), (double)(p0.y + yOff + 5.0f), (int)Colors.getColor((int)255, (int)40));
            }
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public void buttonKeyPressed(Button button, int key) {
        if (button.isBinding && key != 0) {
            int keyToBind = key;
            if (key == Keyboard.KEY_ESCAPE || key == Keyboard.KEY_DELETE) {
            	keyToBind = Keyboard.getKeyIndex((String)"NONE");
            }
            button.module.setKey(keyToBind);
            button.isBinding = false;
        }
    }
    
    @Override
    public void checkBoxMouseClicked(Checkbox p0, int p2, int p3, int p4, CategoryPanel panel) {
        if (panel.categoryButton.enabled) {
            boolean hovering;
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            int hoverneed = GLwheel; //TODO
            boolean bl = hovering = (float)p2 >= p0.x + xOff && (float)p3 >= p0.y + yOff + hoverneed && (float)p2 <= p0.x + 35.0f + xOff && (float)p3 <= p0.y + 6.0f + yOff + hoverneed;
            if (hovering && p4 == 0) {
                boolean xd = (Boolean)p0.setting.getValueState();
                p0.setting.setValueState((Object)(!xd));
                Client.instance.fileMgr.saveValues();
            }
        }
    }
    @Override
    public void checkBoxDraw(Checkbox p0, float p2, float p3, CategoryPanel panel) {
        if (panel.categoryButton.enabled) {
            GlStateManager.pushMatrix();
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            GlStateManager.pushMatrix();
            String xd = p0.setting.getValueName().split("_")[1].charAt(0) + p0.setting.getValueName().split("_")[1].substring(1);
            Client.fss.drawStringWithShadow(xd, p0.x + 7.5f + xOff, p0.y + 1.0f + yOff, Colors.getColor((int)220, (int)((int)this.opacity.getOpacity())));
            GlStateManager.popMatrix();
            RenderUtil.rectangle((double)((double)(p0.x + xOff) + 0.6), (double)((double)(p0.y + yOff) + 0.6), (double)((double)(p0.x + 6.0f + xOff) + -0.6), (double)((double)(p0.y + 6.0f + yOff) + -0.6), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
            RenderUtil.drawGradient((double)(p0.x + xOff + 1.0f), (double)(p0.y + yOff + 1.0f), (double)(p0.x + 6.0f + xOff + -1.0f), (double)(p0.y + 6.0f + yOff + -1.0f), (int)Colors.getColor((int)76), (int)Colors.getColor((int)51, (int)((int)this.opacity.getOpacity())));
            p0.enabled = (Boolean)p0.setting.getValueState();
            int hoverneed = GLwheel; //TODO
            boolean hovering = p2 >= p0.x + xOff && p3 >= p0.y + yOff +hoverneed && p2 <= p0.x + 35.0f + xOff && p3 <= p0.y + 6.0f + yOff + hoverneed;
            if (p0.enabled) {
            	RenderUtil.drawGradient((double)(p0.x + xOff + 1.0f), (double)(p0.y + yOff + 1.0f), (double)(p0.x + xOff + 5.0f), (double)(p0.y + yOff + 5.0f), Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), ((int)this.opacity.getOpacity())), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), 120));
            }
            if (hovering && !p0.enabled) {
            	RenderUtil.rectangle((double)(p0.x + xOff + 1.0f), (double)(p0.y + yOff + 1.0f), (double)(p0.x + xOff + 5.0f), (double)(p0.y + yOff + 5.0f), (int)Colors.getColor((int)255, (int)40));
            }
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public void dropDownContructor(DropdownBox p0, float p2, float p3, CategoryPanel panel) {
        int y = 10;
        String[] ee = new String[p0.setting.mode.size()];
        for (int i = 0; i < p0.setting.mode.size(); ++i) {

        	ee[i] = p0.setting.getModeAt(i);
        }
        for (String value : ee) {
            p0.buttons.add(new DropdownButton(value, p2, p3 + (float)y, p0));
            y += 9;
        }
    }
    
    @Override
    public void dropDownMouseClicked(DropdownBox dropDown, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
        for (DropdownButton db : dropDown.buttons) {
            if (!dropDown.active || !dropDown.panel.visible) continue;
            db.mouseClicked(mouseX, mouseY, mouse);
        }
        int hoverneed = GLwheel; //TODO
        dropDown.active = (float)mouseX >= panel.categoryButton.panel.dragX + dropDown.x && (float)mouseY >= panel.categoryButton.panel.dragY + dropDown.y + hoverneed&& (float)mouseX <= panel.categoryButton.panel.dragX + dropDown.x + 40.0f && (float)mouseY <= panel.categoryButton.panel.dragY + dropDown.y + hoverneed + 8.0f && mouse == 0 && dropDown.panel.visible ? !dropDown.active : false;
    }
    
    @Override
    public void dropDownDraw(DropdownBox p0, float p2, float p3, CategoryPanel panel) {
        float xOff = panel.categoryButton.panel.dragX;
        float yOff = panel.categoryButton.panel.dragY;
        int hoverneed = GLwheel; //TODO
        boolean hovering = p2 >= panel.categoryButton.panel.dragX + p0.x && p3 >= panel.categoryButton.panel.dragY + p0.y + hoverneed && p2 <= panel.categoryButton.panel.dragX + p0.x + 40.0f && p3 <= panel.categoryButton.panel.dragY + p0.y + 9.0f + hoverneed;
        RenderUtil.rectangle((double)((double)(p0.x + xOff) - 0.3), (double)((double)(p0.y + yOff) - 0.3), (double)((double)(p0.x + xOff + 40.0f) + 0.3), (double)((double)(p0.y + yOff + 9.0f) + 0.3), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
        RenderUtil.drawGradient((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + xOff + 40.0f), (double)(p0.y + yOff + 9.0f), (int)Colors.getColor((int)31, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)36, (int)((int)this.opacity.getOpacity())));
        if (hovering) {
        	RenderUtil.rectangleBordered((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + xOff + 40.0f), (double)(p0.y + yOff + 9.0f), (double)0.3, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)90, (int)((int)this.opacity.getOpacity())));
        }
        Client.fss.drawStringWithShadow(p0.setting.getModeTitle(), p0.x + xOff + 1.0f, p0.y - 6.0f + yOff, Colors.getColor((int)220, (int)((int)this.opacity.getOpacity())));
        GlStateManager.pushMatrix();
        GlStateManager.translate((double)((double)(p0.x + xOff + 38.0f) - (p0.active ? 2.5 : 0.0)), (double)((double)p0.y + 4.5 + (double)yOff), (double)0.0);
        GlStateManager.rotate((float)(p0.active ? 270.0f : 90.0f), (float)0.0f, (float)0.0f, (float)90.0f);
        RenderUtil.rectangle((double)-1.0, (double)0.0, (double)-0.5, (double)2.5, (int)Colors.getColor((int)0));
        RenderUtil.rectangle((double)-0.5, (double)0.0, (double)0.0, (double)2.5, (int)Colors.getColor((int)151));
        RenderUtil.rectangle((double)0.0, (double)0.5, (double)0.5, (double)2.0, (int)Colors.getColor((int)151));
        RenderUtil.rectangle((double)0.5, (double)1.0, (double)1.0, (double)1.5, (int)Colors.getColor((int)151));
        GlStateManager.popMatrix();
        String modenow = p0.setting.getModeAt(p0.setting.getCurrentMode());
        Client.fss.drawString(modenow, p0.x + 4.0f + xOff - 1.0f, p0.y + 3.0f + yOff, Colors.getColor((int)151, (int)((int)this.opacity.getOpacity())));
        if (p0.active) {
            int i = p0.buttons.size();
            RenderUtil.rectangle((double)((double)(p0.x + xOff) - 0.3), (double)((double)(p0.y + 10.0f + yOff) - 0.3), (double)((double)(p0.x + xOff + 40.0f) + 0.3), (double)((double)(p0.y + yOff + 9.0f + (float)(9 * i)) + 0.3), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
            RenderUtil.drawGradient((double)(p0.x + xOff), (double)(p0.y + yOff + 10.0f), (double)(p0.x + xOff + 40.0f), (double)(p0.y + yOff + 9.0f + (float)(9 * i)), (int)Colors.getColor((int)31, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)36, (int)((int)this.opacity.getOpacity())));
        }
        if (hovering) {
            //Client.fss.drawStringWithShadow(this.getDescription(p0.setting), panel.categoryButton.panel.x + 2.0f + panel.categoryButton.panel.dragX + 55.0f, panel.categoryButton.panel.y + 9.0f + panel.categoryButton.panel.dragY, -1);
        }
    }
    @Override
    public void dropDownButtonMouseClicked(DropdownButton p0, DropdownBox p1, int x, int y, int mouse) {
        int hoverneed = GLwheel; //TODO
        if ((float)x >= p1.panel.categoryButton.panel.dragX + p0.x && (float)y >= p1.panel.categoryButton.panel.dragY + p0.y + hoverneed && (float)x <= p1.panel.categoryButton.panel.dragX + p0.x + 40.0f && (double)y <= (double)(p1.panel.categoryButton.panel.dragY + p0.y + hoverneed) + 8.5 && mouse == 0) {
       
        	String mod = p1.setting.getModeAt(p0.name);
        	int m = p1.setting.getModeInt(mod);
        	p1.setting.setCurrentMode(m);
            p1.active = false;
        }
    }
    @Override
    public void dropDownButtonDraw(DropdownButton p0, DropdownBox p1, float x, float y) {
        float xOff = p1.panel.categoryButton.panel.dragX;
        float yOff = p1.panel.categoryButton.panel.dragY;
        int hoverneed = GLwheel;
        boolean hovering = x >= xOff + p0.x && y >= yOff + p0.y + hoverneed && x <= xOff + p0.x + 40.0f && (double)y <= (double)(yOff + p0.y + hoverneed) + 8.5;
        GlStateManager.pushMatrix();
        Client.fss.drawStringWithShadow((hovering ? "\u00a7l" : "") + p0.name, p0.x + 3.0f + xOff, p0.y + 2.0f + yOff,  -1);
        GlStateManager.popMatrix();
    }
    @Override
    public void categoryButtonMouseReleased(CategoryButton categoryButton, int x, int y, int button) {
        categoryButton.categoryPanel.mouseReleased(x, y, button);
    }
    @Override
    public void slButtonDraw(SLButton slButton, float x, float y, MainPanel panel) {
        float xOff = panel.dragX;
        float yOff = panel.dragY + 75.0f;
        int hoverneed = GLwheel; //TODO
        boolean hovering = x >= 55.0f + slButton.x + xOff && y >= slButton.y + yOff - 2.0f + hoverneed && x <= 55.0f + slButton.x + xOff + 40.0f && y <= slButton.y + 8.0f + yOff + 2.0f + hoverneed;
        RenderUtil.rectangleBordered((double)((double)(slButton.x + xOff + 55.0f) - 0.3), (double)((double)(slButton.y + yOff) - 0.3 - 2.0), (double)((double)(slButton.x + xOff + 40.0f + 55.0f) + 0.3), (double)((double)(slButton.y + 8.0f + yOff) + 0.3 + 2.0), (double)0.3, (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
        RenderUtil.drawGradient((double)(slButton.x + xOff + 55.0f), (double)(slButton.y + yOff - 2.0f), (double)(slButton.x + xOff + 40.0f + 55.0f), (double)(slButton.y + 8.0f + yOff + 2.0f), (int)Colors.getColor((int)46, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)27, (int)((int)this.opacity.getOpacity())));
        if (hovering) {
        	RenderUtil.rectangleBordered((double)(slButton.x + xOff + 55.0f), (double)(slButton.y + yOff - 2.0f), (double)(slButton.x + xOff + 40.0f + 55.0f), (double)(slButton.y + 8.0f + yOff + 2.0f), (double)0.6, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)90, (int)((int)this.opacity.getOpacity())));
        }
        float xOffset = Client.fs.getHeight(slButton.name) / 2.0f;
        Client.fs.drawStringWithShadow(slButton.name, xOff + 25.0f + 55.0f - xOffset, slButton.y + yOff + 1.5f, -1);
    }
    @Override
    public void slButtonMouseClicked(SLButton slButton, float x, float y, int button, MainPanel panel) {
        float xOff = panel.dragX;
        float yOff = panel.dragY + 75.0f;
        int hoverneed = GLwheel; //TODO
        if (button == 0 && x >= 55.0f + slButton.x + xOff && y >= slButton.y + yOff + hoverneed - 2.0f && x <= 55.0f + slButton.x + xOff + 40.0f && y <= slButton.y + 8.0f + hoverneed + yOff + 2.0f) {
            if (slButton.load) {
                //ChatUtil.printChat((String)"Settings have been loaded.");
                //ModuleManager.loadSettings();
                //ColorCommand.loadStatus();
                panel.typePanel.forEach(o -> o.sliders.forEach(slider -> {
                    slider.dragX = slider.lastDragX = ((Number)slider.setting.getValueState()).doubleValue() * 40.0 / (double)slider.setting.getValueMax();
                }));
            } else {
                //ChatUtil.printChat((String)"Settings have been saved.");
                //ColorCommand.saveStatus();
               Client.instance.fileMgr.saveValues();
                panel.typePanel.forEach(o -> o.sliders.forEach(slider -> {
                    slider.dragX = slider.lastDragX = ((Number)slider.setting.getValueState()).doubleValue() * 40.0 / (double)slider.setting.getValueMax();
                }));
            }
        }
    }
    @Override
    public void colorConstructor(ColorPreview colorPreview, float x, float y) {
        colorPreview.sliders.add((HSVColorPicker)new HSVColorPicker(x + 10.0f, y, colorPreview, colorPreview.colorObject));
    }
    @Override
    public void colorPrewviewDraw(ColorPreview colorPreview, float x, float y) {
        float xOff = colorPreview.x + colorPreview.categoryPanel.panel.dragX;
        float yOff = colorPreview.y + colorPreview.categoryPanel.panel.dragY + 75.0f;
        RenderUtil.rectangleBordered((double)(xOff - 80.0f), (double)(yOff - 6.0f), (double)(xOff + 1.0f), (double)(yOff + 46.0f), (double)0.3, (int)Colors.getColor((int)48, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangle((double)(xOff - 79.0f), (double)(yOff - 5.0f), (double)xOff, (double)(yOff + 45.0f), (int)Colors.getColor((int)17, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangle((double)(xOff - 74.0f), (double)(yOff - 6.0f), (double)(xOff - 73.0f + Client.fs.getHeight(colorPreview.colorName) + 1.0f), (double)(yOff - 4.0f), (int)Colors.getColor((int)17, (int)((int)this.opacity.getOpacity())));
        Client.fs.drawStringWithShadow(colorPreview.colorName, xOff - 73.0f, yOff - 8.0f, Colors.getColor((int)255, (int)((int)this.opacity.getOpacity())));
        colorPreview.sliders.forEach(o -> o.draw(x, y));
    }
    @Override
    public void colorPickerConstructor(HSVColorPicker picker, float x, float y) {
        Color color = new Color(picker.colorPreview.colorObject.getColorInt());
        picker.opacity = (float)picker.colorPreview.colorObject.getAlpha() / 255.0f;
        picker.hue = Color.RGBtoHSB((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), null)[0];
        picker.saturation = Color.RGBtoHSB((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), null)[1];
        picker.brightness = Color.RGBtoHSB((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), null)[2];
    }
    @Override
    public void colorPickerDraw(HSVColorPicker cp, float x, float y) {
        boolean shouldUpdate;
        float xOff = cp.x + cp.colorPreview.categoryPanel.panel.dragX - 85.5f;
        float yOff = cp.y + cp.colorPreview.categoryPanel.panel.dragY + 74.0f;
        RenderUtil.rectangle((double)xOff, (double)yOff, (double)(xOff + 43.0f), (double)(yOff + 43.0f), (int)Colors.getColor((int)32, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangle((double)((double)xOff + 0.5), (double)((double)yOff + 0.5), (double)((double)xOff + 42.5), (double)((double)yOff + 42.5), (int)Color.getHSBColor((float)cp.hue, (float)1.0f, (float)1.0f).getRGB());
        Depth.pre();
        Depth.mask();
        RenderUtil.rectangle((double)((double)xOff + 0.5), (double)((double)yOff + 0.5), (double)((double)xOff + 42.5), (double)((double)yOff + 42.5), (int)-1);
        Depth.render();
        RenderUtil.drawGradient((double)((double)xOff + 0.5), (double)((double)yOff + 0.5), (double)(xOff + 46.5f), (double)((double)yOff + 42.5), (int)Colors.getColor((int)255, (int)255), (int)Colors.getColor((int)255, (int)0));
        RenderUtil.drawGradient((double)((double)xOff + 0.5), (double)(yOff - 4.0f), (double)((double)xOff + 42.5), (double)((double)yOff + 42.5), (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)0, (int)255));
        Depth.post();
        RenderUtil.rectangleBordered((double)((double)xOff + 42.5 * (double)cp.saturation - 1.0), (double)((double)yOff + 42.5 - 42.5 * (double)cp.brightness - 1.0), (double)((double)xOff + 42.5 * (double)cp.saturation + 1.0), (double)((double)yOff + 42.5 - 42.5 * (double)cp.brightness + 1.0), (double)0.5, (int)Color.getHSBColor((float)cp.hue, (float)cp.saturation, (float)cp.brightness).getRGB(), (int)Colors.getColor((int)0));
        RenderUtil.rectangle((double)(xOff + 45.0f), (double)yOff, (double)(xOff + 48.0f), (double)(yOff + 43.0f), (int)Colors.getColor((int)32, (int)((int)this.opacity.getOpacity())));
        RenderUtil.drawGradient((double)(xOff + 45.5f), (double)(yOff + 0.5f), (double)(xOff + 47.5f), (double)(yOff + 8.0f), (int)Color.getHSBColor((float)0.0f, (float)1.0f, (float)1.0f).getRGB(), (int)Color.getHSBColor((float)0.2f, (float)1.0f, (float)1.0f).getRGB());
        RenderUtil.drawGradient((double)(xOff + 45.5f), (double)(yOff + 8.0f), (double)(xOff + 47.5f), (double)(yOff + 13.0f), (int)Color.getHSBColor((float)0.2f, (float)1.0f, (float)1.0f).getRGB(), (int)Color.getHSBColor((float)0.3f, (float)1.0f, (float)1.0f).getRGB());
        RenderUtil.drawGradient((double)(xOff + 45.5f), (double)(yOff + 13.0f), (double)(xOff + 47.5f), (double)(yOff + 17.0f), (int)Color.getHSBColor((float)0.3f, (float)1.0f, (float)1.0f).getRGB(), (int)Color.getHSBColor((float)0.4f, (float)1.0f, (float)1.0f).getRGB());
        RenderUtil.drawGradient((double)(xOff + 45.5f), (double)(yOff + 17.0f), (double)(xOff + 47.5f), (double)(yOff + 22.0f), (int)Color.getHSBColor((float)0.4f, (float)1.0f, (float)1.0f).getRGB(), (int)Color.getHSBColor((float)0.5f, (float)1.0f, (float)1.0f).getRGB());
        RenderUtil.drawGradient((double)(xOff + 45.5f), (double)(yOff + 22.0f), (double)(xOff + 47.5f), (double)(yOff + 26.0f), (int)Color.getHSBColor((float)0.5f, (float)1.0f, (float)1.0f).getRGB(), (int)Color.getHSBColor((float)0.6f, (float)1.0f, (float)1.0f).getRGB());
        RenderUtil.drawGradient((double)(xOff + 45.5f), (double)(yOff + 26.0f), (double)(xOff + 47.5f), (double)(yOff + 30.0f), (int)Color.getHSBColor((float)0.6f, (float)1.0f, (float)1.0f).getRGB(), (int)Color.getHSBColor((float)0.7f, (float)1.0f, (float)1.0f).getRGB());
        RenderUtil.drawGradient((double)(xOff + 45.5f), (double)(yOff + 30.0f), (double)(xOff + 47.5f), (double)(yOff + 34.0f), (int)Color.getHSBColor((float)0.7f, (float)1.0f, (float)1.0f).getRGB(), (int)Color.getHSBColor((float)0.8f, (float)1.0f, (float)1.0f).getRGB());
        RenderUtil.drawGradient((double)(xOff + 45.5f), (double)(yOff + 34.0f), (double)(xOff + 47.5f), (double)((double)yOff + 42.5), (int)Color.getHSBColor((float)0.8f, (float)1.0f, (float)1.0f).getRGB(), (int)Color.getHSBColor((float)1.0f, (float)1.0f, (float)1.0f).getRGB());
        RenderUtil.rectangleBordered((double)(xOff + 45.0f), (double)((double)yOff + 42.5 * (double)cp.hue - 1.5), (double)(xOff + 48.0f), (double)((double)yOff + 42.5 * (double)cp.hue + 1.5), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)(cp.selectingHue ? 255 : 200), (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangleBordered((double)(xOff + 50.0f), (double)yOff, (double)(xOff + 53.0f), (double)(yOff + 43.0f), (double)0.5, (int)cp.color.getColorInt(), (int)Colors.getColor((int)32, (int)((int)this.opacity.getOpacity())));
        RenderUtil.rectangleBordered((double)(xOff + 50.0f), (double)((double)yOff + 42.5 * (double)cp.opacity - 1.5), (double)(xOff + 53.0f), (double)((double)yOff + 42.5 * (double)cp.opacity + 1.5), (double)0.5, (int)Colors.getColor((int)0, (int)0), (int)Colors.getColor((int)(cp.selectingOpacity ? 255 : 200), (int)((int)this.opacity.getOpacity())));
        boolean bl = shouldUpdate = cp.selectingHue || cp.selectingColor || cp.selectingOpacity;
        if (shouldUpdate) {
            Color tcolor = Color.getHSBColor((float)(1.0f - cp.hue), (float)cp.saturation, (float)cp.brightness);
            cp.color.updateColors(tcolor.getRed(), tcolor.getBlue(), tcolor.getGreen(), (int)(255.0f * cp.opacity));
        }
        if (cp.selectingOpacity) {
            float tempY = y;
            if (tempY > yOff + 42.0f) {
                tempY = yOff + 42.0f;
            } else if (tempY < yOff) {
                tempY = yOff;
            }
            cp.opacity = (tempY -= yOff) / 42.0f;
        }
        if (cp.selectingHue) {
            float tempY = y;
            if (tempY > yOff + 42.0f) {
                tempY = yOff + 42.0f;
            } else if (tempY < yOff) {
                tempY = yOff;
            }
            cp.hue = (tempY -= yOff) / 42.0f;
        }
        if (cp.selectingColor) {
            float tempY = y;
            float tempX = x;
            if (tempY > yOff + 43.0f) {
                tempY = yOff + 43.0f;
            } else if (tempY < yOff) {
                tempY = yOff;
            }
            tempY -= yOff;
            if (tempX > xOff + 43.0f) {
                tempX = xOff + 43.0f;
            } else if (tempX < xOff) {
                tempX = xOff;
            }
            cp.brightness = 1.0f - tempY / 43.0f;
            cp.saturation = (tempX -= xOff) / 43.0f;
        }
        RenderUtil.rectangle((double)(xOff + 57.0f), (double)yOff, (double)(xOff + 72.0f), (double)(yOff + 30.0f), (int)-1);
        boolean offset = false;
        for (int yThing = 0; yThing < 30; ++yThing) {
            int i;
            int n = i = offset ? 0 : 1;
            while (i < 15) {
            	RenderUtil.rectangle((double)(xOff + 57.0f + (float)i), (double)(yOff + (float)yThing), (double)(xOff + 57.0f + (float)i + 1.0f), (double)(yOff + (float)yThing + 1.0f), (int)Colors.getColor((int)190));
                i += 2;
            }
            offset = !offset;
        }
        RenderUtil.rectangleBordered((double)(xOff + 59.0f), (double)(yOff + 2.0f), (double)(xOff + 70.0f), (double)(yOff + 28.0f), (double)0.5, (int)cp.colorPreview.colorObject.getColorInt(), (int)Color.BLACK.getRGB());
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(xOff + 65.0f), (float)(yOff + 33.0f), (float)0.0f);
        GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
//        this.mc.fontRenderer.drawStringWithShadow("Copy", (float)(0 - this.mc.fontRenderer.getStringWidth("Copy") / 2), 0.0f, -1);
//        this.mc.fontRenderer.drawStringWithShadow("Paste", (float)(0 - this.mc.fontRenderer.getStringWidth("Paste") / 2), 12.0f, -1);
        GlStateManager.popMatrix();
    }

    private boolean hovering(float mouseX, float mouseY, float x, float y, float width, float height) {
        int hoverneed = GLwheel; //TODO
        return mouseX >= x && mouseY >= y + hoverneed && mouseX <= x + width && mouseY <= y + height + hoverneed;
    }

    public static String copy() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipTf = sysClip.getContents(null);
        if (clipTf != null && clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                ret = (String)clipTf.getTransferData(DataFlavor.stringFlavor);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static void paste(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection tText = new StringSelection(writeMe);
        clip.setContents((Transferable)tText, null);
    }
    @Override
    public void colorPickerClick(HSVColorPicker cp, float x, float y, int mouse) {
        float xOff = cp.x + cp.colorPreview.categoryPanel.panel.dragX - 85.5f;
        float yOff = cp.y + cp.colorPreview.categoryPanel.panel.dragY + 74.0f;
        if (mouse == 0) {
            try {
                String hex;
                if (this.hovering(x, y, xOff + 59.0f, yOff + 33.0f, 12.0f, 4.0f)) {
                    hex = String.format((String)"#%02X%02X%02X", (Object[])new Object[]{cp.color.getRed(), cp.color.getGreen(), cp.color.getBlue()});
                    SkeetMenu.paste(hex);
                    //DevNotifications.getManager().post("Copied to clipboard hex: " + hex);
                }
                if (this.hovering(x, y, xOff + 59.0f, yOff + 39.0f, 12.0f, 4.0f)) {
                    hex = SkeetMenu.copy();
                    if (!Objects.equals((Object)hex, (Object)"")) {
                        if (!hex.startsWith("#")) {
                            hex = "#" + hex;
                        }
                        Color c = Color.decode((String)hex);
                        cp.opacity = (float)cp.colorPreview.colorObject.getAlpha() / 255.0f;
                        cp.hue = Color.RGBtoHSB((int)c.getRed(), (int)c.getGreen(), (int)c.getBlue(), null)[0];
                        cp.saturation = Color.RGBtoHSB((int)c.getRed(), (int)c.getGreen(), (int)c.getBlue(), null)[1];
                        cp.brightness = Color.RGBtoHSB((int)c.getRed(), (int)c.getGreen(), (int)c.getBlue(), null)[2];
                        Color tcolor = Color.getHSBColor((float)(1.0f - cp.hue), (float)cp.saturation, (float)cp.brightness);
                        cp.color.updateColors(tcolor.getRed(), tcolor.getBlue(), tcolor.getGreen(), (int)(255.0f * cp.opacity));
                    }
                    //DevNotifications.getManager().post("Pasted color to " + cp.colorPreview.colorName);
                }
            }
            catch (Exception e) {
                //DevNotifications.getManager().post("\u00a7c" + e.getMessage() + " exception!");
            }
        }
        if (!(cp.selectingHue || cp.selectingColor || cp.selectingOpacity || mouse != 0)) {
            if (this.hovering(x, y, xOff + 50.0f, yOff, 3.0f, 43.0f)) {
                cp.selectingOpacity = true;
            }
            if (this.hovering(x, y, xOff + 45.0f, yOff, 3.0f, 43.0f)) {
                cp.selectingHue = true;
            }
            if (this.hovering(x, y, xOff, yOff, 43.0f, 43.0f)) {
                cp.selectingColor = true;
            }
        }
    }
    @Override
    public void colorPickerMovedOrUp(HSVColorPicker slider, float x, float y, int mouse) {
        if (mouse == 0 && (slider.selectingHue || slider.selectingColor || slider.selectingOpacity)) {
            //ColorCommand.saveStatus();
            slider.selectingOpacity = false;
            slider.selectingColor = false;
            slider.selectingHue = false;
        }
    }
    @Override
    public void SliderMouseMovedOrUp(Slider slider, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
        if (mouse == 0) {
            slider.dragging = false;
        }
    }
    @Override
    public void SliderContructor(Slider p0, CategoryPanel panel) {
        double percent = ((double)p0.setting.getValueState() - (double)p0.setting.getValueMin()) / ((double)p0.setting.getValueMax() - (double)p0.setting.getValueMin());
        p0.dragX = 40.0 * percent;
    }
    @Override
    public void SliderDraw(Slider slider, float x, float y, CategoryPanel panel) {
        if (panel.visible) {
            Object newValue;
            GlStateManager.pushMatrix();
            float xOff = panel.categoryButton.panel.dragX;
            float yOff = panel.categoryButton.panel.dragY;
            double percent = slider.dragX / 38.0;
            double value = getIncremental((double)(percent * 100.0 * ((double)slider.setting.getValueMax() - (double)slider.setting.getValueMin()) / 100.0 + (double)slider.setting.getValueMin()), (double)slider.setting.getSteps());
            float sliderX = (float)((((Number)slider.setting.getValueState()).doubleValue() - (double)slider.setting.getValueMin()) / ((double)slider.setting.getValueMax() - (double)slider.setting.getValueMin()) * 38.0);
            RenderUtil.rectangle((double)((double)(slider.x + xOff) - 0.3), (double)((double)(slider.y + yOff) - 0.3), (double)((double)(slider.x + xOff + 38.0f) + 0.3), (double)((double)(slider.y + yOff) + 2.5 + 0.3), (int)Colors.getColor((int)10, (int)((int)this.opacity.getOpacity())));
            RenderUtil.drawGradient((double)(slider.x + xOff), (double)(slider.y + yOff), (double)(slider.x + xOff + 38.0f), (double)((double)(slider.y + yOff) + 2.5), (int)Colors.getColor((int)46, (int)((int)this.opacity.getOpacity())), (int)Colors.getColor((int)27, (int)((int)this.opacity.getOpacity())));
           if ((double)slider.setting.getValueMin() < 0.0 && (double)slider.setting.getValueMax() > 0.0) {
                if (value > 0.0) {
                	RenderUtil.drawGradient((double)(slider.x + xOff + 19.0f), (double)(slider.y + yOff), (double)(slider.x + xOff + sliderX), (double)((double)(slider.y + yOff) + 2.5), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), (int)((int)this.opacity.getOpacity())), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), (int)120));
                } else {
                	RenderUtil.drawGradient((double)(slider.x + xOff + sliderX), (double)(slider.y + yOff), (double)(slider.x + xOff + 19.0f), (double)((double)(slider.y + yOff) + 2.5), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), (int)((int)this.opacity.getOpacity())), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), (int)120));
                }
            } else {
            	RenderUtil.drawGradient((double)(slider.x + xOff), (double)(slider.y + yOff), (double)(slider.x + xOff + sliderX), (double)((double)(slider.y + yOff) + 2.5), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), (int)((int)this.opacity.getOpacity())), (int)Colors.getColor(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue(), (int)120));
           }
           int hoverneed = GLwheel; //TODO
            boolean hoverMinus = x >= panel.x + xOff + slider.x - 3.0f && y >= yOff + panel.y + slider.y + hoverneed && (double)x <= (double)(xOff + panel.x + slider.x) - 0.5 && y <= yOff + panel.y + slider.y + hoverneed + 2.0f;
            boolean hoverPlus = (double)x >= (double)(panel.x + xOff + slider.x) + 38.5 && y >= yOff + panel.y + slider.y + hoverneed && x <= xOff + panel.x + slider.x + 41.0f && y <= yOff + panel.y + slider.y +hoverneed + 2.0f;
            RenderUtil.rectangle((double)((double)(slider.x + xOff) - 2.5), (double)(slider.y + yOff + 1.0f), (double)(slider.x + xOff - 1.0f), (double)((double)(slider.y + yOff) + 1.5), (int)Colors.getColor((int)(hoverMinus ? 220 : 120), (int)((int)this.opacity.getOpacity())));
            RenderUtil.rectangle((double)(slider.x + xOff + 39.0f), (double)(slider.y + yOff + 1.0f), (double)((double)(slider.x + xOff) + 40.5), (double)((double)(slider.y + yOff) + 1.5), (int)Colors.getColor((int)(hoverPlus ? 220 : 120), (int)((int)this.opacity.getOpacity())));
            RenderUtil.rectangle((double)((double)(slider.x + xOff) + 39.5), (double)((double)(slider.y + yOff) + 0.5), (double)(slider.x + xOff + 40.0f), (double)(slider.y + yOff + 2.0f), (int)Colors.getColor((int)(hoverPlus ? 220 : 120), (int)((int)this.opacity.getOpacity())));
            String xd = slider.setting.getValueName().split("_")[1].charAt(0) + slider.setting.getValueName().split("_")[1].toLowerCase().substring(1);
            double setting = ((Number)slider.setting.getValueState()).doubleValue();
            GlStateManager.pushMatrix();
            String valu2e = isInteger((Double)setting) ? (setting + "").replace((CharSequence)".0", (CharSequence)"") : setting + "";
          //  String a = slider.setting.getName().toLowerCase();
          //  if (a.contains((CharSequence)"fov")) {
          //      valu2e = valu2e + "\u00b0";
         //   } else if (a.contains((CharSequence)"delay") || a.contains((CharSequence)"switch") && slider.setting.getSteps() != 1.0) {
        ////        valu2e = valu2e + "ms";
        //    }
        //    if (a.equalsIgnoreCase("Mxaxaps")) {
         //       xd = "Maxaps";
        //    }
            float strWidth = Client.fs.getHeight(valu2e);
            Client.fss.drawBorderedString(valu2e, slider.x + xOff + 38.0f - strWidth, slider.y - 6.0f + yOff, Colors.getColor((int)220, (int)((int)this.opacity.getOpacity())));
            GlStateManager.scale((float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
            Client.fss.drawStringWithShadow(xd, slider.x + xOff, slider.y - 6.0f + yOff, Colors.getColor((int)220, (int)((int)this.opacity.getOpacity())));
            if (slider.dragging) {
                slider.dragX = x - (slider.x + xOff);
                newValue = castNumber((String)Double.toString((double)value), (Object)slider.setting.getSteps());
                slider.setting.setValueState(newValue);
            }
            if (((Number)slider.setting.getValueState()).doubleValue() <= (double)slider.setting.getValueMin()) {
                newValue = castNumber((String)Double.toString((double)slider.setting.getValueMin()), (Object)slider.setting.getSteps());
                slider.setting.setValueState(newValue);
            }
            if (((Number)slider.setting.getValueState()).doubleValue() >=(double) slider.setting.getValueMax()) {
                newValue = castNumber((String)Double.toString((double)slider.setting.getValueMax()), (Object)slider.setting.getSteps());
                slider.setting.setValueState(newValue);
            }
            if (slider.dragX <= 0.0) {
                slider.dragX = 0.0;
            }
            if (slider.dragX >= 38.0) {
                slider.dragX = 38.0;
            }
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void SliderMouseClicked(Slider slider, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
        float xOff = panel.categoryButton.panel.dragX;
        float yOff = panel.categoryButton.panel.dragY;
        int hoverneed = GLwheel;  //TODO
        if (panel.visible && (float)mouseX >= panel.x + xOff + slider.x && (float)mouseY >= yOff + panel.y + hoverneed + slider.y - 6.0f && (float)mouseX <= xOff + panel.x + slider.x + 38.0f && (float)mouseY <= yOff + panel.y + hoverneed + slider.y + 4.0f && mouse == 0) {
            slider.dragging = true;
            slider.lastDragX = (double)mouseX - slider.dragX;
        }
        if (panel.visible && (float)mouseX >= panel.x + xOff + slider.x - 3.0f && (float)mouseY >= hoverneed + yOff + panel.y + slider.y && (double)mouseX <= (double)(xOff + panel.x + slider.x) - 0.5 && (float)mouseY <= hoverneed + yOff + panel.y + slider.y + 2.0f && mouse == 0) {
            Value setting = slider.setting;
            double value = ((Number)setting.getValueState()).doubleValue();
            if (value - setting.getSteps() >= (double)setting.getValueMin()) {
                setting.setValueState((Double)getIncremental((double)(value - setting.getSteps()), (double)setting.getSteps()));
            } else {
                setting.setValueState((Double)setting.getValueMin());
            }
        } else if (panel.visible && (double)mouseX >= (double)(panel.x + xOff + slider.x) + 38.5 && (float)mouseY >= hoverneed + yOff + panel.y + slider.y && (float)mouseX <= xOff + panel.x + slider.x + 41.0f && (float)mouseY <= hoverneed + yOff + panel.y + slider.y + 2.0f && mouse == 0) {
            Value setting = slider.setting;
            double value = ((Number)setting.getValueState()).doubleValue();
            if (value + setting.getSteps() <= (double)setting.getValueMax()) {
                setting.setValueState((Double)getIncremental((double)(value + setting.getSteps()), (double)setting.getSteps()));
            } else {
                setting.setValueState((Double)setting.getValueMax());
            }
        }
    }
    public static double getIncremental(double val, double inc) {
        double one = 1.0D / inc;
        return (double)Math.round(val * one) / one;
    }
    @Override
    public void configButtonDraw(ConfigButton configButton, float x, float y) {
        float xOff = configButton.configList.categoryPanel.categoryButton.panel.dragX;
        float yOff = configButton.configList.categoryPanel.categoryButton.panel.dragY;
    }
    @Override
    public void configButtonMouseClicked(ConfigButton configButton, float x, float y, int button) {
        float xOff = configButton.configList.categoryPanel.categoryButton.panel.dragX;
        float yOff = configButton.configList.categoryPanel.categoryButton.panel.dragY;
    }
    @Override
    public void configListDraw(ConfigList configList, float x, float y) {
        float xOff = configList.categoryPanel.categoryButton.panel.dragX;
        float yOff = configList.categoryPanel.categoryButton.panel.dragY;
    }
    @Override
    public void configListMouseClicked(ConfigList configList, float x, float y, int button) {
        float xOff = configList.categoryPanel.categoryButton.panel.dragX;
        float yOff = configList.categoryPanel.categoryButton.panel.dragY;
    }
    
    public static boolean isInteger(Double variable) {
        return variable.doubleValue() == Math.floor(variable.doubleValue()) && !Double.isInfinite(variable.doubleValue());
    }

    public static boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }
    
    public static Object castNumber(String newValueText, Object currentValue) {
        if (newValueText.contains(".")) {
            return newValueText.toLowerCase().contains("f") ? Float.parseFloat(newValueText) : Double.parseDouble(newValueText);
        } else {
            return isNumeric(newValueText) ? Integer.parseInt(newValueText) : newValueText;
        }
    }
    
    private static class Depth {
        private static final List<Integer> depth = Lists.newArrayList();

        public static void pre() {
            if (depth.isEmpty()) {
                GlStateManager.clearDepth((double)1.0);
                GlStateManager.clear((int)256);
            }
        }

        public static void mask() {
            depth.add(0, (Integer)GL11.glGetInteger((int)2932));
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
            GlStateManager.depthFunc((int)513);
            GlStateManager.colorMask((boolean)false, (boolean)false, (boolean)false, (boolean)true);
        }

        public static void render() {
            Depth.render(514);
        }

        public static void render(int gl) {
            GlStateManager.depthFunc((int)gl);
            GlStateManager.colorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        }

        public static void post() {
            GlStateManager.depthFunc((int)((Integer)depth.get(0)));
            depth.remove(0);
        }
    }
}
