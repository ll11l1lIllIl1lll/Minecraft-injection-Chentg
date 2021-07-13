package cn.snowflake.rose.mod.mods.RENDER;


import cn.snowflake.rose.events.impl.EventRender3D;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.mcutil.AltAxisAlignedBB;
import cn.snowflake.rose.utils.render.ColorUtil;
import cn.snowflake.rose.utils.mcutil.GlStateManager;
import cn.snowflake.rose.utils.Value;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntityChest;

import java.awt.*;

public class ChestESP extends Module {
    public static Value<String> mode = new Value("ChestESP", "Mode", 0);
    public Value<Double> alpha = new Value<Double>( "ChestESP_Alpha", 0.1d, 0.1d, 1.0d);
    public Value<Double> Width = new Value<Double>("ChestESP_Width", 0.5d, 0.5d, 5d);
    public Value<Boolean> Alpha = new Value<Boolean>("ChestESP_Alpha", true);
    public Value<Boolean> Lines = new Value<Boolean>("ChestESP_Lines", true);

    public ChestESP() {
        super("ChestESP", "Chest ESP",Category.RENDER);
        this.mode.addValue("ESP");
        setChinesename("\u7bb1\u5b50\u900f\u89c6");
    }

    @Override
    public String getDescription() {
        return "箱子透视!";
    }

    @EventTarget
    public void onRender(EventRender3D e) throws ClassNotFoundException {
        if (mode.isCurrentMode("ESP")){



            for (final Object o : mc.theWorld.loadedTileEntityList) {
            if (o instanceof TileEntityChest || o instanceof TileEntityEnderChest) {
                TileEntity tileEntity = (TileEntity) o;
                float renderX = (float) (tileEntity.xCoord - RenderManager.instance.viewerPosX);
                float renderY = (float) (tileEntity.yCoord - RenderManager.instance.viewerPosY);
                float renderZ = (float) (tileEntity.zCoord - RenderManager.instance.viewerPosZ);
                double minX = renderX;
                double minY = renderY;
                double minZ = renderZ;
                double maxX = renderX + tileEntity.getBlockType().getBlockBoundsMaxX();
                double maxY = renderY + tileEntity.getBlockType().getBlockBoundsMaxY();
                double maxZ = renderZ + tileEntity.getBlockType().getBlockBoundsMaxZ();
                double negXDoubleChest = 0;
                double posXDoubleChest = 0;
                double negZDoubleChest = 0;
                double posZDoubleChest = 0;

                Color color = ColorUtil.getClickGUIColor();
                if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest || Class.forName("cpw.mods.ironchest.TileEntityIronChest").isInstance(tileEntity)) {
                    if (tileEntity instanceof TileEntityChest) {
                        negXDoubleChest = ((TileEntityChest) tileEntity).adjacentChestXNeg != null ? 1 : 0D;
                        posXDoubleChest = ((TileEntityChest) tileEntity).adjacentChestXPos != null ? 0.875 : 0D;
                        negZDoubleChest = ((TileEntityChest) tileEntity).adjacentChestZNeg != null ? 1 : 0D;
                        posZDoubleChest = ((TileEntityChest) tileEntity).adjacentChestZPos != null ? 0.875 : 0D;
                    }
                    minX = (renderX + 0.0625) -  negXDoubleChest;
                    minY = renderY;
                    minZ = (renderZ + 0.0625) - negZDoubleChest;
                    maxX = (renderX + 0.9375) - posXDoubleChest;
                    maxY = renderY + 0.875;
                    maxZ = (renderZ + 0.9375) - posZDoubleChest;

                    drawBlockESP(new AltAxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ), color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
                            (float) alpha.getValueState().floatValue(), color.getRed() / 255.0f, color.getGreen() / 255.0f,color.getBlue() / 255.0f, 1, (float) Width.getValueState().floatValue(), Alpha.getValueState().booleanValue(), Alpha.getValueState().booleanValue());

                }
            }
        }
    }
    }

    public static void drawBlockESP(AltAxisAlignedBB axisAlignedBB, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth, boolean bounding, boolean line) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.disableLighting();
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        if (bounding) {
            drawBoundingBox(new AltAxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ));
        }
        GL11.glLineWidth(lineWidth);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        if (line) {
            drawOutlinedBoundingBox(new AltAxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ));
        }
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.enableLighting();
        RenderHelper.disableStandardItemLighting();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
    private static void drawOutlinedBoundingBox(AltAxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(3);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawing(3);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawing(1);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
    }

    private static void drawBoundingBox(AltAxisAlignedBB aa)  {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.maxZ);
        tessellator.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.minZ);
        tessellator.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        tessellator.addVertex(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
    }
}

