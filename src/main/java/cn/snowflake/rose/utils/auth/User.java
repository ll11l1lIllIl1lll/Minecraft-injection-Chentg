package cn.snowflake.rose.utils.auth;

public class User {
    String hwid,name;

    public User(String hwid,String name){
        this.hwid = hwid;
        this.name = name;
    }

    public String getHwid() {
        return hwid;
    }

    public String getName() {
        return name;
    }
}
