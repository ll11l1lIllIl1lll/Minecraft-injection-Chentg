package cn.snowflake.rose.ui.skeet.components;

import java.util.ArrayList;
import cn.snowflake.rose.Client;
import cn.snowflake.rose.ui.skeet.ui.UI;
import cn.snowflake.rose.utils.Value;


public class CategoryPanel {
    public float x;
    public float y;
    public boolean visible;
    public CategoryButton categoryButton;
    public String headerString;
    public ArrayList<Button> buttons;
    public ArrayList<Slider> sliders;
    public ArrayList<DropdownBox> dropdownBoxes;
    public ArrayList<Checkbox> checkboxes;
    public ArrayList<ColorPreview> colorPreviews;
    public ArrayList<GroupBox> groupBoxes;

    public CategoryPanel(String name, CategoryButton categoryButton, float x, float y) {
        this.headerString = name;
        this.x = x;
        this.y = y;
        this.categoryButton = categoryButton;
        this.colorPreviews = new ArrayList();
        this.buttons = new ArrayList();
        this.sliders = new ArrayList();
        this.dropdownBoxes = new ArrayList();
        this.checkboxes = new ArrayList();
        this.groupBoxes = new ArrayList();
        this.visible = false;
        categoryButton.panel.theme.categoryPanelConstructor(this, categoryButton, x, y);
    }

    public void draw(float x, float y) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.categoryPanelDraw(this, x, y);
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.categoryPanelMouseClicked(this, x, y, button);
        }
    }

    public void mouseReleased(int x, int y, int button) {
        for (UI theme : Client.getSkeetClickGui().getThemes()) {
            theme.categoryPanelMouseMovedOrUp(this, x, y, button);
        }
    }
}
