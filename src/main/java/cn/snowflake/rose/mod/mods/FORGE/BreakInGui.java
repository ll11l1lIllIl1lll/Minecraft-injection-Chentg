package cn.snowflake.rose.mod.mods.FORGE;

import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.render.MouseInputHandler;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Mouse;

public class BreakInGui extends Module {
    public final MouseInputHandler  left = new MouseInputHandler(0);

    public BreakInGui() {
        super("BreakInGui", "Break In Gui", Category.FORGE);
        setChinesename("gui\u65b9\u5757\u7834\u89e3");
    }

    @Override
    public String getDescription() {
        return "打开背包也能挖东西!";
    }

    @EventTarget
    public void onUpdate(EventUpdate update){
        if ((mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) &&  Mouse.isButtonDown(0)){
            int x = mc.objectMouseOver.blockX;
            int y = mc.objectMouseOver.blockY;
            int z = mc.objectMouseOver.blockZ;
            mc.thePlayer.swingItem();
            mc.playerController.onPlayerDamageBlock(x,y,z, 1);

        }
    }

}
