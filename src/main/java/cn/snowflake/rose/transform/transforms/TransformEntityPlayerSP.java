package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.events.impl.EventPushOut;
import cn.snowflake.rose.events.impl.EventStep;
import cn.snowflake.rose.mod.mods.PLAYER.NoSlowDown;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.types.EventType;
import net.minecraft.client.Minecraft;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

public class TransformEntityPlayerSP implements Opcodes {

    public static void transformEntityPlayerSP(ClassNode clazz, MethodNode method) {
        if (method.name.equalsIgnoreCase("func_70636_d") || method.name.equalsIgnoreCase("onLivingUpdate")) {
            Iterator<AbstractInsnNode> iter = method.instructions.iterator();
            while (iter.hasNext()) {
                AbstractInsnNode insn = iter.next();
                if (insn.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                    MethodInsnNode methodInsn = (MethodInsnNode) insn;
                    if (methodInsn.name.equals("updatePlayerMoveState") || methodInsn.name.equals("func_78898_a")) {
                        method.instructions.insert(insn, ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformEntityPlayerSP.class), "onNoSlowEnable", "()V"));
                    }
                    if (methodInsn.name.equals("func_145771_j")) {
                        method.instructions.insertBefore(insn, ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformEntityPlayerSP.class), "onToggledTimerZero", "()V"));
                    }
                }
            }
        }
        if (method.name.equalsIgnoreCase("isSneaking") || method.name.equalsIgnoreCase("func_70093_af")) {
            final InsnList insnList = new InsnList();
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformMovementInputFromOptions.class), "isDownEnabled", "()Z"));
            LabelNode jmp = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ,jmp));
            insnList.add(new InsnNode(ICONST_0));
            insnList.add(new InsnNode(IRETURN));
            insnList.add(jmp);
            insnList.add(jmp);
            insnList.add(new FrameNode(F_SAME, 0, null, 0, null));
            method.instructions.insert(insnList);
        }
        if (method.name.equalsIgnoreCase("func_145771_j")){
            final InsnList insnList = new InsnList();
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformEntityPlayerSP.class), "pushOutOfBlocksHooks","()Z"));
            final LabelNode jmp = new LabelNode();
            insnList.add(new JumpInsnNode(Opcodes.IFEQ, jmp));
            insnList.add(new InsnNode(ICONST_0));
            insnList.add(new InsnNode(IRETURN));
            insnList.add(jmp);
            insnList.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
            method.instructions.insert(insnList);
        }
    }

    public static boolean pushOutOfBlocksHooks(){
        EventPushOut event = new EventPushOut();
        EventManager.call(event);
        return !event.cancel;
    }

    private static float cacheStrafe;
    private static float cacheForward;

    public static boolean onNoSlowEnable2() {
        return NoSlowDown.no;
    }

    public static boolean isSlow() {
        return Minecraft.getMinecraft().thePlayer.isUsingItem() && !Minecraft.getMinecraft().thePlayer.isRiding();
    }

    public static void eventStepHook1(float stepheight){
        EventManager.call(new EventStep(EventType.PRE,stepheight));
    }
    public static void eventStepHook2(float stepheight){
        EventManager.call(new EventStep(EventType.POST,stepheight));
    }

    public static void onNoSlowEnable() {
        if (!isSlow()) {
            return;
        }
        if (NoSlowDown.no) {
            cacheStrafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
            cacheForward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        }
    }
    public static void onToggledTimerZero(){
        if (!isSlow()) {
            return;
        }
        if (NoSlowDown.no) {
            Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe = cacheStrafe;
            Minecraft.getMinecraft().thePlayer.movementInput.moveForward = cacheForward;
        }
    }

}
