package cn.snowflake.rose.utils.other;

import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.mods.RENDER.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class FakeFontRenderer extends FontRenderer {

    public FakeFontRenderer(GameSettings p_i1035_1_, ResourceLocation p_i1035_2_, TextureManager p_i1035_3_, boolean p_i1035_4_) {
        super(p_i1035_1_, p_i1035_2_, p_i1035_3_, p_i1035_4_);
    }

    @Override
    public int drawString(String string, int p_85187_2_, int p_85187_3_, int p_85187_4_, boolean p_85187_5_) {
        return super.drawString(string.replace(
                Minecraft.getMinecraft().getSession().getUsername(),
                ModManager.getModByClass(NameProtect.class).isEnabled()
                ? NameProtect.name.getText() : Minecraft.getMinecraft().getSession().getUsername() ),

                p_85187_2_, p_85187_3_, p_85187_4_, p_85187_5_);
    }


}
