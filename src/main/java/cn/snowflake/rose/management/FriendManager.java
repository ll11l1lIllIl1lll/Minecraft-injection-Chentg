package cn.snowflake.rose.management;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.snowflake.rose.utils.Friend;
import net.minecraft.entity.player.EntityPlayer;

public class FriendManager {
    private static CopyOnWriteArrayList friends = new CopyOnWriteArrayList();

    public static CopyOnWriteArrayList getFriends() {
        return friends;
    }

    public static boolean isFriend(EntityPlayer player) {
        Iterator var2 = friends.iterator();

        while(var2.hasNext()) {
            Friend friend = (Friend)var2.next();
            if (friend.getName().equalsIgnoreCase(player.getCommandSenderName())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isFriend(String player) {
        Iterator var2 = friends.iterator();

        while(var2.hasNext()) {
            Friend friend = (Friend)var2.next();
            if (friend.getName().equalsIgnoreCase(player)) {
                return true;
            }
        }

        return false;
    }

    public static Friend getFriend(String name) {
        Iterator var2 = friends.iterator();

        while(var2.hasNext()) {
            Friend friend = (Friend)var2.next();
            if (friend.getName().equalsIgnoreCase(name)) {
                return friend;
            }
        }

        return null;
    }
}
