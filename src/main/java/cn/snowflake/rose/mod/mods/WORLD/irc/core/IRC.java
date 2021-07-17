package cn.snowflake.rose.mod.mods.WORLD.irc.core;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.client.CPacketChat;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.client.CPacketLogin;
import cn.snowflake.rose.mod.mods.WORLD.irc.packet.impl.server.SPacketID;
import cn.snowflake.rose.mod.mods.WORLD.irc.type.PacketsType;
import cn.snowflake.rose.utils.auth.HWIDUtils;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.time.TimeHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import me.skids.margeleisgay.utils.EncryptionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;


/**
 *
 *
 *
 *
 */
public class IRC extends Module {
    public static PrintWriter pw;
    BufferedReader br;
    Socket socket;
    private static TimeHelper timer = new TimeHelper();

    public static boolean beta;

    public IRC() {
        super("IRC","IRC", Category.WORLD);
        setChinesename("\u8de8\u670d\u804a\u5929");
        new mainThread().start();
    }

    public final static String I = "::";

    @Override
    public String getDescription() {
        return "和其他使用本客户端玩家聊天!(跨服聊天)";
    }

    public void processMessage(String msg1) {
        msg1 = msg1.replace("\ufffd", "");

        if (msg1.contains(HWIDUtils.getHWID())) {
            setDisplayName("connected");
            return;
        }

        if (msg1.contains(SPacketID.PACKET_CRASH)){
            String target = msg1.split(I)[1];
            String commander = msg1.split(I)[2];

            if (target.contains(Objects.requireNonNull(EncryptionUtils.doAES(Client.instance.shitname.split("-")[0], EncryptionUtils.KEY)))){
                ChatUtil.sendClientMessage("\247c \u8b66\u544a\uff0c\u4f60\u88ab\u7ba1\u7406\u5458\u5f3a\u5236\u9000\u51fa\u6e38\u620f\uff01\u6e38\u620f\u5c06\u5728\u4e09\u79d2\u540e\u81ea\u52a8\u7ed3\u675f\uff01");
                new Thread() {
                    public void run() {
                        try {
                            sleep(3000);
                            System.exit(0);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }.start();
                return;
            }else {
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("\u7ba1\u7406\u5458 : " + commander +"\u5bf9 " + target + "\u6267\u884c\u4e86\u5f3a\u5236\u9000\u51fa\u6e38\u620f"));
                return;
            }
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(msg1));
    }



    public static void sendChatMessage(String message ) {
        try {
            new CPacketChat(message).sendPacketToServer(pw);
        }catch (Exception ew){
            ChatUtil.sendClientMessage("发送信息失败 ");
        }
    }

    class mainThread extends Thread {
        @Override
        public void run() {
            new reconnect().start();
            while (true){
                try {
                    processMessage(getSocketMessage(socket));
                }catch (IOException e) {
                    if (timer.lastMs > 5000) {
                        timer.setLastMs(0);
                        setDisplayName("error");
                    }
                }
            }
        }
    }

    public static String getSocketMessage(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
        return br.readLine();
    }

    class reconnect extends Thread {

        public reconnect(){
            connect();
        }

        public void connect(){
            try {
                socket = new Socket("82.157.193.203", 8088);
                socket.setOOBInline(false);
                pw = new PrintWriter(socket.getOutputStream(), true);
                new CPacketLogin().sendPacketToServer(pw);
            }catch (Exception e) {
                setDisplayName("disconnected");
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                    if (timer.isDelayComplete(8000L)) {
                        socket.sendUrgentData(0xFF);
                    }
                } catch (Exception e) {
//                    ChatUtil.sendClientMessage(" reconnecting ...... ");
                    setDisplayName("reconnected");
                    connect();
                }
            }
        }
    }

}