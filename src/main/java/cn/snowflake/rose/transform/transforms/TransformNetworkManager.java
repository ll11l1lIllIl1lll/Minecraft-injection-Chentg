package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventPacket;
import cn.snowflake.rose.mod.mods.MOVEMENT.Freecam;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformNetworkManager implements Opcodes {

    public static void transformNetworkManager(ClassNode classNode, MethodNode methodNode) {

        if (methodNode.name.equalsIgnoreCase("channelRead0") && methodNode.desc.equalsIgnoreCase("(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V")){
            final InsnList preInsn = new InsnList();
            preInsn.add(new VarInsnNode(Opcodes.ALOAD, 2));
            preInsn.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/darkmagician6/eventapi/types/EventType", "RECIEVE", "Lcom/darkmagician6/eventapi/types/EventType;"));
            preInsn.add(ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformNetworkManager.class), "channelRead0Hook","(Ljava/lang/Object;Lcom/darkmagician6/eventapi/types/EventType;)Z"));
            final LabelNode jmp = new LabelNode();
            preInsn.add(new JumpInsnNode(Opcodes.IFEQ, jmp));
            preInsn.add(new InsnNode(Opcodes.RETURN));
            preInsn.add(jmp);
            preInsn.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            methodNode.instructions.insert(preInsn);
            /**
             * if(channelRead0Hook(packet,EventType.RECIEVE)){
             *  return ;
             * }
             */
        }
    }



    public static boolean channelRead0Hook(Object packet, EventType eventType) {
        if(packet != null) {
            if (packet instanceof S05PacketSpawnPosition){
                Freecam.canCancle = false;
            }
            final EventPacket event = new EventPacket(eventType,packet);
            EventManager.call(event);
            if (event.getPacket() instanceof S08PacketPlayerPosLook){
                Freecam.canCancle = true;
            }
            return event.isCancelled();
        }
        return false;
    }

}
