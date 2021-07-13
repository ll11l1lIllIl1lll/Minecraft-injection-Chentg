package cn.snowflake.rose.mod.mods.RENDER;


import cn.snowflake.rose.events.impl.EventRender2D;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.mcutil.GlStateManager;
import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.render.RenderUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.awt.*;


public class ArrowESP
        extends Module {
    private boolean dragging;
    float hue;
    private Value<Double> scale = new Value<Double>("ArrowESP_Scale", 1.0,1.0,5.0,0.1);
    private Value<Double> size = new Value<Double>( "ArrowESP_Size", 0.0,0.0, 200.0, 1.0);

    public ArrowESP() {
        super("ArrowESP", "Arrow ESP", Category.RENDER);
        setChinesename("\u7bad\u5934\u663e\u793a");
    }

    @Override
    public String getDescription() {
        return "箭头显示玩家!";
    }
    @EventTarget
    public void onGui(EventRender2D e) {
        ScaledResolution sr = new ScaledResolution(this.mc, mc.displayWidth, mc.displayHeight);
        GlStateManager.pushMatrix();
        int size = 50;
        float xOffset = sr.getScaledWidth() / 2 - 24.5f;
        float yOffset = sr.getScaledHeight() / 2 - 25.2f;
        float playerOffsetX = (float) mc.thePlayer.posX;
        float playerOffSetZ = (float) mc.thePlayer.posZ;
        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (o instanceof EntityPlayer&&o !=mc.thePlayer&&!((EntityPlayer) o).isInvisible()) {
                EntityPlayer ent = (EntityPlayer) o;
                float loaddist = 0.2F;
                float pTicks = JReflectUtility.getRenderPartialTicks();
                float posX = (float) (((ent.posX + (ent.posX - ent.lastTickPosX) * pTicks) - playerOffsetX) * loaddist);
                float posZ = (float) (((ent.posZ + (ent.posZ - ent.lastTickPosZ) * pTicks) - playerOffSetZ) * loaddist);
                Color color =  new Color(255,255,255);
                float cos = (float) Math.cos(mc.thePlayer.rotationYaw * (Math.PI * 2 / 360));
                float sin = (float) Math.sin(mc.thePlayer.rotationYaw * (Math.PI * 2 / 360));
                float rotY = -(posZ * cos - posX * sin);
                float rotX = -(posX * cos + posZ * sin);
                float var7 = 0 - rotX;
                float var9 = 0 - rotY;
                if (MathHelper.sqrt_double(var7 * var7 + var9 * var9) < size / 2 - 4) {
                    float angle = (float) (Math.atan2(rotY - 0, rotX - 0) * 180 / Math.PI);
                    float x = (float) ((size / 2) * Math.cos(Math.toRadians(angle))) + xOffset + size / 2;
                    float y = (float) ((size / 2) * Math.sin(Math.toRadians(angle))) + yOffset + size / 2;
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(x, y, 0);
                    GlStateManager.rotate(angle, 0, 0, 1);
                    float scale=this.scale.getValueState().floatValue();
                    float i = 3f;
                    GlStateManager.scale(scale, scale,scale);
                    while (i >0f) {
                        RenderUtil.drawESPCircle(this.size.getValueState().floatValue(), 0, i, 3, new Color(255,255,255,50));
                        i-=0.1f;
                    }
                    GlStateManager.popMatrix();
                }
            }
        }
        GlStateManager.popMatrix();
    }

}