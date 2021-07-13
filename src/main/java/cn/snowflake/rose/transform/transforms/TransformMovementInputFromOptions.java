package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.mod.mods.MOVEMENT.Scaffold;
import cn.snowflake.rose.utils.asm.ASMUtil;
import net.minecraft.injection.ClientLoader;
import org.lwjgl.input.Keyboard;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformMovementInputFromOptions implements Opcodes {


    public static void transformMovementInputFromOptions(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("updatePlayerMoveState") || methodNode.name.equalsIgnoreCase("func_78898_a")){
            AbstractInsnNode target = ASMUtil.findFieldInsnNode(methodNode, GETFIELD, "net/minecraft/util/MovementInputFromOptions", ClientLoader.runtimeObfuscationEnabled ? "field_78899_d" : "sneak", "Z");
            if (target != null) {
                final InsnList insnList = new InsnList();
                insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformMovementInputFromOptions.class), "isDownEnabled", "()Z"));
                LabelNode jmp = new LabelNode();
                insnList.add(new JumpInsnNode(IFEQ,jmp));
                insnList.add(new InsnNode(RETURN));
                insnList.add(jmp);
                insnList.add(new FrameNode(F_SAME, 0, null, 0, null));
                methodNode.instructions.insert(ASMUtil.forward(target,2),insnList);
            }
        }
    }

    public static boolean isDownEnabled() {
        return Scaffold.down.getValueState() &&
                Keyboard.isKeyDown(42) &&
                Scaffold.d;
    }

}
