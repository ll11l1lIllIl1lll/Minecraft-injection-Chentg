package cn.snowflake.rose.mod.mods.WORLD;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.auth.HWIDUtils;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.time.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class IRC extends Module {
    static PrintWriter pw;
    BufferedReader br;
    Socket socket;
    private final TimeHelper timer = new TimeHelper();
    private boolean messageThread;

    public IRC() {
        super("IRC","IRC", Category.WORLD);
        new reconnect().start();
        setChinesename("\u8de8\u670d\u804a\u5929");
    }

    @Override
    public String getDescription() {
        return "和其他使用本客户端玩家聊天!(跨服聊天)";
    }

    /**
     *
     *  Author : Sevoive
     * @param msg1
     */
    public void processMessage(String msg1) {
        msg1 = msg1.replace("\ufffd", "");//删除傻逼异常字符
        if (msg1.contains(HWIDUtils.getHWID())) {
            setDisplayName("connected");
            return;
        }
        if (msg1.contains("GETHELP")) {
            ChatUtil.sendMessageWithoutPrefix("\u00a77\u00a7m\u00a7l----------------------------------");
            ChatUtil.sendMessageWithoutPrefix("\u00a7b\u00a7lChentg IRC Help");
            ChatUtil.sendMessageWithoutPrefix("\u00a77\u00a7m\u00a7l----------------------------------");
            ChatUtil.sendMessageWithoutPrefix("\u00a7b\u00a7lUser Type");
            ChatUtil.sendMessageWithoutPrefix("\u00a7b[LIST] >\u00a77 Use -IRC \u00a7bLIST \u00a77To List All User");
            ChatUtil.sendMessageWithoutPrefix("\u00a77\u00a7m\u00a7l----------------------------------");
            return;
        }
//        if (msg1.contains("LIST")) {
//            ChatUtil.sendMessageWithoutPrefix("");
//            ChatUtil.sendMessageWithoutPrefix("\u00a77[\u00a7bIRC\u00a77] Getting List.Pls Wait A Minute.");
//            sendIRCMessage("\247aName: \2477" + this.mc.thePlayer.getCommandSenderName() + " \247f| \247bUser: \2477 "+ Client.shitname  , true);
//            return;
//        }
        if (msg1.contains("COUNTER//")) {
            ChatUtil.sendMessageWithoutPrefix("\u00a77[\u00a7bIRC\u00a77]" + msg1.split("//")[1]);
            return;
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(msg1));
    }



    public static void sendIRCMessage(String message,boolean prefix) {
        try {
            if(prefix) {
                //TODO 发送信息
                pw.println("#IRC#Msg::"+ Client.shitname +" : " + message);

            } else {
                pw.println(message);
            }
        }catch (Exception ew){
            ChatUtil.sendClientMessage("发送信息失败 ");
        }
    }

    class connect extends Thread {
        @Override
        public void run() {
            try {
                messageThread = false;
                socket = new Socket("82.157.193.203", 43691);
                pw = new PrintWriter(socket.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String test = "#IRC#Login::"+HWIDUtils.getHWID() ;
                sendIRCMessage(test,false);
                messageThread = true;
                new readMessage().start();
            }catch (Throwable e) {
                e.printStackTrace();
                setDisplayName("disconnected");
            }
        }

    }
    class readMessage extends Thread {
        @Override
        public void run() {
            this.setName("ReadMessage");
            try {
                while (messageThread) {
                    String msg1 = br.readLine();
                    if (msg1 == null) continue;
                    processMessage(msg1);
                }
            }catch (IOException e) {
                setDisplayName("connectionless");
            }
        }
    }

    class reconnect extends Thread {
        @Override
        public void run() {
            this.setName("Reconnect");
            new connect().start();
            try {
                reconnect.sleep(10000L);
            }catch (InterruptedException e1) {
            }
            do {
                try {
                    do {
                        socket.sendUrgentData(255);
                        reconnect.sleep(3000L);
                    } while (true);
                }catch (IOException e) {
                    if (!timer.isDelayComplete(8000L)) continue;
                    timer.reset();
                    new connect().start();
                }
                catch (NullPointerException | InterruptedException ignored) {
                }
            } while (true);
        }
    }
    public void onEnable() {
    }

}