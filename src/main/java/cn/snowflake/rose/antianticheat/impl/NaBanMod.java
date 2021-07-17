package cn.snowflake.rose.antianticheat.impl;

import cn.snowflake.rose.events.impl.EventFMLChannels;
import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.utils.client.ChatUtil;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.EventType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.MathHelper;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class NaBanMod {
    public static String cpuID = "";

    public NaBanMod(){
        EventManager.register(this);
        cpuID = new StringBuilder().insert(0,getCpuID()).append(getSerialNumber()).toString();
    }


    @EventTarget
    public void onPacket(EventPacket e) {
        if (e.getType() == EventType.SEND){
            if (e.getPacket() instanceof C17PacketCustomPayload){
                if (((C17PacketCustomPayload) e.getPacket()).func_149559_c().equals("naban")){
                    e.setCancelled(true);

                    PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                    packetBuffer.writeInt(cpuID.length());
                    packetBuffer.writeBytes(cpuID.getBytes(StandardCharsets.UTF_8));

                    Minecraft.getMinecraft().getNetHandler().getNetworkManager().scheduleOutboundPacket(new C17PacketCustomPayload( ((C17PacketCustomPayload) e.getPacket()).func_149559_c() , packetBuffer));

                }
            }
        }


    }

    public static String cpuid() {
        Process exec = null;
        try {
            exec = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
            exec.getOutputStream().close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        Scanner scanner;
        (scanner = new Scanner(exec.getInputStream())).next();
        String next = scanner.next();
        System.out.println(next);
        return next;
    }


    public static String getCpuID(){
        Random random = new Random();
        //A0375
        StringBuilder randomID = new StringBuilder("BFEBFBFF000");
        String[] strings = new String[]{"E","A","0","1","2","3","4","5","6","7","8","9"};
        for (int i = 0;i <= 4 ; i++){
            int i1 = MathHelper.getRandomIntegerInRange(random,0,11);
            randomID.append(strings[i1]);
        }
        return randomID.toString();
    }

    public static String getSerialNumber(){
        Random random = new Random();
        //A0375
        StringBuilder randomID = new StringBuilder("-1");
        String[] strings = new String[]{"0","1","2","3","4","5","6","7","8","9"};
        for (int i = 0;i <= 8 ; i++){
            int i1 = MathHelper.getRandomIntegerInRange(random,0,9);
            randomID.append(strings[i1]);
        }
        return randomID.toString();
    }

    public static String getSerialNumber(String var0) {
        String var1 = "";

        try {
            File var2 = File.createTempFile("nmsl", ".vbs");
            var2.deleteOnExit();
            FileWriter var3 = new FileWriter(var2);
            String var4 = (new StringBuilder()).insert(0, "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"").append(var0).append("\")\nWscript.Echo objDrive.SerialNumber").toString();
            var3.write(var4);
            var3.close();
            Process var6 = Runtime.getRuntime().exec((new StringBuilder()).insert(0, "cscript //NoLogo ").append(var2.getPath()).toString());
            BufferedReader var7 = new BufferedReader(new InputStreamReader(var6.getInputStream()));

            while(true) {
                var4 = var7.readLine();
                if (var4 == null) {
                    var7.close();
                    break;
                }

                var1 = (new StringBuilder()).insert(0, var1).append(var4).toString();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return var1.trim();
    }

}
