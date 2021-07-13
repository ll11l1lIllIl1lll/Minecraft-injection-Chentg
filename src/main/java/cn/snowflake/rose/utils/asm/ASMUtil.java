package cn.snowflake.rose.utils.asm;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;

/**
 * Author Seth
 * 4/4/2019 @ 11:22 PM.
 */
public final class ASMUtil {

    public static MethodInsnNode newInstance(final int opcode, final String owner,final String name, final String desc){
        try{
            Opcodes.class.getField("ASM5");
            return new MethodInsnNode(  opcode,   owner,
              name,   desc,   false);
        }catch (Exception e){
            return new MethodInsnNode(  opcode,   owner,
                    name,   desc);
        }
    }


    public static AbstractInsnNode findPattern(AbstractInsnNode start, int[] pattern, char[] mask) {
        if (pattern.length != mask.length) {
            throw new IllegalArgumentException("Mask must be same length as pattern");
        }
        return findPattern(
                start,
                pattern.length,
                (node) -> true,
                (found, next) -> mask[found] != 'x' || next.getOpcode() == pattern[found],
                (first, last) -> first);
    }

    public static <T> T findPattern(
            final AbstractInsnNode start,
            final int patternSize,
            Predicate<AbstractInsnNode>
                    isValidNode, // if this returns false then dont invoke the predicate and dont update found
            BiPredicate<Integer, AbstractInsnNode> nodePredicate,
            BiFunction<AbstractInsnNode, AbstractInsnNode, T> outputFunction) {
        if (start != null) {
            int found = 0;
            AbstractInsnNode next = start;
            do {
                // Check if node matches the predicate.
                // If the node is not considered a "valid" node then we'll consider it to match the pattern
                // but the predicate will not be invoked for it and found will not be incremented
                final boolean validNode = isValidNode.test(next);
                if (!validNode || nodePredicate.test(found, next)) {
                    if (validNode) {
                        // Increment number of matched opcodes
                        found++;
                    }
                } else {
                    // Go back to the starting node
                    for (int i = 1; i <= (found - 1); i++) {
                        next = next.getPrevious();
                    }
                    // Reset the number of insns matched
                    found = 0;
                }

                // Check if found entire pattern
                if (found >= patternSize) {
                    final AbstractInsnNode end = next;
                    // Go back to top node
                    for (int i = 1; i <= (found - 1); i++) {
                        next = next.getPrevious();
                    }
                    return outputFunction.apply(next, end);
                }
                next = next.getNext();
            } while (next != null);
        }
        // failed to find pattern
        return null;
    }

    public static AbstractInsnNode findPattern(AbstractInsnNode start, int[] pattern, String mask) {
        return findPattern(start, pattern, mask.toCharArray());
    }

    public static AbstractInsnNode findPattern(AbstractInsnNode start, int... opcodes) {
        StringBuilder mask = new StringBuilder();
        for (int op : opcodes) {
            mask.append(op == MagicOpcodes.NONE ? '?' : 'x');
        }
        return findPattern(start, opcodes, mask.toString());
    }

    public static AbstractInsnNode findPattern(InsnList instructions, int... opcodes) {
        return findPattern(instructions.getFirst(), opcodes);
    }

    public static AbstractInsnNode findPattern(MethodNode node, int... opcodes) {
        return findPattern(node.instructions, opcodes);
    }

    @Nullable
    public static AbstractInsnNode forward(AbstractInsnNode start, int n) {
        AbstractInsnNode node = start;
        for (int i = 0;
             i < Math.abs(n) && node != null;
             ++i, node = n > 0 ? node.getNext() : node.getPrevious()) {
        }
        return node;
    }

    public static String getClassData(ClassNode node) {
        StringBuilder builder = new StringBuilder("METHODS:\n");
        for (MethodNode method : node.methods) {
            builder.append("\t");
            builder.append(method.name);
            builder.append(method.desc);
            builder.append("\n");
        }
        builder.append("\nFIELDS:\n");
        for (FieldNode field : node.fields) {
            builder.append("\t");
            builder.append(field.desc);
            builder.append(" ");
            builder.append(field.name);
            builder.append("\n");
        }
        return builder.toString();
    }


    public static int addNewLocalVariable(
            MethodNode method, String name, String desc, LabelNode start, LabelNode end) {
        Optional<LocalVariableNode> lastVar =
                method.localVariables.stream().max(Comparator.comparingInt(var -> var.index));
        final int newIndex =
                lastVar.map(var -> var.desc.matches("[JD]") ? var.index + 2 : var.index + 1).orElse(0);

        LocalVariableNode variable = new LocalVariableNode(name, desc, null, start, end, newIndex);
        method.localVariables.add(variable);

        return newIndex;
    }

    // args should be type descriptors
    public static InsnList newInstance(String name, String[] argTypes, @Nullable InsnList args) {
        final String desc = Stream.of(argTypes).collect(Collectors.joining("", "(", ")V"));
        return newInstance(name, desc, args);
    }

    public static InsnList newInstance(String name, String desc, @Nullable InsnList args) {
        InsnList list = new InsnList();
        list.add(new TypeInsnNode(Opcodes.NEW, name));
        list.add(new InsnNode(Opcodes.DUP));
        if (args != null) {
            list.add(args);
        }
        list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, name, "<init>", desc, false));
        return list;
    }

    public interface MagicOpcodes {

        int NONE = -666;
    }

    public static MethodNode findMethod(ClassNode classNode, String name, String desc) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(name) && methodNode.desc.equals(desc)) {
                return methodNode;
            }
        }
        return null;
    }

    public static AbstractInsnNode findMethodInsn(MethodNode mn, int opcode, String owner, String name, String desc) {
        for(AbstractInsnNode insn : mn.instructions.toArray()) {
            if(insn instanceof MethodInsnNode) {
                final MethodInsnNode method = (MethodInsnNode)insn;
                if(method.getOpcode() == opcode && method.owner.equals(owner) && method.name.equals(name) && method.desc.equals(desc)) {
                    return insn;
                }
            }
        }
        return null;
    }

    public static AbstractInsnNode findFieldInsnNode(MethodNode mn, int opcode, String owner, String name, String desc) {
        for(AbstractInsnNode insn : mn.instructions.toArray()) {
            if(insn instanceof FieldInsnNode) {
                final FieldInsnNode field = (FieldInsnNode)insn;
                if(field.getOpcode() == opcode && field.owner.equals(owner) && field.name.equals(name) && field.desc.equals(desc)) {
                    return insn;
                }
            }
        }
        return null;
    }


    public static List<AbstractInsnNode> getFieldList(MethodNode mn, int opcode, String owner, String name, String desc) {
        List<AbstractInsnNode> list = new ArrayList<>();
        for(AbstractInsnNode insn : mn.instructions.toArray()) {
            if(insn instanceof FieldInsnNode) {
                final FieldInsnNode field = (FieldInsnNode)insn;
                if(field.getOpcode() == opcode && field.owner.equals(owner) && field.name.equals(name) && field.desc.equals(desc)) {
                    list.add(field);
                }
            }
        }
        return list;
    }

    public static AbstractInsnNode findPatternInsn(MethodNode mn, int[] pattern) {
        for(AbstractInsnNode insn : mn.instructions.toArray()) {
            for (final int opcode : pattern) {
                if (opcode == insn.getOpcode()) {
                    return insn;
                }
            }
        }
        return null;
    }

    public static AbstractInsnNode findInsnLdc(MethodNode mn, String s) {
        for(AbstractInsnNode insn : mn.instructions.toArray()) {
            if(insn instanceof LdcInsnNode) {
                final LdcInsnNode ldc = (LdcInsnNode)insn;
                if(ldc.cst instanceof String) {
                    String var = (String)ldc.cst;
                    if(var.equals(s)) {
                        return insn;
                    }
                }
            }
        }
        return null;
    }
    public static AbstractInsnNode head(MethodNode method) {
        return method.instructions.getFirst().getNext();
    }
    public static AbstractInsnNode bottom(MethodNode method) {
        return method.instructions.get(method.instructions.size() - 2);
    }

    public static ClassNode getNode(byte[] classBuffer) {
        ClassNode classNode = new ClassNode();
        ClassReader reader = new ClassReader(classBuffer);
        reader.accept(classNode, 0);
        return classNode;
    }

    public static byte[] toBytes(ClassNode classNode) {
        ClassWriter writer = new ClassWriter(COMPUTE_MAXS | COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}
