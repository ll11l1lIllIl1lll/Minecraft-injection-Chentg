package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.mod.mods.RENDER.Chams;
import cn.snowflake.rose.utils.asm.ASMUtil;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformRenderPlayer implements Opcodes {

    public static void transformRenderPlayer(ClassNode classNode, MethodNode method) {
        if (method.name.equalsIgnoreCase("doRender") || method.name.equalsIgnoreCase("func_76986_a")){
            InsnList insnList1 = new InsnList();
            InsnList insnList2 = new InsnList();
            insnList1.add(new VarInsnNode(ALOAD,1));
            insnList1.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformRenderPlayer.class), "chamsHook1", "(Ljava/lang/Object;)V"));
            method.instructions.insert(insnList1);

            insnList2.add(new VarInsnNode(ALOAD,1));
            insnList2.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformRenderPlayer.class), "chamsHook2", "(Ljava/lang/Object;)V"));
            method.instructions.insertBefore(ASMUtil.bottom(method),insnList2);
        }
    }
    public static void chamsHook1(Object object){
        if (Chams.chams && object instanceof EntityPlayer){
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -2000000F);
        }
    }
    public static void chamsHook2(Object object){
        if (Chams.chams && object instanceof EntityPlayer){
            GL11.glPolygonOffset(1.0F, 2000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }
}
