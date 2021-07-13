package cn.snowflake.rose.mod.mods.WORLD;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.utils.Friend;
import cn.snowflake.rose.management.FriendManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.render.MouseInputHandler;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.player.EntityPlayer;

public class MCF extends Module {
    public MCF() {
        super("MCF","MCF", Category.WORLD);
        setChinesename("\u4e2d\u952e\u597d\u53cb");
    }

    private final MouseInputHandler handler = new MouseInputHandler(2);

    @Override
    public String getDescription() {
        return "鼠标中键玩家添加好友!";
    }

    @EventTarget
    public void onupdate(EventUpdate e){
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit != null && this.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            String name = this.mc.objectMouseOver.entityHit.getCommandSenderName();
            if (this.handler.canExcecute()) {
                if (FriendManager.isFriend(name)) {
                    for(int i = 0; i < FriendManager.getFriends().size(); ++i) {
                        Friend f = (Friend)FriendManager.getFriends().get(i);
                        if (f.getName().equalsIgnoreCase(name)) {
                            FriendManager.getFriends().remove(i);
                        }
                    }
                } else {
                    FriendManager.getFriends().add(new Friend(name, name));
                }
            }
        }

    }
}
