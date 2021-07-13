package cn.snowflake.rose.mod.mods.WORLD;

import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.time.TimeHelper;
import com.darkmagician6.eventapi.EventTarget;
import joptsimple.internal.Strings;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.server.S3APacketTabComplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plugins extends Module {
    public Plugins() {
        super("Plugins", "Plugins", Category.WORLD);
        setChinesename("\u67e5\u770b\u63d2\u4ef6");
    }

    @Override
    public String getDescription() {
        return "查看服务器插件!";
    }

    @Override
    public void onEnable() {
        if(mc.thePlayer == null)
            return;

        mc.getNetHandler().addToSendQueue(new C14PacketTabComplete("/"));
        time.reset();
        super.onEnable();
    }

    @EventTarget
    public void packet(EventPacket e){
        if(e.getPacket() instanceof S3APacketTabComplete) {
            final S3APacketTabComplete s3APacketTabComplete = (S3APacketTabComplete) e.getPacket();

            final List<String> plugins = new ArrayList<>();
            final String[] commands = s3APacketTabComplete.func_149630_c();

            for(final String command1 : commands) {
                final String[] command = command1.split(":");

                if(command.length > 1) {
                    final String pluginName = command[0].replace("/", "");

                    if(!plugins.contains(pluginName))
                        plugins.add(pluginName);
                }
            }

            Collections.sort(plugins);

            if(!plugins.isEmpty()) {
                ChatUtil.sendClientMessage("\247fFound "+plugins.size() +" Plugins -> \247c" + Strings.join(plugins.toArray(new String[0]), "\2477, \247c"));
            }else {
                ChatUtil.sendClientMessage("\247fNo plugins found.");
            }
            set(false);
            time.reset();
        }
    }
    @EventTarget
    public void update(EventUpdate e){
        if(time.isDelayComplete(1000)) {
            ChatUtil.sendClientMessage("\247cPlugins check timed out...");
            time.reset();
            set(false);
        }
    }

    public TimeHelper time  = new TimeHelper();

}
