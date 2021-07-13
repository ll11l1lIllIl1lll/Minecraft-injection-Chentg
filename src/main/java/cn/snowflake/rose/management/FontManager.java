package cn.snowflake.rose.management;


import cn.snowflake.rose.Client;
import cn.snowflake.rose.utils.render.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author SnowFlake
 *
 * 1:13:30 2019
 */
public class FontManager {
    private HashMap<String, HashMap<Float, UnicodeFontRenderer>> fonts = new HashMap();
    public UnicodeFontRenderer simpleton10;
    public UnicodeFontRenderer simpleton11;
    public UnicodeFontRenderer simpleton12;
    public UnicodeFontRenderer simpleton13;
    public UnicodeFontRenderer simpleton15;
    public UnicodeFontRenderer simpleton20;
    public UnicodeFontRenderer simpleton30;

    public UnicodeFontRenderer robotoregular19;
    
    public UnicodeFontRenderer notif20;
    public UnicodeFontRenderer notif40;

    public UnicodeFontRenderer wqy19;
    public UnicodeFontRenderer wqy13;

    public FontManager() {
        this.simpleton10 = this.getFont("simpleton", 10.0f, true);
        this.simpleton11 = this.getFont("simpleton", 11.0f, true);
        this.simpleton12 = this.getFont("simpleton", 12.0f, true);
        this.simpleton13 = this.getFont("simpleton", 13.0f, true);
        this.simpleton15 = this.getFont("simpleton", 15.0f, true);
        this.robotoregular19 = this.getFont("roboto-regular", 19.0f);
        this.simpleton20 = this.getFont("simpleton", 20.0f, true);
        this.simpleton30 = this.getFont("simpleton", 30.0f, true);
        this.notif20 = this.getFont("stylesicons", 20.0f);
        this.notif40 = this.getFont("stylesicons", 40.0f);

        if (Client.chinese) {
            this.wqy13 = this.getChineseFont("wqy", 13);
            this.wqy19 = this.getChineseFont("wqy", 19);
        }
    }

    public UnicodeFontRenderer getFont(String name, float size, boolean b) {
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && ((HashMap)this.fonts.get(name)).containsKey(Float.valueOf(size))) {
                return (UnicodeFontRenderer)((HashMap)this.fonts.get(name)).get(Float.valueOf(size));
            }
            InputStream inputStream = this.getClass().getResourceAsStream("/assets/fonts/" + name + ".otf");

            Font font = null;
            font = Font.createFont((int)0, (InputStream)inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());

            HashMap map = new HashMap();
            if (this.fonts.containsKey(name)) {
                map.putAll(this.fonts.get(name));
            }
            map.put(Float.valueOf(size), unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return unicodeFont;
    }

    public UnicodeFontRenderer getChineseFont(String name, float size) {
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && ((HashMap)this.fonts.get(name)).containsKey(Float.valueOf((float)size))) {
                return (UnicodeFontRenderer)((HashMap)this.fonts.get(name)).get(Float.valueOf((float)size));
            }
            InputStream inputStream = this.getClass().getResourceAsStream("/assets/fonts/" + name + ".ttf");

            Font font = null;
            font = Font.createFont((int)0, (InputStream)inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size),true);
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());

            HashMap map = new HashMap();
            if (this.fonts.containsKey(name)) {
                map.putAll((Map)this.fonts.get(name));
            }
            map.put(Float.valueOf((float)size), unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return unicodeFont;
    }

    public UnicodeFontRenderer getFont(String name, float size) {
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && ((HashMap)this.fonts.get(name)).containsKey(Float.valueOf((float)size))) {
                return (UnicodeFontRenderer)((HashMap)this.fonts.get(name)).get(Float.valueOf((float)size));
            }
            InputStream inputStream = this.getClass().getResourceAsStream("/assets/fonts/" + name + ".ttf");

            Font font = null;
            font = Font.createFont((int)0, (InputStream)inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().getLanguageManager().isCurrentLanguageBidirectional());

            HashMap map = new HashMap();
            if (this.fonts.containsKey(name)) {
                map.putAll((Map)this.fonts.get(name));
            }
            map.put(Float.valueOf((float)size), unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return unicodeFont;
    }

}
