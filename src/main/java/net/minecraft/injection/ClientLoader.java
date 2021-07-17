package net.minecraft.injection;

import java.util.Map;


import cn.snowflake.rose.transform.ClassTransformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;

@Name("ClientLoader")
public class ClientLoader implements IFMLLoadingPlugin {
	public static boolean runtimeObfuscationEnabled = true;
	
	@Override
	public String[] getASMTransformerClass() {
		  return new String[]{ClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		runtimeObfuscationEnabled = (boolean)data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
	
}
