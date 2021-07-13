package cn.snowflake.rose.utils.client;

public class Rotation {
    public float yaw;
    public float pitch;


    public Rotation(float yaw, float pitch){
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
