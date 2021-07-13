package cn.snowflake.rose.mod.mods.PLAYER;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.injection.ClientLoader;

public class NoWeb extends Module {
    public NoWeb() {
        super("NoWeb","No Web", Category.PLAYER);
    }

    @Override
    public String getDescription() {
        return "无视蜘蛛网!";
    }

    @EventTarget
    public void onUpdate(EventUpdate e)   {
        ReflectionHelper.setPrivateValue(Entity.class,mc.thePlayer, false, ClientLoader.runtimeObfuscationEnabled ? "field_70134_J": "isInWeb");
    }

}
