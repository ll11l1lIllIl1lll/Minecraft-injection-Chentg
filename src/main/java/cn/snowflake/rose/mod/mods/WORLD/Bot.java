package cn.snowflake.rose.mod.mods.WORLD;


import cn.snowflake.rose.command.commands.CommandWalkTo;
import cn.snowflake.rose.events.impl.EventMotion;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.*;
import cn.snowflake.rose.utils.client.ChatUtil;
import cn.snowflake.rose.utils.client.RotationUtil;
import cn.snowflake.rose.utils.mcutil.BlockPos;
import cn.snowflake.rose.utils.path.AStarNode;
import cn.snowflake.rose.utils.path.AStarPath;
import cn.snowflake.rose.utils.path.AStarUtil;
import cn.snowflake.rose.utils.render.ColorUtil;
import cn.snowflake.rose.utils.render.RenderUtil;
import com.darkmagician6.eventapi.EventTarget;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderHandEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class Bot extends Module {
    public EntityPlayer player;
    public static ArrayList<AStarNode> path = new ArrayList();
    public static AStarPath pathFinder;
    private Value<Boolean> esp = new Value("Bot_ESP", true);
    public static AStarUtil astarUtil = new AStarUtil();
    public Bot() {
        super("Bot","Bot", Category.WORLD);
        setChinesename("\u673a\u5668\u4eba");
        working = false;
    }

    @Override
    public String getDescription() {
        return "机器人!";
    }

    @SubscribeEvent
    public void onRedner(RenderHandEvent e){
        if (this.path.size() > 0) {
            glPushMatrix();
            glDisable(GL_TEXTURE_2D);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_LINE_SMOOTH);
            glEnable(GL_BLEND);
            glDisable(GL_DEPTH_TEST);
            mc.entityRenderer.disableLightmap(1);
            glBegin(GL_LINE_STRIP);
            RenderUtil.glColor(ColorUtil.getClickGUIColor().getRGB());
            double renderPosX = RenderManager.instance.viewerPosX;
            double renderPosY = RenderManager.instance.viewerPosY;
            double renderPosZ = RenderManager.instance.viewerPosZ;
            for(AStarNode pos : path) {
                glVertex3d(pos.getX() - renderPosX, pos.getY()  - renderPosY, pos.getZ() - renderPosZ);
            }
            glColor4d(1, 1, 1, 1);
            glEnd();
            glEnable(GL_DEPTH_TEST);
            glDisable(GL_LINE_SMOOTH);
            glDisable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);
            glPopMatrix();
        }
    }
    private boolean startThread = true;

    private void updatePathFinder(int x,int y,int z) {

        Bot.pathFinder = new AStarPath(
                new BlockPos((int)Minecraft.getMinecraft().thePlayer.posX, (int)Minecraft.getMinecraft().thePlayer.posY,(int)Minecraft.getMinecraft().thePlayer.posZ)
                ,new BlockPos(x, y, z)
        );


    }

    public float[] getRotationTo(Vec3 pos) {
        final double xD = Minecraft.getMinecraft().thePlayer.posX - pos.xCoord;
        final double yD = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - pos.yCoord;
        final double zD = Minecraft.getMinecraft().thePlayer.posZ - pos.zCoord;
        final double yaw = Math.atan2(zD, xD);
        final double pitch = Math.atan2(yD, Math.sqrt(Math.pow(xD, 2.0) + Math.pow(zD, 2.0)));
        return new float[]{(float) Math.toDegrees(yaw) + 90.0f, (float) Math.toDegrees(pitch)};
    }

    @EventTarget
    public void onUpdate(EventMotion event) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (this.startThread) {
            this.startThread = false;
            Runnable run = () -> {
                this.updatePathFinder(CommandWalkTo.x,CommandWalkTo.y,CommandWalkTo.z);
                Bot.pathFinder.doAstar();
                Bot.path = Bot.pathFinder.getPath();
                this.startThread = true;
            };
            (new Thread(run)).start();
        }
        if (this.path.size() > 1) {
            AStarNode lastNode = (AStarNode)this.path.get(0);
            if (this.mc.thePlayer.getDistance(lastNode.getX(), lastNode.getY(), lastNode.getZ()) > 2.5D) {
                AStarNode node1 = (AStarNode)this.path.get(this.path.size() - 1);
                AStarNode node2 = (AStarNode)this.path.get(this.path.size() - 2);
                AStarNode node3 = (AStarNode)this.path.get(this.path.size() - 3);
                AStarNode pos = this.mc.thePlayer.getDistance(node1.getX(), node1.getY(), node1.getZ()) < 1.5D ? (this.mc.thePlayer.getDistance(node2.getX(), node2.getY(), node2.getZ()) < 1.5D ? node3 : node2) : node1;
                Class clazz = Class.forName("net.minecraft.util.Vec3");
                Constructor vec3 = clazz.getDeclaredConstructor(double.class,double.class,double.class);
                vec3.setAccessible(true);
                float[] rot = this.getRotationTo((Vec3)(vec3.newInstance(pos.getX(),pos.getY(),pos.getZ())));
                this.mc.thePlayer.rotationYaw = rot[0];
//                    System.out.println("Block" + mc.theWorld.getBlock((int)pos.getX(), (int)pos.getY() - 2, (int)pos.getZ()));
//                    if ((mc.theWorld.getBlock((int)pos.getX(), (int)pos.getY(), (int)pos.getZ()) == Blocks.air)){
//                        rot = (RotationUtil.getRotationsNeededBlock(pos.getX(), pos.getY() - 2, pos.getZ()));
//                        this.mc.thePlayer.rotationYaw = rot[0];
//                        this.mc.thePlayer.rotationPitch = rot[1];
//                    }else{
//                        rot = RotationUtil.getRotationsNeededBlock(pos.getX(), pos.getY(), pos.getZ());
//                        this.mc.thePlayer.rotationYaw = rot[0];
//                        this.mc.thePlayer.rotationPitch = rot[1];
//
//                    }
//                ReflectionHelper.setField(mc.gameSettings.keyBindForward.getClass(),mc.gameSettings.keyBindForward,true,"pressed","field_74513_e");

//                    ((IKeyBinding)this.mc.gameSettings.keyBindForward).setPress(true);
//                    if (AutoMine.astarUtil.getCost(new BlockPos(Minecraft.getMinecraft().thePlayer.posX,Minecraft.getMinecraft().thePlayer.posY,Minecraft.getMinecraft().thePlayer.posZ),new BlockPos(CommandWalkTo.x,CommandWalkTo.x,CommandWalkTo.x)) < 1){
//                        this.startThread = false;
//                    }
                if (this.mc.thePlayer.onGround && (pos.getY() - this.mc.thePlayer.posY > 1.0D || this.mc.thePlayer.isCollidedHorizontally)) {
                    this.mc.thePlayer.jump();
                }
            } else {
                float[] rot = RotationUtil.getRotationsNeededBlock(lastNode.getX(), lastNode.getY() + 1.0D, lastNode.getZ());
                ChatUtil.sendClientMessage("已到达 : " + CommandWalkTo.x + "-" + CommandWalkTo.y + "-" +CommandWalkTo.z);
                this.set(false);
                mc.thePlayer.moveForward = 0f;
//                ReflectionHelper.setField(mc.gameSettings.keyBindForward.getClass(),mc.gameSettings.keyBindForward,false,"pressed","field_74513_e");
            }
        }

    }
}

