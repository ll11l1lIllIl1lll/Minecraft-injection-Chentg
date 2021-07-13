package cn.snowflake.rose.ui.window.elements.impl;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.mods.RENDER.ClickGui;
import cn.snowflake.rose.ui.window.elements.Element;
import cn.snowflake.rose.utils.render.RenderUtil;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

public class CategoryBox extends Element {
    private Category category;


    public CategoryBox(Category category){
        this.category = category;
    }

    @Override
    public void draw(int x,int y,int mouseX, int mouseY){
        String name = category.name();
        int color = new  Color(ClickGui.r.getValueState().intValue(), ClickGui.g.getValueState().intValue(), ClickGui.b.getValueState().intValue()).getRGB();

        if (isHovered(x - 32,y - 10 ,x + 100,y+35,mouseX,mouseY)){
            RenderUtil.drawBordRect(x - 32,y - 10 ,x + 100,y+35,0.9,-1,new Color(230,230,230).getRGB());
        }


        Client.instance.fontManager.simpleton15.drawBoldString(name,x,y,new Color(0,0,0).getRGB());
//        String desc = null;
//        if (name.toLowerCase().equalsIgnoreCase("movement")){
//            desc = "A";
//        }else if (name.toLowerCase().equalsIgnoreCase("render")){
//            desc =  "C";
//        }else if (name.toLowerCase().equalsIgnoreCase("combat")){
//            desc = "D";
//        }else if (name.toLowerCase().equalsIgnoreCase("player")){
//            desc = "B";
//        }else if (name.toLowerCase().equalsIgnoreCase("forge")){
//            desc = "E";
//        }else if (name.toLowerCase().equalsIgnoreCase("world")){
//            desc = "G";
//        }
        Client.instance.fontManager.simpleton15.drawBoldString(name,x,y,new Color(0,0,0).getRGB());

        String iconname = null;
        if (name.toLowerCase().equalsIgnoreCase("movement")){
            iconname = "A";
        }else if (name.toLowerCase().equalsIgnoreCase("render")){
            iconname =  "C";
        }else if (name.toLowerCase().equalsIgnoreCase("combat")){
            iconname = "D";
        }else if (name.toLowerCase().equalsIgnoreCase("player")){
            iconname = "B";
        }else if (name.toLowerCase().equalsIgnoreCase("forge")){
            iconname = "E";
        }else if (name.toLowerCase().equalsIgnoreCase("world")){
            iconname = "G";
        }
        Client.instance.fontManager.notif40.drawBoldString(iconname,x - 25,y - 2,color);



    }


    @Override
    public void actionPerformed(GuiButton p_146284_1_) {
        super.actionPerformed(p_146284_1_);
    }

    @Override
    public void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY) {
        super.mouseMovedOrUp(mouseX, mouseY);
    }
}
