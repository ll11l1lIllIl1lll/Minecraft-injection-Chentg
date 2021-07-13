package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.events.impl.EventMessage;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import cpw.mods.fml.common.network.simpleimpl.SimpleChannelHandlerWrapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformSimpleChannelHandlerWrapper implements Opcodes {


    public static void transform(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equals("channelRead0")){
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ALOAD,0));
            insnList.add(new VarInsnNode(ALOAD,1));
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformSimpleChannelHandlerWrapper.class),"onMessageHook","(Ljava/lang/Object;Ljava/lang/Object;)V"));
            methodNode.instructions.insertBefore(methodNode.instructions.getFirst(),insnList);
        }
    }

    public void onMessageHook(Object simpleChannelHandlerWrapper,Object message){
        EventManager.call(new EventMessage(simpleChannelHandlerWrapper,message));
    }

}
