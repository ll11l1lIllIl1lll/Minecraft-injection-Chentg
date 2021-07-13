package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.events.impl.EventPlayerDamageBlock;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class TransformPlayerControllerMP implements Opcodes {

    public static void transformPlayerControllerMP(ClassNode clazz, MethodNode method) {
        if (method.name.equals("onPlayerDamageBlock") || method.name.equals("func_78759_c")) {
            method.instructions.insert(method.instructions.getFirst(), ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformPlayerControllerMP.class), "PlayerDamageBlock", "()V"));
        }
    }

    public static void PlayerDamageBlock(){
        EventManager.call(new EventPlayerDamageBlock());
    }


}
