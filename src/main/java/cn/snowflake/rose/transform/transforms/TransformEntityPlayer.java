package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.events.impl.EventInsideBlock;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformEntityPlayer implements Opcodes {

    public static void transformEntityPlayer(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("isEntityInsideOpaqueBlock") || methodNode.name.equalsIgnoreCase("func_70094_T")){
            InsnList insnList = new InsnList();
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformEntityPlayer.class), "insideHook", "()Z"));
            LabelNode labelNode = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ, labelNode));
            insnList.add(new InsnNode(ICONST_0));
            insnList.add(new InsnNode(IRETURN));
            insnList.add(labelNode);
            methodNode.instructions.insert(insnList);
        }
    }

    public static boolean insideHook(){
        EventInsideBlock event = new EventInsideBlock();
        EventManager.call(event);
        return event.cancel;
    }
}
