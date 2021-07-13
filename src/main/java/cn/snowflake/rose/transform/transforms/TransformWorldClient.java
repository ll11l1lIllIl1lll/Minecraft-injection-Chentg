package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.utils.asm.ASMUtil;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import sun.reflect.Reflection;

public class TransformWorldClient implements Opcodes {

    public static void transform(ClassNode classNode, MethodNode methodNode){
        if (methodNode.name.equalsIgnoreCase("func_72882_A") ||
            methodNode.name.equalsIgnoreCase("sendQuittingDisconnectingPacket")
        ){
            InsnList insnList = new InsnList();
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformWorldClient.class),"call","()Z"));
            LabelNode labelNode = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ,labelNode));
            insnList.add(new InsnNode(RETURN));
            insnList.add(labelNode);
            methodNode.instructions.insert( insnList);
        }
    }

    public static boolean call(){
        String caller = Reflection.getCallerClass(3).getName();

        if (caller.contains("luohuayu")){
            return true;
        }
        return false;
    }

}
