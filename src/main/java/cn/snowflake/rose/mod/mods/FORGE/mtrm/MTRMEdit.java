package cn.snowflake.rose.mod.mods.FORGE.mtrm;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MTRMEdit extends Module {

    public MTRMEdit(){
        super("MTRMEdit","MTRM Edit", Category.FORGE);
        try {
            Class.forName("net.doubledoordev.mtrm.network.MessageSend");
         } catch (Throwable ex) {
         this.setWorking(false);
        }
    }

    @Override
    public String getDescription() {
        return "修改服务器合成(在.m/MTRMEditScript.cfg写入合成)!";
    }

    @Override
    public void onEnable() {
        File file = new File(mc.mcDataDir, "MTRMEditcript.cfg");
        boolean shapeless = false;
        boolean remove = false;
        boolean advanced = false;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            List<String> cfg = FileUtils.readLines(file);
             for (String recipes : cfg) {
                if(!recipes.startsWith("recipes."))continue;
                ByteBuf buf = Unpooled.buffer();
                advanced = recipes.startsWith("advanced:");
                String content2;
                String addition;
                if (advanced) {
                    final String[] c1 = recipes.split("\\|");
                    content2 = c1[0];
                    addition = c1[1];
                }
                else {
                    content2 = recipes;
                    addition = "";
                }
                content2 = content2.replaceAll("[\\[\\]]", "");
                content2 = content2.replaceAll(" ", "");
                final String[] c2 = content2.split(",");
                final String[] s = c2[0].split("\\(<");
                s[1] = "<" + s[1];
                c2[c2.length - 1] = c2[c2.length - 1].replace(");", "");
                s[1] = s[1].replace(");", "");
                final String[] s2 = s[0].split("\\.");
                final String s3 = s2[1];
                switch (s3) {
                    case "addShaped": {
                        shapeless = false;
                        break;
                    }
                    case "addShapeless": {
                        shapeless = true;
                        break;
                    }
                    case "remove": {
                        remove = true;
                        break;
                    }
                    default: {
                        Client.instance.getNotificationManager().addNotification(this,"\247cScriptError", Notification.Type.ERROR);
                        continue;
                    }
                }
                if (advanced) {
                    c2[8] = c2[8] + "]]);" + addition + "//";
                }
                buf.writeByte(0);
                buf.writeBoolean(remove);
                buf.writeBoolean(shapeless);
                buf.writeBoolean(false);
                buf.writeInt(c2.length);
                c2[0] = s[1];
                for (final String item : c2) {
                    final boolean tag = item != null && !item.equals("null");
                    buf.writeBoolean(tag);
                    if (tag) {
                        ByteBufUtils.writeUTF8String(buf, item);
                    }
                }
                mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("MTRM", buf));
            }
            Client.instance.getNotificationManager().addNotification(this,"Recipes send to server!", Notification.Type.ERROR);
            this.set(false);
        }
        catch (IOException e) {
            Client.instance.getNotificationManager().addNotification(this,"\247cScriptError!", Notification.Type.ERROR);
            this.set(false);
            e.printStackTrace();
        }
    }


}
