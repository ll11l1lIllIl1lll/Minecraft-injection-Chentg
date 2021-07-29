package cn.snowflake.rose.utils;

//slowly
public class Friend {
    private String name;
    private String alias;

    public Friend(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return this.name;
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }
}
