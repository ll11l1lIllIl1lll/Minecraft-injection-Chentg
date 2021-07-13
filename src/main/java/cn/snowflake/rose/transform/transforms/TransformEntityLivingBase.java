package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.events.impl.EventLivingUpdate;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformEntityLivingBase implements Opcodes {


    public static void transformEntityLivingBase(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("onEntityUpdate") || methodNode.name.equalsIgnoreCase("func_70030_z")){
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ALOAD,0));
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformEntityLivingBase.class),"onEntityUpdateHook","(Ljava/lang/Object;)V"));
        }
    }

    public static void onEntityUpdateHook(Object object){
        EventLivingUpdate eventLivingUpdate = new EventLivingUpdate((Entity) object);
        EventManager.call(eventLivingUpdate);
    }

}
