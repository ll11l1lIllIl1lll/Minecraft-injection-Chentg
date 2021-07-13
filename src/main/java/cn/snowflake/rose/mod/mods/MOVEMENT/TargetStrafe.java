package cn.snowflake.rose.mod.mods.MOVEMENT;

import cn.snowflake.rose.events.impl.EventMove;
import cn.snowflake.rose.events.impl.EventRender3D;
import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.mod.mods.COMBAT.Aura;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.client.PlayerUtil;
import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.mcutil.GlStateManager;
import cn.snowflake.rose.utils.other.JReflectUtility;
import cn.snowflake.rose.utils.render.RenderUtil;
import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.Priority;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.glu.Cylinder;

public class TargetStrafe extends Module {

	public static Value<Double> dist = new Value<Double>("TargetStrafe_Distance", 3.0d, 1.0d, 5.0d, 0.05d);
	public static Value<Double> spaceRange = new Value<Double>("TargetStrafe_Hit Reach", 4.5d, 1.0d, 7.0d, 0.05d);
	public static Value<Double> Sides = new Value<Double>("TargetStrafe_Sides", 6.0d, 3.0d, 25.0d, 1.0d);
	public Value<Boolean> onlyInRange = new Value("TargetStrafe_Only Hit Reach", true);
	public Value<Boolean> check = new Value("TargetStrafe_Wall&void Check", true);
	public Value<Boolean> esp = new Value("TargetStrafe_Draw", true);
	public EntityLivingBase target;

	private double degree = 0.0D;

	private float groundY;

	private boolean left = true;
	
    public TargetStrafe() {
        super("TargetStrafe", "Target Strafe", Category.MOVEMENT);
        setChinesename("\u7ed5\u76ee\u6807\u8f6c\u5708");
    }

	@Override
	public String getDescription() {
		return "绕目标转圈(开启杀戮和加速同时按住空格)!";
	}

	@EventTarget
	public void onRender(EventRender3D event) {
		if (Aura.target != null && esp.getValueState()) {
			final double x = Aura.target.lastTickPosX
					+ (Aura.target.posX - Aura.target.lastTickPosX) * JReflectUtility.getRenderPartialTicks()
	                - RenderManager.renderPosX;
			final double y = Aura.target.lastTickPosY
					+ (Aura.target.posY - Aura.target.lastTickPosY) * JReflectUtility.getRenderPartialTicks()
	                - RenderManager.renderPosY;
			final double z = Aura.target.lastTickPosZ
					+ (Aura.target.posZ - Aura.target.lastTickPosZ) * JReflectUtility.getRenderPartialTicks()
	                - RenderManager.renderPosZ;
			Color color = mc.gameSettings.keyBindJump.getIsKeyPressed() ? new Color(0, 150, 255) : new Color(255, 255, 255);
				RenderUtil.pre3D();
			   	GL11.glTranslated((double)x, (double)y, (double)z);
	        	GL11.glRotatef((float)(-Aura.target.width), (float)0.0f, (float)1.0f, (float)0.0f);
		        Cylinder c = new Cylinder();
		        GlStateManager.rotate((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
		        c.setDrawStyle(100011);
		        GL11.glColor4f(0,0,0,1);
		        GL11.glLineWidth((float)3);
		        c.draw(dist.getValueState().floatValue(), dist.getValueState().floatValue(), 0.0f, Sides.getValueState().intValue()==25 ? 360: Sides.getValueState().intValue(), 0);
		        
		    	GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255,1);
		        GL11.glLineWidth((float)2);
		        c.draw(dist.getValueState().floatValue(), dist.getValueState().floatValue(), 0.0f, Sides.getValueState().intValue()==25 ? 360: Sides.getValueState().intValue(), 0);
		        RenderUtil.post3D();
		}
	}
	
	@EventTarget(Priority.LOW)
	private void onMove(EventMove e) {
	    if (e.getEntity() == mc.thePlayer){
	    	
		Speed speedM = (Speed) ModManager.getModByClass(Speed.class);
		if (Aura.target != null) {
			this.target = Aura.target;
		} else {
			this.target = null;
		}
		if(mc.gameSettings.keyBindJump.getIsKeyPressed() && PlayerUtil.Moving() && speedM.isEnabled()) {
//			  JReflectUtility.setjumpticks(4);;
		}
		
		if (canStrafe() && mc.gameSettings.keyBindJump.getIsKeyPressed()) {
			double speed = speedM.speed;
			this.degree = Math.atan2(this.mc.thePlayer.posZ - this.target.posZ,
					this.mc.thePlayer.posX - this.target.posX);
			this.degree += !this.left ? (speed / getDistanceXZToEntity(mc.thePlayer,this.target))
					: -(speed / getDistanceXZToEntity(mc.thePlayer,this.target));
			double x = this.target.posX + ((Double) this.dist.getValueState()).doubleValue() * Math.cos(this.degree);
			double z = this.target.posZ + ((Double) this.dist.getValueState()).doubleValue() * Math.sin(this.degree);
			if ((this.check.getValueState()).booleanValue() && needToChange(x, z)) {
				this.left = !this.left;
				this.degree += 2.0D * (this.left ? (speed / getDistanceXZToEntity(mc.thePlayer,this.target))
						: -(speed / getDistanceXZToEntity(mc.thePlayer,this.target)));
				x = this.target.posX + ((Double) this.dist.getValueState()).doubleValue() * Math.cos(this.degree);
				z = this.target.posZ + ((Double) this.dist.getValueState()).doubleValue() * Math.sin(this.degree);
			}
			e.setX(speed * -Math.sin((float) Math.toRadians(toDegree(x, z))));
			e.setZ(speed * Math.cos((float) Math.toRadians(toDegree(x, z))));
			}
	    }
	    
    }
	
	public boolean canStrafe() {
		Aura ka = (Aura) ModManager.getModByClass(Aura.class);
		Speed speed = (Speed) ModManager.getModByClass(Speed.class);
		return (this.target != null && PlayerUtil.Moving() && ka.isEnabled()
				&& (speed.isEnabled())
				&& (!((Boolean) this.onlyInRange.getValueState()).booleanValue() || getDistanceXZToEntity(this.target,(Entity) this.mc.thePlayer) < ((Double) this.spaceRange.getValueState()).doubleValue()));
	}

    public float getDistanceXZToEntity(Entity entity ,Entity toentity) {
        float f = (float)(entity.posX - toentity.posX);
        float f2 = (float)(entity.posZ - toentity.posZ);
        return MathHelper.sqrt_float(f * f + f2 * f2);
    }
    
	public boolean needToChange(double x, double z) {
		if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.ticksExisted % 2 == 0)
			return true;
		for (int i = (int) (mc.thePlayer.posY + 4.0D); i >= 0; i--) {
			BlockPos playerPos = new BlockPos(x, i, z);
			if (mc.theWorld.getBlock(playerPos.x,playerPos.y,playerPos.z).equals(Blocks.lava)
			|| mc.theWorld.getBlock(playerPos.x,playerPos.y,playerPos.z).equals(Blocks.fire)) {
				return true;
			}
			if (!mc.theWorld.isAirBlock(playerPos.x,playerPos.y,playerPos.z))
				return false;
		}
		return true;
	}
    
	
    public static float toDegree(double x, double z) {
        return (float)(Math.atan2(z - (Minecraft.getMinecraft()).thePlayer.posZ, x - (Minecraft.getMinecraft()).thePlayer.posX) * 180.0D / Math.PI) - 90.0F;
      }
    
	public void onEnable() {
		this.degree = 0.0D;
		this.left = true;
		this.target = null; 
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

}