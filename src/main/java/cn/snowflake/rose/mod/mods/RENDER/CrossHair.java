package cn.snowflake.rose.mod.mods.RENDER;


import cn.snowflake.rose.events.impl.EventRender2D;
import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.Value;
import cn.snowflake.rose.utils.render.RenderUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class CrossHair extends Module{
	public Value<String> mode = new Value("CrossHair_Mode", "Mode", 0);
	
	public int corlor;
	
	public CrossHair() {
		super("CrossHair","Cross Hair", Category.RENDER);
		mode.mode.add("Red");
		mode.mode.add("Pink");
		mode.mode.add("White");
		mode.mode.add("Yello");
		mode.mode.add("Green");
		setChinesename("\u51c6\u5fc3");
	}

	@Override
	public String getDescription() {
		return "准心!";
	}

	@EventTarget
	public void On2d(EventRender2D e) {
		 double gap = 1.25D;
	      double width = 0.3D;
	      double size = 4.5D;
	      float alp = 0.7F;
	      float insidealp = 0.7F;
	      if (mode.isCurrentMode("White")) {
	    	  corlor = Color.WHITE.getRGB();
	      }
	      if (mode.isCurrentMode("Red")) {
	    	  corlor = Color.RED.getRGB();
	      }
	      if (mode.isCurrentMode("Pink")) {
	    	  corlor = Color.PINK.getRGB();
	      }
	      if (mode.isCurrentMode("Yello")) {
	    	  corlor = Color.YELLOW.getRGB();
	      }
	      if (mode.isCurrentMode("Green")) {
	    	  corlor = Color.GREEN.getRGB();
	      }
	      ScaledResolution scaledRes = new ScaledResolution(mc,mc.displayWidth,mc.displayHeight);
	      RenderUtil.drawBordRect((scaledRes.getScaledWidth() / 2) - width, (scaledRes.getScaledHeight() / 2) - gap - size - (2), ((scaledRes.getScaledWidth() / 2) + 1.0F) + width, (scaledRes.getScaledHeight() / 2) - gap - (2), 0.5D, RenderUtil.reAlpha(corlor, insidealp), RenderUtil.reAlpha(Color.BLACK.getRGB(), alp));
	      RenderUtil.drawBordRect((scaledRes.getScaledWidth() / 2) - width, (scaledRes.getScaledHeight() / 2) + gap + 1.0D + (2) - 0.15D, ((scaledRes.getScaledWidth() / 2) + 1.0F) + width, (scaledRes.getScaledHeight() / 2 + 1) + gap + size + (2) - 0.15D, 0.5D, RenderUtil.reAlpha(corlor, insidealp), RenderUtil.reAlpha(Color.BLACK.getRGB(), alp));
	      RenderUtil.drawBordRect((scaledRes.getScaledWidth() / 2) - gap - size - (2) + 0.15D, (scaledRes.getScaledHeight() / 2) - width, (scaledRes.getScaledWidth() / 2) - gap - (2) + 0.15D, ((scaledRes.getScaledHeight() / 2) + 1.0F) + width, 0.5D, RenderUtil.reAlpha(corlor, insidealp), RenderUtil.reAlpha(Color.BLACK.getRGB(), alp));
	      RenderUtil.drawBordRect((scaledRes.getScaledWidth() / 2 + 1) + gap + (2), (scaledRes.getScaledHeight() / 2) - width, (scaledRes.getScaledWidth() / 2) + size + gap + 1.0D + (2), ((scaledRes.getScaledHeight() / 2) + 1.0F) + width, 0.5D, RenderUtil.reAlpha(corlor, insidealp), RenderUtil.reAlpha(Color.BLACK.getRGB(), alp));
	}
}	
