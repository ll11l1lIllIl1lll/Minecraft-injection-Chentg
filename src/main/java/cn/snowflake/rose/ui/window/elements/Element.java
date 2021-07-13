package cn.snowflake.rose.ui.window.elements;

import net.minecraft.client.gui.GuiButton;

public class Element {

    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2) {
            return true;
        }
        return false;
    }

    public void actionPerformed(GuiButton p_146284_1_) {}

    public void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {

    }

    public void keyTyped(char p_73869_1_, int p_73869_2_) {

    }

    public void mouseMovedOrUp(int mouseX, int mouseY){

    }
    public void draw(int x, int y,int mouseX, int mouseY) {

    }


}
