package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.events.impl.EventRender3D;
import cn.snowflake.rose.management.FriendManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.GLUtil;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.other.JReflectUtility;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @Auther: SnowFlake
 * @Date: 2021/7/17 17:34
 */
public class Nametags extends Module {
    private Value<Boolean> invisible = new Value<Boolean>("NameTags_Invisible", false);
    private Value<Double> size = new Value<Double>("NameTags_Size", 1d, 1d, 20d, 0.1D);


    public Nametags() {
        super("NameTags", "Name Tags", Category.RENDER);
    }

    @EventTarget
    public void onRender(EventRender3D event) {
        for (Object o : mc.theWorld.playerEntities) {
            EntityPlayer p = (EntityPlayer) o;
            if(p != mc.thePlayer) {
                double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * JReflectUtility.getRenderPartialTicks()
                        - RenderManager.renderPosX;
                double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * JReflectUtility.getRenderPartialTicks()
                        - RenderManager.renderPosY;
                double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * JReflectUtility.getRenderPartialTicks()
                        - RenderManager.renderPosZ;
                renderNameTag(p, p.getCommandSenderName(), pX, pY, pZ);
            }
        }
    }

    private void renderNameTag(EntityPlayer entity, String tag, double pX, double pY, double pZ) {
        if(entity.isInvisible() && !invisible.getValueState().booleanValue()) {
            return;
        }
        FontRenderer fr = mc.fontRenderer;
        float size = mc.thePlayer.getDistanceToEntity(entity) / 20.0f;
        if(size < 1.1f) {
            size = 1.1f;
        }
        pY += (entity.isSneaking() ? 0.5D : 0.7D);
        float scale = (float) (size * this.size.getValueState());
        scale /= 100f;
        tag = entity.getCommandSenderName();
        String hp = "" + (int)entity.getHealth();

        String f = "";
        if(FriendManager.isFriend(entity)) {
            f = "\247a[Friend]";
        } else {
            f = "";
        }
        String lol = f  + tag;

        double plyHeal = entity.getHealth();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) pX, (float) pY + 1.4F, (float) pZ);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-scale, -scale, scale);
        GLUtil.setGLCap(2896, false);
        GLUtil.setGLCap(2929, false);
        int width = 31;
        GLUtil.setGLCap(3042, true);
        GL11.glBlendFunc(770, 771);

        int var16 = fr.getStringWidth(lol+"-\247a"+hp) / 2;

        GL11.glColor3f(1,1,1);

        GL11.glScaled(0.6f, 0.6f, 0.6f);


        GL11.glScaled(1, 1, 1);
        int color = new Color(188,0,0).getRGB();
        if(entity.getHealth() > 20) {
            color = -42292;
        }

        int xLeft = (-width / 2 - 66);

        fr.drawStringWithShadow(lol+" \247a"+hp, (int) ((int)0 - var16 + 0.5f),
                (int)(-16f) ,
                16777215);

        GLUtil.revertAllCaps();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }


}
