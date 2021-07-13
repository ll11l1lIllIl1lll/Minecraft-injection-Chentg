package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.management.ModManager;
import cn.snowflake.rose.mod.mods.RENDER.NoHurtcam;
import cn.snowflake.rose.mod.mods.RENDER.ViewClip;
import cn.snowflake.rose.utils.asm.ASMUtil;
import net.minecraft.injection.ClientLoader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Objects;

public class TransformEntityRenderer implements Opcodes {

    public static boolean isNohurtcamEnable(){
        return NoHurtcam.no;
    }

    public static boolean isViewClipEnabled() {
        return Objects.requireNonNull(ModManager.getModByClass(ViewClip.class)).isEnabled();
    }

    public static void transformRenderEntityRenderer(ClassNode classNode, MethodNode method) {
        if (method.name.equalsIgnoreCase("hurtCameraEffect") || method.name.equalsIgnoreCase("func_78482_e")){
            InsnList insnList = new InsnList();
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformEntityRenderer.class), "isNohurtcamEnable", "()Z"));
            LabelNode labelNode = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ, labelNode));
            insnList.add(new InsnNode(RETURN));
            insnList.add(labelNode);
            method.instructions.insert(insnList);
        }
        if ((method.name.equalsIgnoreCase("orientCamera") || method.name.equalsIgnoreCase("func_78467_g")) && method.desc.equalsIgnoreCase("(F)V")){
            AbstractInsnNode target = ASMUtil.findMethodInsn(method, INVOKEVIRTUAL,"net/minecraft/util/Vec3", ClientLoader.runtimeObfuscationEnabled ?"func_72438_d" : "distanceTo","(Lnet/minecraft/util/Vec3;)D");
            if (target != null){
                InsnList insnList2 = new InsnList();

                InsnList insnList = new InsnList();
                insnList.add(ASMUtil.newInstance(INVOKESTATIC,Type.getInternalName(TransformEntityRenderer.class),"isViewClipEnabled","()Z"));
                LabelNode labelNode = new LabelNode();
                insnList.add(new JumpInsnNode(IFNE,labelNode));
                method.instructions.insertBefore(ASMUtil.forward(target,8),insnList);
                insnList2.add(labelNode);
                insnList2.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
                method.instructions.insert(ASMUtil.forward(target,13),insnList2);
                //   dump net.minecraft.client.renderer.EntityRenderer
                // jad net.minecraft.client.renderer.EntityRenderer
            }
        }
    }
}
