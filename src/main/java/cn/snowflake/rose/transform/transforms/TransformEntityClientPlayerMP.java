package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.command.Command;
import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.events.impl.EventUpdate;
import cn.snowflake.rose.management.CommandManager;
import cn.snowflake.rose.mod.mods.WORLD.NoCommand;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.client.Minecraft;
import net.minecraft.injection.ClientLoader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformEntityClientPlayerMP implements Opcodes {


    public static void transformEntityClientPlayerMP(ClassNode clazz, MethodNode method) {
        if (method.name.equals("onUpdate") || method.name.equals("func_70071_h_") ) {
            method.instructions.insert(ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformEntityClientPlayerMP.class), "onUpdate", "()V"));
        }
        if (method.name.equalsIgnoreCase("sendMotionUpdates") || method.name.equalsIgnoreCase("func_71166_b")){
            //replace the shit of old

            InsnList preInsn = new InsnList();
            preInsn.add(new FieldInsnNode(GETSTATIC, Type.getInternalName(EventType.class), "PRE", "Lcom/darkmagician6/eventapi/types/EventType;"));
            preInsn.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformEntityClientPlayerMP.class), "onUpdateWalkingPlayerHook","(Lcom/darkmagician6/eventapi/types/EventType;)V"));
            method.instructions.insert(preInsn);

            InsnList postInsn = new InsnList();
            postInsn.add(new FieldInsnNode(GETSTATIC, Type.getInternalName(EventType.class), "POST", "Lcom/darkmagician6/eventapi/types/EventType;"));
            postInsn.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformEntityClientPlayerMP.class), "onUpdateWalkingPlayerHook","(Lcom/darkmagician6/eventapi/types/EventType;)V"));
            method.instructions.insertBefore(ASMUtil.bottom(method), postInsn);



            for (AbstractInsnNode abstractInsnNode : method.instructions.toArray()){
                if (abstractInsnNode.getOpcode() == ALOAD &
                        abstractInsnNode.getNext() instanceof FieldInsnNode
                ){
                    if ( ((FieldInsnNode) abstractInsnNode.getNext()).name.equalsIgnoreCase(ClientLoader.runtimeObfuscationEnabled ? "field_70163_u" : "posY")
                    ){
                        method.instructions.set(abstractInsnNode.getNext(),
                                new FieldInsnNode(GETSTATIC,"cn/snowflake/rose/events/impl/EventMotion",
                                        "y","D"));
                        method.instructions.remove(abstractInsnNode);
                    }else if ( ((FieldInsnNode) abstractInsnNode.getNext()).name.equalsIgnoreCase(ClientLoader.runtimeObfuscationEnabled ? "field_70177_z" : "rotationYaw")
                    ){
                        method.instructions.set(abstractInsnNode.getNext(),
                                new FieldInsnNode(GETSTATIC,"cn/snowflake/rose/events/impl/EventMotion",
                                        "yaw","F"));
                        method.instructions.remove(abstractInsnNode);
                    }else if ( ((FieldInsnNode) abstractInsnNode.getNext()).name.equalsIgnoreCase(ClientLoader.runtimeObfuscationEnabled ? "field_70125_A" : "rotationPitch")
                    ){
                        method.instructions.set(abstractInsnNode.getNext(),
                                new FieldInsnNode(GETSTATIC,"cn/snowflake/rose/events/impl/EventMotion",
                                        "pitch","F"));
                        method.instructions.remove(abstractInsnNode);
                    }else if ( ((FieldInsnNode) abstractInsnNode.getNext()).name.equalsIgnoreCase(ClientLoader.runtimeObfuscationEnabled ? "field_70122_E" : "onGround")
                    ){
                        method.instructions.set(abstractInsnNode.getNext(),
                                new FieldInsnNode(GETSTATIC,"cn/snowflake/rose/events/impl/EventMotion",
                                        "onGround","Z"));
                        method.instructions.remove(abstractInsnNode);
                    }
                }
            }

        }
        if (method.name.equalsIgnoreCase("func_71165_d") || method.name.equalsIgnoreCase("sendChatMessage")) {

            final InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
            insnList.add(ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformEntityClientPlayerMP.class), "sendMessageHook", "(Ljava/lang/String;)V"));

            insnList.add(new VarInsnNode(Opcodes.ALOAD, 1));
            insnList.add(new LdcInsnNode("-"));
            insnList.add(ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformEntityClientPlayerMP.class), "isNoCommandEnabled", "(Ljava/lang/String;Ljava/lang/String;)Z"));

            final LabelNode jmp = new LabelNode();
            insnList.add(new JumpInsnNode(Opcodes.IFEQ, jmp));
            insnList.add(new InsnNode(Opcodes.RETURN));
            insnList.add(jmp);
            insnList.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            method.instructions.insert(insnList);
        }
    }

    public static void onUpdateWalkingPlayerHook(EventType stage) {
        if (stage == EventType.PRE){
            EventMotion em = new EventMotion(Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.rotationYaw,Minecraft.getMinecraft().thePlayer.rotationPitch,Minecraft.getMinecraft().thePlayer.onGround);
            EventManager.call(em);
        }else if (stage == EventType.POST){
            EventMotion ep = new EventMotion(stage,Minecraft.getMinecraft().thePlayer.rotationPitch);
            EventManager.call(ep);
        }
    }

    public static void sendMessageHook(String message){
        String s = CommandManager.removeSpaces(message);
        if (message.startsWith("-") && !NoCommand.n) {
            for (Command cmd : CommandManager.getCommands()) {
                int i = 0;
                while (i < cmd.getCommands().length) {
                    //TODO 符号
                    if (s.split(" ")[0].equals("-" + cmd.getCommands()[i])) {
                        cmd.onCmd(s.split(" "));
                        return;
                    }
                    ++i;
                }
            }
            return;
        }
    }
    public static boolean isNoCommandEnabled(String s,String s1) {
        return s.startsWith(s1) && !NoCommand.n;
    }

    public static void onUpdate(){
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
            EventUpdate event = new EventUpdate();
            EventManager.call(event);
        }
    }
}
