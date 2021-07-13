package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.utils.asm.ASMUtil;
import net.minecraft.network.NetHandlerPlayServer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class TransformNetHandlerPlayServer implements Opcodes {

    public static void transformNetHandlerPlayServer(ClassNode classNode, MethodNode methodNode) {
        InsnList nodelist = methodNode.instructions;
        if (methodNode.name.equalsIgnoreCase("func_147360_c") || methodNode.name.equalsIgnoreCase("kickPlayerFromServer")){
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ALOAD,1));
            insnList.add(new LdcInsnNode("Illegal stance"));
            insnList.add(ASMUtil.newInstance(INVOKEVIRTUAL,"java/lang/String", "startsWith", "(Ljava/lang/String;)Z"));
            LabelNode l1 = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ,l1));
            insnList.add(new InsnNode(RETURN));
            insnList.add(l1);
            insnList.add(new FrameNode(F_SAME, 4, null, 0, null));
            methodNode.instructions.insertBefore(nodelist.getFirst().getNext(),insnList);
        }
        if (methodNode.name.equalsIgnoreCase("processPlayer") || methodNode.name.equalsIgnoreCase("func_147347_a")){
            AbstractInsnNode ldc = ASMUtil.findInsnLdc(methodNode," had an illegal stance: ");
            if (ldc != null){
                methodNode.instructions.remove(ASMUtil.forward(ldc,8));
            }
        }
    }

}
