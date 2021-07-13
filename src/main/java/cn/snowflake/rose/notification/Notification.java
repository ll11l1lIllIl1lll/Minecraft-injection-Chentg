package cn.snowflake.rose.notification;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.utils.render.Colors;
import cn.snowflake.rose.utils.render.RenderUtil;
import cn.snowflake.rose.utils.render.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.BigDecimal;

public class Notification {
    private TimerUtil timer;
    private static Minecraft mc = Minecraft.getMinecraft();
    private float x, oldX, y, oldY, width;
    private String text;
    private int stayTime;
    private boolean done;
    private float stayBar;
    private int color;

    Notification(String text, int stayTime , final Type Type) {
        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        this.x = rs.getScaledWidth() - 2;
        this.y = rs.getScaledHeight() - 2;
        this.text = text;
        this.stayTime = stayTime;
        timer = new TimerUtil();
        timer.reset();
        stayBar = stayTime;
        done = false;
        if (Type.equals(Notification.Type.INFO)) {
            this.color = reAlpha(Colors.getColor(Color.WHITE), 1F);
        } else if (Type.equals(Notification.Type.ERROR)) {
            this.color = reAlpha(Colors.getColor(Color.RED), 1F);
        } else if (Type.equals(Notification.Type.SUCCESS)) {
            this.color = reAlpha(Colors.getColor(Color.GREEN), 1F);
        } else if (Type.equals(Notification.Type.WARNING)) {
            this.color = reAlpha(Colors.getColor(Color.YELLOW), 1F);
        }
    }

    public void draw(float prevY) {
        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        UnicodeFontRenderer font = Client.instance.fontManager.simpleton15;
        stayBar = timer.time();
        String text = this.text.replace("%timer"," \2477("+new BigDecimal((stayTime - stayBar )/1000 ).setScale(1, BigDecimal.ROUND_HALF_UP)+"s)");
        final float ySpeed = (rs.getScaledHeight() - prevY) /*/ (Minecraft.getDebugFPS() / 8) Client.delta*/;
        if (width != font.getStringWidth(text) + 8) {
            width = font.getStringWidth(text) + 8;
        }

//        drawBorderedRect(0, rs.getScaledHeight() , width, 14, (float) 0.5, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 120).getRGB());
//        if (done()) drawRect(finishedX , finishedY+20, ((width - 1) / stayTime) * stayBar, 1, color);
        GL11.glPushMatrix();
        RenderUtil.drawRoundedRectCSGO(1,prevY - 4 ,font.getStringWidth(text) + 6,prevY+9,new Color(22,22,22).getRGB());
        font.drawStringWithColor(text, 4,  (int)prevY-1, new Color(255, 255, 255, 124).getRGB());
        GL11.glColor4f(1,1,1,1);
        GL11.glPopMatrix();

        if (delete()) Client.instance.getNotificationManager().getNotifications().remove(this);


    }

    public boolean done() {
        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        return x <= rs.getScaledWidth()  - width;
    }

    public boolean delete() {
        ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        if(timer.reach(stayTime)){
            timer.reset();
            return true;
        }

        return false;

    }

    public enum Type {
        SUCCESS("SUCCESS", 0), INFO("INFO", 1), WARNING("WARNING", 2), ERROR("ERROR", 3);
        private Type(final String s, final int n) {
        }
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = ((float) 1 / 255) * c.getRed();
        float g = ((float) 1 / 255) * c.getGreen();
        float b = ((float) 1 / 255) * c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }
    public static void drawBorderedRect(double x, double y, double width, double height, double lineSize, int borderColor, int color) {
        RenderUtil.drawRect(x, y, x + width, y + height, color);
        RenderUtil.drawRect(x, y, x + width, y + lineSize, borderColor);
        RenderUtil.drawRect(x, y, x + lineSize, y + height, borderColor);
        RenderUtil.drawRect(x + width, y, x + width - lineSize, y + height, borderColor);
        RenderUtil.drawRect(x, y + height, x + width, y + height - lineSize, borderColor);
    }
    public static void drawRect(double x, double y, double width, double height, int color) {
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
        RenderUtil.drawRect(x, y, x + width, y + height, color);
    }
}
