package cn.snowflake.rose.command.commands;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.utils.Friend;
import cn.snowflake.rose.management.FriendManager;
import cn.snowflake.rose.utils.client.ChatUtil;

public class CommandFriend extends Command {
    public CommandFriend(String[] commands) {
        super(commands);
        setArgs(Client.chinese ? "-friend\u6216\u8005-f <add\u6216\u8005remove> <\u597d\u53cb\u540d\u5b57> (\u6dfb\u52a0\u597d\u53cb add \u6dfb\u52a0 remove \u5220\u9664)" : "friend/f add/remove Friendname");
    }

    @Override
    public void onCmd(String[] args) {
        if (args.length < 3) {
            ChatUtil.sendClientMessage(this.getArgs());
        } else {
            String option = args[1];
            String name = args[2];
            String alias = args.length > 3 ? args[3] : name;
            Friend friend = FriendManager.getFriend(name);
            if (!option.equalsIgnoreCase("a") && !option.equalsIgnoreCase("add")) {
                if (!option.equalsIgnoreCase("r") && !option.equalsIgnoreCase("remove")) {
                    ChatUtil.sendClientMessage(this.getArgs());
                } else if (friend != null) {
                    FriendManager.getFriends().remove(friend);
                    ChatUtil.sendClientMessage(Client.chinese ? "\u5220\u9664\u6210\u529f" : "Removed friend");
                }
            } else if (friend == null) {
                Friend newFriend = new Friend(name, alias);
                ChatUtil.sendClientMessage((Client.chinese ? "\u5220\u9664\u6210\u529f": "Added friend ") + name);
                FriendManager.getFriends().add(newFriend);
            } else {
                friend.setAlias(alias);
                ChatUtil.sendClientMessage("Changed alias to " + alias);
            }
        }
    }

}
