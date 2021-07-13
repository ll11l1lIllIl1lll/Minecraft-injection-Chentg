package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.mod.mods.WORLD.Xray;
import cn.snowflake.rose.utils.asm.ASMUtil;
import net.minecraft.block.Block;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformBlock implements Opcodes {
    public static boolean isXrayContains(Block blcok) {
        return  Xray.containsID(blcok);
    }
    public static boolean isXrayCaveEnabled() {
//        return  isXrayEnabled() & !Xray.cave.getValueState();
        return false;
    }
    public static boolean isXrayEnabled() {
        return Xray.x;
    }


    public static void transformBlock(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("shouldSideBeRendered") || methodNode.name.equalsIgnoreCase("func_149646_a")){
            final InsnList insnList = new InsnList();
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformBlock.class), "isXrayEnabled", "()Z"));
            LabelNode jmp = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ,jmp));
            insnList.add(new FieldInsnNode(GETSTATIC, Type.getInternalName(Xray.class), "block", "Ljava/util/ArrayList;"));
            insnList.add(new VarInsnNode(ALOAD,0));// == this
            insnList.add(ASMUtil.newInstance(INVOKEVIRTUAL, "java/util/ArrayList", "contains", "(Ljava/lang/Object;)Z"));
            insnList.add(new InsnNode(IRETURN));
            insnList.add(jmp);
            insnList.add(new FrameNode(F_SAME, 0, null, 0, null));
            methodNode.instructions.insert(insnList);
        }
//		if (methodNode.name.equalsIgnoreCase("getRenderBlockPass") || methodNode.name.equalsIgnoreCase("func_149701_w")){
//			final InsnList insnList = new InsnList();
//			insnList.add(new Mhttp://marketplace.eclipse.org/marketplace-client-intro?mpc_install=124ethodInsnNode(INVOKESTATIC, Type.getInternalName(TransformBlock.class), "isXrayEnabled", "()Z", false));
//			LabelNode jmp1 = new LabelNode();
//			insnList.add(new JumpInsnNode(IFNE,jmp1));
//			LabelNode jmp2 = new LabelNode();
//			insnList.add(new VarInsnNode(ALOAD,0));// == this
//			insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformBlock.class), "getRenderBlockPass", "(Lnet/minecraft/block/Block;)Z", false));
//			insnList.add(new JumpInsnNode(IFEQ,jmp2));
//			insnList.add(new InsnNode(ICONST_1));
//			insnList.add(new InsnNode(IRETURN));
//			insnList.add(jmp1);
//			insnList.add(jmp2);
//
//			methodNode.instructions.insert(insnList);
////			if (MinecraftHook.isXrayEnabled()){
////				if (Xray.block.contains(this)){
////					return 1;
////				}
////				return 0;
////			}
//
//		}
    }
    public static boolean getRenderBlockPass(Block block){
        return Xray.containsID(block);
    }

}
