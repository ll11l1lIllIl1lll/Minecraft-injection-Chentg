package cn.snowflake.rose.mod.mods.RENDER;

import cn.snowflake.rose.Client;
import cn.snowflake.rose.events.impl.EventRender2D;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.render.RenderUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerList extends Module {
    public PlayerList() {
        super("PlayerList", "Player List", Category.RENDER);
        setChinesename("\u663e\u793a\u9644\u8fd1\u73a9\u5bb6");
    }
    public static Value<Double> x = new Value<Double>("PlayerList_X", 2.0d, 0.0d, 1920.0d, 10.0d);
    public static Value<Double> y = new Value<Double>("PlayerList_Y", 20.0d, 0.0d, 1080.0d, 10.0d);

    @Override
    public String getDescription() {
        return "显示附近玩家!";
    }
    @EventTarget
    public void on2D(EventRender2D eventRender2D){
            float width = 60;
            int height = 10;
            int posX = x.getValueState().intValue();
            int posY = y.getValueState().intValue();

//            RenderUtil.drawRect(posX, posY, getTargets().isEmpty() ? posX +width + 2 : posX + Client.instance.fontManager.simpleton11.getStringWidth(("hp:" + (int)getTargets().get(0).getHealth() + " " +getTargets().get(0).getCommandSenderName() + " d: " + (int)getTargets().get(0).getDistanceToEntity(mc.thePlayer))) + 10, posY + height, new Color(5, 5, 5, 150).getRGB());


            if (getTargets().isEmpty()){
//                RenderUtil.drawRect(posX + 2, posY + height, posX + width, posY + height + 6, new Color(5, 5, 5, 150).getRGB());
                RenderUtil.drawRoundedRectCSGO(posX, posY - 2, posX + width + 2, posY + height + 13,new Color(25,25,25).getRGB());

                RenderUtil.drawGradientRect(posX + 2, posY , posX + width, posY + 1,true,
                        HUD.rainbow(100),
                        HUD.rainbow(1000));

                Client.instance.fontManager.simpleton11.drawStringWithColor("No enemies nearby ", 5 + posX, 10 + posY, -1,0);
            }else {
                int fontY = posY;
                RenderUtil.drawRoundedRectCSGO(posX, posY - 2, posX + Client.instance.fontManager.simpleton11.getStringWidth(("hp:" + (int)getTargets().get(0).getHealth() + " " +getTargets().get(0).getCommandSenderName() + " d: " + (int)getTargets().get(0).getDistanceToEntity(mc.thePlayer))) + 8, (posY + Client.instance.fontManager.simpleton13.FONT_HEIGHT + 5) + (7 * (getTargets().size())) ,new Color(25,25,25).getRGB());

                RenderUtil.drawGradientRect(posX + 2, posY, posX + Client.instance.fontManager.simpleton11.getStringWidth(("hp:" + (int)getTargets().get(0).getHealth() + " " +getTargets().get(0).getCommandSenderName() + " d: " + (int)getTargets().get(0).getDistanceToEntity(mc.thePlayer))) + 6, posY + 1,true,
                        HUD.rainbow(100),
                        HUD.rainbow(1000));

                for (EntityLivingBase e : getTargets()){
//                RenderUtil.drawRect(posX + 2, fontY + height, posX + Client.instance.fontManager.simpleton11.getStringWidth(("hp:" + (int)getTargets().get(0).getHealth() + " " +getTargets().get(0).getCommandSenderName() + " d: " + (int)getTargets().get(0).getDistanceToEntity(mc.thePlayer))) + 8, fontY + height + 7, new Color(5, 5, 5, 150).getRGB());
                    Client.instance.fontManager.simpleton11.drawStringWithColor("hp:" + (int)e.getHealth() + " " +e.getCommandSenderName() + " d: " + (int)e.getDistanceToEntity(mc.thePlayer), 5 + posX, fontY + 10, -1,0);
                    fontY+=Client.instance.fontManager.simpleton11.FONT_HEIGHT + 2;
                }
            }



            Client.instance.fontManager.simpleton13.drawStringWithColor("PlayerList", 5 + posX, 2 + posY, -1,0);


    }

    private List<EntityLivingBase> getTargets() {
        try {
            ArrayList<EntityLivingBase> targets = new ArrayList();
            for(Object entity : mc.theWorld.loadedEntityList) {
                if(entity instanceof EntityPlayer && !((EntityPlayer) entity).getCommandSenderName().contains("\247")  && entity != mc.thePlayer &&  ((Entity)entity).isEntityAlive()) {
                    targets.add((EntityLivingBase) entity);
                }
            }
            targets.sort(Comparator.comparingDouble(
                    o -> -Client.instance.fontManager.simpleton11.getStringWidth("hp:" + (int) o.getHealth() + " " + o.getCommandSenderName() + " d: " + (int) o.getDistanceToEntity(mc.thePlayer))) );

            return targets;
        } catch (Exception e) {
            return null;
        }
    }

}
