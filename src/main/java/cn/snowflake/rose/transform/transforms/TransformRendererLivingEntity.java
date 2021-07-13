package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.events.impl.EventRenderPlayer;
import cn.snowflake.rose.utils.other.JReflectUtility;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class TransformRendererLivingEntity implements Opcodes {

	public static void transformRendererLivingEntity(ClassNode classNode, MethodNode method) {
//		if (method.name.equalsIgnoreCase("doRender") || method.name.equalsIgnoreCase("func_76986_a")){
//			AbstractInsnNode pre = ASMUtil.findFieldInsnNode(
//					method,
//					INVOKESTATIC,
//					Type.getInternalName(RendererLivingEntity.class),
//					ClientLoader.runtimeObfuscationEnabled ? "func_77041_b" : "preRenderCallback","(Lnet/minecraft/entity/EntityLivingBase;F)V");
//			if (pre != null){
//				InsnList insnList = new InsnList();
//				LabelNode labelNode = new LabelNode();
//				insnList.add(new VarInsnNode(ALOAD,1));
//				insnList.add(ASMUtil.newInstance(INVOKESTATIC,
//						Type.getInternalName(TransformRendererLivingEntity.class),
//						"eventrenderplayer",
//						"(Ljava/lang/Object;)Z",
//						false
//				));
//				insnList.add(new JumpInsnNode(IFEQ,labelNode));
//				insnList.add(new InsnNode(RETURN));
//				insnList.add(labelNode);
//				method.instructions.insert(pre,insnList);
//			}
//		}
	}


    public static boolean eventrenderplayer(Object object){
        if (object instanceof EntityPlayerSP){
			EntityLivingBase entityLivingBase = (EntityLivingBase) object;
			float limbSwing = entityLivingBase.limbSwing - entityLivingBase.limbSwingAmount * (1.0F - JReflectUtility.getRenderPartialTicks());
			float limbSwingAmount = entityLivingBase.prevLimbSwingAmount + (entityLivingBase.limbSwingAmount - entityLivingBase.prevLimbSwingAmount) * JReflectUtility.getRenderPartialTicks();
			float ageInTicks = entityLivingBase.ticksExisted + JReflectUtility.getRenderPartialTicks();
			
			float f2 = interpolateRotation(entityLivingBase.prevRenderYawOffset, entityLivingBase.renderYawOffset, JReflectUtility.getRenderPartialTicks());
			float f3 = interpolateRotation(entityLivingBase.prevRotationYawHead, entityLivingBase.rotationYawHead, JReflectUtility.getRenderPartialTicks());
			float rotationYawHead = f3 - f2;
			float f4;

			if (entityLivingBase.isRiding() && entityLivingBase.ridingEntity instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase1 = (EntityLivingBase)entityLivingBase.ridingEntity;
				f2 = interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, JReflectUtility.getRenderPartialTicks());
				rotationYawHead = f3 - f2;
				f4 = MathHelper.wrapAngleTo180_float(rotationYawHead);
				if (f4 < -85.0F) {
					f4 = -85.0F;
				}

				if (f4 >= 85.0F) {
					f4 = 85.0F;
				}

				f2 = f3 - f4;
				if (f4 * f4 > 2500.0F) {
					f2 += f4 * 0.2F;
				}
			}
			float rotationPitch = entityLivingBase.prevRotationPitch + (entityLivingBase.rotationPitch - entityLivingBase.prevRotationPitch) * JReflectUtility.getRenderPartialTicks();

			EventRenderPlayer eventRenderPlayer = new EventRenderPlayer(entityLivingBase,
					true,
					limbSwing,
					limbSwingAmount,
					ageInTicks,
					rotationYawHead,
					rotationPitch,
					f2,
					0.0625F
			);
            return eventRenderPlayer.cancelled;
        }
        return false;
    }

	private static float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_) {
		float f3;
		for(f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F) {
		}

		while(f3 >= 180.0F) {
			f3 -= 360.0F;
		}

		return p_77034_1_ + p_77034_3_ * f3;
	}
}
