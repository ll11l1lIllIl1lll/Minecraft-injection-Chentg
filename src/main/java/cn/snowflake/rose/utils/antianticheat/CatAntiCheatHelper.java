package cn.snowflake.rose.utils.antianticheat;

import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.other.JReflectUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.injection.ClientLoader;
import net.minecraft.util.ScreenShotHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

public class CatAntiCheatHelper {
    static ArrayList<Module> render = new ArrayList<>();
    static boolean fucku;
    static ArrayList<Module> close = new ArrayList<>();

    public static byte[] screenshot() {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);
//            if (ScreenProtect.mode.isCurrentMode("CloseModule")) {



            BufferedImage bufferedImage = createScreenshot(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
            ImageIO.write(bufferedImage, "png", gzipOutputStream);
            gzipOutputStream.flush();
            gzipOutputStream.close();


//            if (close != null) { //重新打开 关闭掉的功能
//                for (Module c : close) {
//                    c.set(true);
//                }
//                close.clear();
//            }
        }
        catch (Exception ignored) {}
        return out.toByteArray();
    }

    public static BufferedImage createScreenshot(int width, int height, final Framebuffer framebufferIn) {
        IntBuffer pixelBuffer = null;
        try {
            pixelBuffer = (IntBuffer) JReflectUtility.getField(ScreenShotHelper.class, ClientLoader.runtimeObfuscationEnabled ? "field_74293_b":"pixelBuffer",true).get(null);
        } catch (IllegalAccessException ignored) {
        }
        int[] pixelValues =null;
        try {
            pixelValues = (int[]) JReflectUtility.getField(ScreenShotHelper.class, ClientLoader.runtimeObfuscationEnabled ? "field_74294_c":"pixelValues",true).get(null);
        } catch (IllegalAccessException ignored) {
        }
        if (OpenGlHelper.isFramebufferEnabled()) {
            width = framebufferIn.framebufferTextureWidth;
            height = framebufferIn.framebufferTextureHeight;
        }
        final int k = width * height;
        if (pixelBuffer == null || pixelBuffer.capacity() < k) {
            pixelBuffer = BufferUtils.createIntBuffer(k);
            JReflectUtility.setField(ScreenShotHelper.class,null,ClientLoader.runtimeObfuscationEnabled ? "field_74294_c" : "pixelValues",new int[k]);
        }
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        pixelBuffer.clear();
        if (OpenGlHelper.isFramebufferEnabled()) {
            GL11.glBindTexture(3553, framebufferIn.framebufferTexture);
            GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
        }else {
            GL11.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
        }
        pixelBuffer.get(pixelValues);
        TextureUtil.func_147953_a(pixelValues, width, height);
        BufferedImage bufferedimage = null;
        if (OpenGlHelper.isFramebufferEnabled()) {
            bufferedimage = new BufferedImage(framebufferIn.framebufferWidth, framebufferIn.framebufferHeight, 1);
            int i1;
            for (int l = i1 = framebufferIn.framebufferTextureHeight - framebufferIn.framebufferHeight; i1 < framebufferIn.framebufferTextureHeight; ++i1) {
                for (int j1 = 0; j1 < framebufferIn.framebufferWidth; ++j1) {
                    bufferedimage.setRGB(j1, i1 - l, pixelValues[i1 * framebufferIn.framebufferTextureWidth + j1]);
                }
            }
        }
        else {
            bufferedimage = new BufferedImage(width, height, 1);
            bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
        }

        return bufferedimage;
    }

}
