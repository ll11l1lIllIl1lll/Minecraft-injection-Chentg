package cn.snowflake.rose.ui.skeet.components;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;

import java.util.ArrayList;



public class MainPanel {
    public float x;
    public float y;
    public String headerString;
    public float dragX;
    public float dragY;
    public float lastDragX;
    public float lastDragY;
    public boolean dragging;
    UI theme;
    public boolean opened;
    public ArrayList<CategoryButton> typeButton;
    public ArrayList<CategoryPanel> typePanel;
    public ArrayList<SLButton> slButtons;

    public boolean isOpened() {
        return this.opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public MainPanel(String header, float x, float y, UI theme) {
        this.headerString = header;
        this.x = x;
        this.y = y;
        this.theme = theme;
        this.typeButton = new ArrayList();
        this.typePanel = new ArrayList();
        this.slButtons = new ArrayList();
        theme.panelConstructor(this, x, y);
    }

    public void mouseClicked(int x, int y, int state) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.panelMouseClicked(this, x, y, state);
        }
    }

    public void mouseMovedOrUp(int x, int y, int state) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.panelMouseMovedOrUp(this, x, y, state);
        }
    }

    public void draw(int mouseX, int mouseY) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.mainPanelDraw(this, mouseX, mouseY);
        }
    }

    public void keyPressed(int key) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.mainPanelKeyPress(this, key);
        }
    }
}
