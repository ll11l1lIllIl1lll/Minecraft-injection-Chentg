package cn.snowflake.rose.command;

import net.minecraft.client.Minecraft;

public class Command {
    private String[] commands;
    private String args;
    public Minecraft mc = Minecraft.getMinecraft();
    public Command(String[] commands) {
        this.commands = commands;
    }

    public String[] getCommands() {
        return this.commands;
    }

    public void onCmd(String[] args) {

    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getArgs() {
        return this.args;
    }
}
