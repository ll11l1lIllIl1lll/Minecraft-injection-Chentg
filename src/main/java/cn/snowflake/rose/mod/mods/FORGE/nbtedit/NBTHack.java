package cn.snowflake.rose.mod.mods.FORGE.nbtedit;

import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.other.JReflectUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.List;

public class NBTHack extends Module {

    private Entity pointedEntity;
    private MovingObjectPosition moving;
    public Value<Double> Reach = new Value<Double>("NBTHack_Reach", 50.0, 5.0D, 100.0D,1.0D);

    public NBTHack() {
        super("NBTHack", "NBT Hack", Category.FORGE);
        try {
            Class.forName("com.mcf.davidee.nbtedit.NBTEdit");
        } catch (Exception var2) {
            this.working = false;
        }
    }

    @Override
    public String getDescription() {
        return "强制打开NBTEdit的Gui界面!";
    }

    @Override
    public void onEnable() {
        try {
            Class<?> clazz = Class.forName("com.mcf.davidee.nbtedit.NBTEdit");
            Object proxy = clazz.getField("proxy").get(null);
            Class<?> clientproxy = Class.forName("com.mcf.davidee.nbtedit.forge.ClientProxy");
            NBTTagCompound compound = new NBTTagCompound();
            getMouseOver(JReflectUtility.getRenderPartialTicks(), Reach.getValueState());
            MovingObjectPosition mop = this.moving;
            TileEntity entity = mc.theWorld.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            if (entity != null) {
                entity.writeToNBT(compound);
                clientproxy.getMethod("openEditGUI", int.class, int.class, int.class, NBTTagCompound.class).invoke(proxy, entity.xCoord, entity.yCoord, entity.zCoord, compound);
            }else if (mop.entityHit != null) {
                mop.entityHit.writeToNBT(compound);
                clientproxy.getMethod("openEditGUI", int.class, NBTTagCompound.class).invoke(proxy, mop.entityHit.getEntityId(), compound);
            }else {
                mc.thePlayer.writeToNBT(compound);
                clientproxy.getMethod("openEditGUI", int.class, NBTTagCompound.class).invoke(proxy, mc.thePlayer.getEntityId(), compound);
            }
            this.set(false);
        }
        catch (Throwable ex) {
            this.set(false);
        }
    }


    public void getMouseOver(float p_78473_1_ ,double reach)
    {
        if (this.mc.renderViewEntity != null)
        {
            if (this.mc.theWorld != null)
            {
                this.mc.pointedEntity = null;
                double d0 = (double)reach;
                this.moving= this.mc.renderViewEntity.rayTrace(d0, p_78473_1_);
                double d1 = d0;
                Vec3 vec3 = this.mc.renderViewEntity.getPosition(p_78473_1_);
                if (this.moving!= null) {
                    d1 = this.moving.hitVec.distanceTo(vec3);
                }

                Vec3 vec31 = this.mc.renderViewEntity.getLook(p_78473_1_);
                Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
                this.pointedEntity = null;
                Vec3 vec33 = null;
                float f1 = 1.0F;
                List list = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
                double d2 = d1;

                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity = (Entity)list.get(i);

                    if (entity.canBeCollidedWith())
                    {
                        float f2 = entity.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                        if (axisalignedbb.isVecInside(vec3))
                        {
                            if (0.0D < d2 || d2 == 0.0D)
                            {
                                this.pointedEntity = entity;
                                vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                                d2 = 0.0D;
                            }
                        }
                        else if (movingobjectposition != null)
                        {
                            double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                            if (d3 < d2 || d2 == 0.0D)
                            {
                                if (entity == this.mc.renderViewEntity.ridingEntity)
                                {
                                    if (d2 == 0.0D)
                                    {
                                        this.pointedEntity = entity;
                                        vec33 = movingobjectposition.hitVec;
                                    }
                                }
                                else
                                {
                                    this.pointedEntity = entity;
                                    vec33 = movingobjectposition.hitVec;
                                    d2 = d3;
                                }
                            }
                        }
                    }
                }

                if (this.pointedEntity != null && (d2 < d1 || this.moving== null))
                {
                    this.moving= new MovingObjectPosition(this.pointedEntity, vec33);

                    if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame)
                    {
                        this.mc.pointedEntity = this.pointedEntity;
                    }
                }
            }
        }
    }

}
