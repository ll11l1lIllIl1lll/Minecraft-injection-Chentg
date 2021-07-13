package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;

public class Animation extends Module {
    public static Value<String> mode = new Value<String>("Animation","Mode",0);
    public static Value<Double> size = new Value<Double>("HitAnimation_Size",1.0d,0.1d,2.0d,0.05D);

    public Animation() {
        super("Animation","Animation",  Category.RENDER);
        this.mode.addValue("Jello");
        this.mode.addValue("Meme");
        this.mode.addValue("Swang");
        this.mode.addValue("Swank");
        this.mode.addValue("Swong");
    }

    @Override
    public String getDescription() {
        return "防砍动画!";
    }
    @EventTarget
    public void OnUpdate(EventUpdate e) {
        this.setDisplayName(this.mode.getModeAt(this.mode.getCurrentMode()));
    }

}
