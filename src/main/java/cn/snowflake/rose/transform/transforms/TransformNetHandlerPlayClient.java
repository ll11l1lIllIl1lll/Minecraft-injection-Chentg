package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.events.impl.EventhandleWindowItem;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformNetHandlerPlayClient implements Opcodes {


    public static void transform(ClassNode classNode, MethodNode methodNode){
        if (methodNode.name.equals("addToSendQueue") || methodNode.name.equals("func_147297_a")){
            final InsnList preInsn = new InsnList();
            preInsn.add(new VarInsnNode(Opcodes.ALOAD, 1));//方法的第一个参数

            preInsn.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/darkmagician6/eventapi/types/EventType", "SEND", "Lcom/darkmagician6/eventapi/types/EventType;"));
            preInsn.add(ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformNetHandlerPlayClient.class), "addToSendQueueHook","(Ljava/lang/Object;Lcom/darkmagician6/eventapi/types/EventType;)Z"));
            final LabelNode jmp = new LabelNode();
            preInsn.add(new JumpInsnNode(Opcodes.IFEQ, jmp));
            preInsn.add(new InsnNode(Opcodes.RETURN));
            preInsn.add(jmp);
            preInsn.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            methodNode.instructions.insert(preInsn);
        }
        if (methodNode.name.equals("func_147241_a") || methodNode.name.equals("handleWindowItems")){
            InsnList preInsn = new InsnList();
            preInsn.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformNetHandlerPlayClient.class), "handleWindowItemsHook","()Z"));
            LabelNode labelNode = new LabelNode();
            preInsn.add(new JumpInsnNode(IFEQ,labelNode));
            preInsn.add(new InsnNode(RETURN));
            preInsn.add(labelNode);
            preInsn.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), preInsn);
        }
    }


    public static boolean handleWindowItemsHook() {
        EventhandleWindowItem eventhandleWindowItem = new EventhandleWindowItem();
        return eventhandleWindowItem.isCancelled();
    }

    public static boolean addToSendQueueHook(Object packet, EventType eventType) {
        if(packet != null) {
            EventPacket event = new EventPacket(eventType,packet);
            EventManager.call(event);
            return event.isCancelled();
        }
        return false;
    }

}
