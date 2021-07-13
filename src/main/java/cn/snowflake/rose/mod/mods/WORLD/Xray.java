package cn.snowflake.rose.mod.mods.WORLD;


import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Xray extends Module {
    public static ArrayList<Block> block = new ArrayList();
//    public static Value<Boolean> cave = new Value<Boolean>("Xray_Cave", false);
//    public static Value<Double> OPACITY = new Value<Double>("Xray_Opacity", 160.0, 0.0, 255.0, 5.0);
    private static int opacity = 160;
    public static List<Integer> blocks = new ArrayList<Integer>();
    public static boolean x = false;
    public Xray() {
        super("Xray","Xray",  Category.WORLD);
        blocks.add(16);
        blocks.add(56);
        blocks.add(14);
        blocks.add(15);
        blocks.add(129);//Emerald Ore
        blocks.add(73);
        setChinesename("\u65b9\u5757\u900f\u89c6");
    }

    @Override
    public String getDescription() {
        return "方块透视!";
    }

    @Override
    public void onEnable() {
        x = true;
        this.mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        x = false;
        this.mc.renderGlobal.loadRenderers();
    }

    public static boolean containsID(Block b) {
        return block.contains(b);
    }
    public static List<Integer> getBlocks() {
        return blocks;
    }

}
