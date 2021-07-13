package cn.snowflake.rose.transform.transforms;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class TransformGuiScreen implements Opcodes {
    public static void transformGuiScreen(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("keyTyped") || methodNode.name.equalsIgnoreCase("func_73869_a")){
//            InsnList list = new InsnList();
//            list.add(new VarInsnNode(ALOAD,1));
//            list.add(new VarInsnNode(ALOAD,2));

        }
    }


}
