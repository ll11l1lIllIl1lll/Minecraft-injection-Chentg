package cn.snowflake.rose.transform.transforms;

import cn.snowflake.rose.events.impl.EventRender2D;
import cn.snowflake.rose.events.impl.EventRender3D;
import cn.snowflake.rose.utils.asm.ASMUtil;
import cn.snowflake.rose.utils.render.GLUProjection;
import cn.snowflake.rose.utils.mcutil.GlStateManager;
import com.darkmagician6.eventapi.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class TransformProfiler implements Opcodes
{
    public static void transformProfiler(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("startSection") || methodNode.name.equalsIgnoreCase("func_76320_a")){
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ALOAD,1));
            insnList.add(ASMUtil.newInstance(INVOKESTATIC, Type.getInternalName(TransformProfiler.class), "startSectionHook", "(Ljava/lang/String;)V"));
            methodNode.instructions.insert(insnList);
        }
    }

    public static void startSectionHook(String info){
        if (info.equalsIgnoreCase("hand")){
            Event3D();
        }else if (info.equalsIgnoreCase("forgeHudText")){
            Event2D();
        }
    }

    public static void Event3D(){
        GLUProjection projection = GLUProjection.getInstance();
        IntBuffer viewPort = GLAllocation.createDirectIntBuffer(16);
        FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
        FloatBuffer projectionPort = GLAllocation.createDirectFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projectionPort);
        GL11.glGetInteger(2978, viewPort);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft(),Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight);
        projection.updateMatrices(viewPort, modelView, projectionPort, (double)scaledResolution.getScaledWidth() / (double)Minecraft.getMinecraft().displayWidth, (double)scaledResolution.getScaledHeight() / (double)Minecraft.getMinecraft().displayHeight);
        EventRender3D er3 = new EventRender3D();
        EventManager.call(er3);
    }

    public static void Event2D(){
        GlStateManager.pushMatrix();
        EventRender2D er = new EventRender2D();
        EventManager.call(er);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
