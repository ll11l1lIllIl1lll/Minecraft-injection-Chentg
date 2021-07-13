package cn.snowflake.rose.management;

import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.command.commands.*;

import java.util.ArrayList;

public class CommandManager {
    private static ArrayList<Command> commands = new ArrayList<Command>();

    public CommandManager() {
        add(new CommandHelp(new String[] {"help"}));
        add(new CommandToggle(new String[] {"toggle", "t"}));
        add(new CommandBind(new String[] {"bind"}));
        add(new CommandTP(new String[]{"tp"}));
        add(new CommandXray(new String[]{"xray"}));
        add(new CommandFriend(new String[]{"friend","f"}));
        add(new CommandHidden(new String[] {"hidden"}));
        add(new CommandScreenshot(new String[]{"screenshot","ss"}));
        add(new CommandPlugins(new String[] {"plugins"}));
        add(new CommandIRC(new String[] {"irc",""}));
        add(new CommandLogin(new String[]{"login","l"}));
        add(new CommandVClip(new String[]{"vclip","v"}));
        add(new CommandSpammer(new String[]{"sp","spammer"}));
//        add(new CommandTP2(new String[]{"tp2"}));
    }

    public void add(Command c) {
        CommandManager.commands.add(c);
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public static String removeSpaces(String message) {
        String space = " ";
        String doubleSpace = "  ";
        while (message.contains(doubleSpace)) {
            message = message.replace(doubleSpace, space);
        }
        return message;
    }
}
