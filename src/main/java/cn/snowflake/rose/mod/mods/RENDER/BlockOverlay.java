package cn.snowflake.rose.mod.mods.RENDER;


import cn.snowflake.rose.events.impl.EventRender2D;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.*;
import cn.snowflake.rose.utils.render.Colors;
import cn.snowflake.rose.utils.render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;

import com.darkmagician6.eventapi.EventTarget;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MovingObjectPosition;

public class BlockOverlay extends Module {

    private Value<Boolean> renderString = new Value<Boolean>("BlockHighlight_Render String", true);

    public BlockOverlay() {
        super("BlockOverlay","Block Overlay",  Category.RENDER);
        setChinesename("\u65b9\u5757\u0049\u0044\u663e\u793a");
    }

    @Override
    public String getDescription() {
        return "方块ID显示!";
    }

    @EventTarget
    public void onRender(EventRender2D event) {
        Block block = this.mc.theWorld.getBlock(this.mc.objectMouseOver.blockX,this.mc.objectMouseOver.blockY,this.mc.objectMouseOver.blockZ);
        String s1 = ""+Block.getIdFromBlock(block);
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && (this.renderString.getValueState())) {
            FontRenderer font = Minecraft.getMinecraft().fontRenderer;
            ScaledResolution res = new ScaledResolution(this.mc,this.mc.displayWidth,this.mc.displayHeight);
            int x = res.getScaledWidth() / 2 - font.getStringWidth(s1) / 2;
            int y = res.getScaledHeight() / 2 + 8;
            Gui.drawRect(x, y, (x + font.getStringWidth(s1) + 3), (int) ((y + font.FONT_HEIGHT) + 0.5f), (int)RenderUtil.reAlpha(Colors.BLACK.c, 0.4f));
            font.drawString(I18n.format(s1), (x + 1), y + 1, -1, true);
        }
    }

}
