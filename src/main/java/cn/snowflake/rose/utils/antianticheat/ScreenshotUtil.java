package cn.snowflake.rose.utils.antianticheat;

import cn.snowflake.rose.Client;
import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.zip.GZIPOutputStream;

public class ScreenshotUtil {


    public static BufferedImage bufferedImage = null;
    public static byte[] catanticheatImage = null;

//    public static IntBuffer intBuffer = null;





    /**
     *  Create by CNSnowFlake
     *
     * @return custom image byte[]
     */
    public static BufferedImage customScreenshot() {
        BufferedImage bufferedImage = null;
        try {
            JFileChooser jf = new JFileChooser();
            jf.setCurrentDirectory(new File(Minecraft.getMinecraft().mcDataDir.getParent()));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("\u6587\u4ef6(*.png,*.jpg,*.jpeg,*.bmp)", "png","jpg","jpeg","bmp");
            jf.setFileFilter(filter);
            int option = jf.showOpenDialog(jf);
            jf.setDialogTitle("\u8bf7\u5feb\u901f\u9009\u62e9\u4f60\u8981\u53d1\u9001\u7684\u622a\u56fe\u0028\u5c0f\u4e8e\u0031\u0036\u006d\u0029");
            File file = jf.getSelectedFile();
            if (file.exists()) {
                try {
                    bufferedImage = ImageIO.read(new FileInputStream(file));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return bufferedImage;
    }


    /**
     *  Create by CNSnowFlake
     *
     * @return custom image byte[]
     */
    public static byte[] catAnticheatScreenhost() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);
            JFileChooser jf = new JFileChooser();
            jf.setCurrentDirectory(new File(Minecraft.getMinecraft().mcDataDir.getParent()));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("\u6587\u4ef6(*.png,*.jpg,*.jpeg,*.bmp)", "png","jpg","jpeg","bmp");
            jf.setFileFilter(filter);
            int option = jf.showOpenDialog(jf);
            jf.setDialogTitle("\u8bf7\u5feb\u901f\u9009\u62e9\u4f60\u8981\u53d1\u9001\u7684\u622a\u56fe\u0028\u5c0f\u4e8e\u0031\u0036\u006d\u0029");
            File file = jf.getSelectedFile();
            if (file.exists()) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
                    ImageIO.write(bufferedImage, "png", gzipOutputStream);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            gzipOutputStream.flush();
            gzipOutputStream.close();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return out.toByteArray();
    }

}
