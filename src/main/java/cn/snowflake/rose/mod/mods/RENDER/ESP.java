package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.events.impl.EventRender3D;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.*;
import cn.snowflake.rose.utils.mcutil.AltAxisAlignedBB;
import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.render.RenderUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class ESP extends Module {
    public Value<Boolean> players = new Value("ESP_Player", true);

    public Value<Boolean> otherentity = new Value("ESP_OtherEntity", true);
    public Value<Boolean> animal = new Value("ESP_Animal", false);
    public Value<Boolean> moster = new Value("ESP_Mob", false);
    public Value<Boolean> village = new Value("ESP_village", false);
    public Value<Boolean> invisible = new Value("ESP_Invisible", false);
    private static Map<EntityPlayer, float[][]> entities = new HashMap<EntityPlayer, float[][]>();
    public Value<String> mode = new Value<>("ESP","Mode",0);
    public ESP() {
        super("ESP","ESP",  Category.RENDER);
//        this.mode.addValue("2DBox");
        this.mode.addValue("2DBox");
        this.mode.addValue("Box");
        setChinesename("\u0033\u0044\u900f\u89c6");
    }
    @Override
    public String getDescription() {
        return "3D透视!";
    }

    public void renderBox(Entity entity,double r,double g, double b) {
        if(entity.isInvisible() && !invisible.getValueState().booleanValue()) {
            return;
        }
        double x = RenderUtil.interpolate((double)entity.posX, (double)entity.lastTickPosX, JReflectUtility.getRenderPartialTicks());
        double y = RenderUtil.interpolate((double)entity.posY, (double)entity.lastTickPosY,JReflectUtility.getRenderPartialTicks());
        double z = RenderUtil.interpolate((double)entity.posZ, (double)entity.lastTickPosZ,JReflectUtility.getRenderPartialTicks());
        GL11.glPushMatrix();
        RenderUtil.pre();
        GL11.glLineWidth((float)1.0f);
        GL11.glEnable((int)2848);
        GL11.glColor3d(r,g,b);
        RenderUtil.drawOutlinedBoundingBox(new AltAxisAlignedBB(
                entity.boundingBox.minX
                        - 0.05
                        - entity.posX
                        + (entity.posX - RenderManager.renderPosX),
                entity.boundingBox.minY
                        - entity.posY
                        + (entity.posY - RenderManager.renderPosY),
                entity.boundingBox.minZ
                        - 0.05
                        - entity.posZ
                        + (entity.posZ - RenderManager.renderPosZ),
                entity.boundingBox.maxX
                        + 0.05
                        - entity.posX
                        + (entity.posX - RenderManager.renderPosX),
                entity.boundingBox.maxY
                        + 0.1
                        - entity.posY
                        + (entity.posY - RenderManager.renderPosY),
                entity.boundingBox.maxZ
                        + 0.05
                        - entity.posZ
                        + (entity.posZ - RenderManager.renderPosZ)));
        GL11.glDisable((int)2848);
        RenderUtil.post();
        GL11.glPopMatrix();
    }

    @EventTarget
    public void onRender(EventRender3D e){
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                Entity entity1 = entity;
                if (canTarget(entity1)) {
                    double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * JReflectUtility.getRenderPartialTicks() - RenderManager.renderPosX;
                    double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * JReflectUtility.getRenderPartialTicks() - RenderManager.renderPosY;
                    double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * JReflectUtility.getRenderPartialTicks() - RenderManager.renderPosZ;
                    if (this.mode.isCurrentMode("Box")){
                        renderBox(entity,255,255,255);
                    }else if (this.mode.isCurrentMode("2DBox")){
                        GL11.glPushMatrix();
                        GL11.glColor4d((double) 1.0, (double) 1.0, (double) 1.0, (double) 0.0);
                        double size = 0.25;
                        double boundindY = entity.boundingBox.maxY - entity.boundingBox.minY;
                        RenderUtil.drawBoundingBox(new AltAxisAlignedBB(posX - size, (double) posY, posZ - size, posX + size, (double) (posY + boundindY), posZ + size));
                        RenderUtil.renderOne();//renderOne
                        RenderUtil.drawBoundingBox(new AltAxisAlignedBB(posX - size, (double) posY, posZ - size, posX + size, (double) (posY + boundindY), posZ + size));
                        GL11.glStencilFunc((int) 512, (int) 0, (int) 15);
                        GL11.glStencilOp((int) 7681, (int) 7681, (int) 7681);
                        GL11.glPolygonMode((int) 1032, (int) 6914);
                        RenderUtil.drawBoundingBox(new AltAxisAlignedBB(posX - size, (double) posY, posZ - size, posX + size, (double) (posY + boundindY), posZ + size));
                        GL11.glStencilFunc((int) 514, (int) 1, (int) 15);
                        GL11.glStencilOp((int) 7680, (int) 7680, (int) 7680);
                        GL11.glPolygonMode((int) 1032, (int) 6913);
                        RenderUtil.setColor(entity1);//draw
                        RenderUtil.drawBoundingBox(new AltAxisAlignedBB(posX - size, (double) posY, posZ - size, posX + size, (double) (posY + boundindY), posZ + size));
                        GL11.glPolygonOffset((float) 1.0f, (float) 2000000.0f);
                        GL11.glDisable((int) 10754);
                        GL11.glEnable((int) 2929);
                        GL11.glDepthMask((boolean) true);
                        GL11.glDisable((int) 2960);
                        GL11.glDisable((int) 2848);
                        GL11.glHint((int) 3154, (int) 4352);
                        GL11.glEnable((int) 3042);
                        GL11.glEnable((int) 2896);
                        GL11.glEnable((int) 3553);
                        GL11.glEnable((int) 3008);
                        GL11.glPopAttrib();
                        GL11.glColor4d((double) 1.0, (double) 1.0, (double) 1.0, (double) 1.0);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }

    private boolean canTarget(Entity entity) {

        if (entity instanceof EntityPlayer && !players.getValueState()) {
            return false;
        }
        if (entity instanceof EntityAnimal && !animal.getValueState()) {
            return false;
        }
        if ((entity instanceof EntityMob || entity instanceof EntitySlime|| entity instanceof EntityBat || entity instanceof EntityVillager)&& !moster.getValueState()) {
            return false;
        }
        if (entity instanceof EntityVillager && !village.getValueState()) {
            return false;
        }
        if (entity.isInvisible() && !invisible.getValueState()) {
            return false;
        }
        if (entity instanceof EntityCreature && !otherentity.getValueState()) {
            return false;
        }
        if (entity == mc.thePlayer){
            return false;
        }
        return true;
    }
}
