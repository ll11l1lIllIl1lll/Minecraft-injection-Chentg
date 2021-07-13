package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.NativeMethod;
import cn.snowflake.rose.events.impl.EventGuiOpen;
import cn.snowflake.rose.events.impl.EventKey;
import cn.snowflake.rose.events.impl.EventTick;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.asm.ASMUtil;
import com.darkmagician6.eventapi.EventManager;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

public class TransformMinecraft implements Opcodes{

    public static void transformMinecraft(ClassNode clazz, MethodNode method) {
        if (method.name.equals("func_71407_l") || method.name.equals("runTick")){
            NativeMethod.method1(method);
        }
        if ((( method.name.equalsIgnoreCase("func_71403_a") || method.name.equalsIgnoreCase("loadWorld") ) && method.desc.equalsIgnoreCase("(Lnet/minecraft/client/multiplayer/WorldClient;)V"))
        ){
            InsnList insnList = new InsnList();
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformWorldClient.class),"call","()Z"));
            LabelNode labelNode = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ,labelNode));
            insnList.add(new InsnNode(RETURN));
            insnList.add(labelNode);
            method.instructions.insert( insnList);
        }

        if (method.name.equals("func_152348_aa")) {
            method.instructions.insert(method.instructions.getFirst(),ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformMinecraft.class), "dispatchKeypressesHook", "()V"));
        }
        if(method.name.equals("func_71411_J") || method.name.equals("runGameLoop")){
            AbstractInsnNode target = ASMUtil.findMethodInsn(method,INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "func_147120_f", "()V");
           if(target != null){
               method.instructions.insert(target.getPrevious(),ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformMinecraft.class), "drawgui", "()V"));
           }
        }

        if (method.name.equals("displayGuiScreen") || method.name.equals("func_147108_a")){

            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ALOAD,1));
            insnList.add(ASMUtil.newInstance(Opcodes.INVOKESTATIC, Type.getInternalName(TransformMinecraft.class), "openGui", "(Lnet/minecraft/client/gui/GuiScreen;)Z"));
            LabelNode labelNode = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ,labelNode));
            insnList.add(new InsnNode(RETURN));
            insnList.add(labelNode);
            method.instructions.insertBefore(method.instructions.getFirst(), insnList);
        }

    }

    public static void drawgui(){
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        float y = scaledresolution.getScaledHeight() - 35;
        if(Minecraft.getMinecraft().theWorld != null && Client.init)
        if(!(Minecraft.getMinecraft().currentScreen instanceof GuiChat))
            if (Client.instance.getNotificationManager() != null && Client.instance.getNotificationManager().getNotifications() != null)
                for (int n = 0; n < Client.instance.getNotificationManager().getNotifications().size(); n++) {
                    Client.instance.getNotificationManager().getNotifications().get(n).draw(y);
                    y -= 14;
                }
    }

    public static boolean openGui(GuiScreen guiScreen){

        EventGuiOpen guiOpen = new EventGuiOpen(guiScreen);
        EventManager.call(guiOpen);

        return guiOpen.isCancelled();
    }


    public static void dispatchKeypressesHook(){
        if (Keyboard.getEventKeyState()) {
            EventManager.call(new EventKey(Keyboard.getEventKey()));
            for (Module mod : Client.instance.modManager.getModList()) {
                if (mod.getKey() != (Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()))continue;
                if (Minecraft.getMinecraft().currentScreen == null) {
                    mod.set(!mod.isEnabled());
//                 }else if (mod.getCategory() == Category.FORGE){
//                    mod.set(!mod.isEnabled());
                }
//                break;
            }
        }
    }


    public static void runTick() {
        Client.onGameLoop();
        EventManager.call(new EventTick());
    }


}