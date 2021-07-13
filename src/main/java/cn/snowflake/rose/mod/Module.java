package cn.snowflake.rose.mod;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.mods.RENDER.Notifications;
import cn.snowflake.rose.notification.Notification;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventManager;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public class Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    private String rendername;
    private final String name;
    private final Category category;
    private String displayName;
    public String chinesename;
    public String description = "unknown";
    private int key;

    private boolean isEnabled;
    public boolean openValues;
    public boolean working = true;
    public boolean hidden ;

    public Module(String name,Category category) {
        this.name = name;
        this.category = category;
        this.key = -1;
    }

    public Module(String name,String rendername,Category category) {
        this.name = name;
        this.category = category;
        this.rendername = rendername;
        this.key = -1;
    }

    public void onDisable() {
        if (ModManager.getModByClass(Notifications.class).isEnabled){
            Client.instance.getNotificationManager().addNotification("Module", "\u00a7c" + this.getName()+" Disabled !", Notification.Type.INFO);
        }
    }
    public boolean isHidden() {
        return hidden;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public String getRenderName() {
        return rendername;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getName() {
        return working ? name : name;
    }

    public String getChinesename() {
        if (chinesename != null){
            return  (working ? chinesename : "\2474"+chinesename);
        }else {
            return name;
        }
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getdisplayName() {
        return displayName;
    }

    public String getDescription() { return this.description; }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean hasValues() {
        for (Value value : Value.list) {
            String name = value.getValueName().split("_")[0];
            if (!name.equalsIgnoreCase(this.getName())) continue;
            return true;
        }
        return false;
    }

    public void onToggle() {
    }

    public void set(boolean state, boolean save) {
        if(!this.working)return;

        this.isEnabled = state;
        this.onToggle();
        if (state) {
            if (this.mc.theWorld != null) {
                this.onEnable();
            }
            EventManager.register(this);
        } else {
            if (this.mc.theWorld != null) {
                this.onDisable();
            }
            EventManager.unregister(this);
        }
        if (save) {
            Client.instance.fileMgr.saveMods();
        }
    }

    public void unregister(){
        EventManager.unregister(this);
    }
    public void register(){
        EventManager.register(this);

    }


    public void onEnable() {
        if (ModManager.getModByClass(Notifications.class).isEnabled){
            Client.instance.getNotificationManager().addNotification("Module", "\u00a7a" + this.getName()+" Enabled !", Notification.Type.INFO);
        }
    }

    public int getValueSize(){
        ArrayList<Value> size = new ArrayList();
        for (Value value : Value.list){
            if (value.getValueName().split("_")[0].equalsIgnoreCase(this.name)){
                size.add(value);
            }
        }
        return size.size();
    }

    public void set(boolean state) {
        this.set(state, false);
        Client.instance.fileMgr.saveMods();
    }

}