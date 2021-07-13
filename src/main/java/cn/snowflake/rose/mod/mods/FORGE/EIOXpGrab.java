package cn.snowflake.rose.mod.mods.FORGE;

import cn.snowflake.rose.mod.Category;
import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.utils.forge.ForgePacketHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.ArrayList;
import java.util.List;

public class EIOXpGrab extends Module{

	public EIOXpGrab() {
		super("EIOXpGrab", "EIOXpGrab", Category.FORGE);
		working = false;
	}

	@Override
	public void onEnable() {
		nearTiles().forEach(this::sendGrab);
		super.onEnable();
	}

	private void sendGrab(TileEntity tile) {
	        try {
	            if (Class.forName("crazypants.enderio.machine.obelisk.xp.TileExperienceObelisk").isInstance(tile)) {
	                ForgePacketHelper.sendPacket("enderio", (byte) 69, coords(tile), Short.MAX_VALUE);
	            }
	        } catch(Exception e) {}
	    }
	
	 public int[] coords(TileEntity curTile) {
	        return new int[] { curTile.xCoord, curTile.yCoord, curTile.zCoord };
	 }
	 
	 public List<TileEntity> nearTiles() {
	        List<TileEntity> out = new ArrayList<TileEntity>();
	        IChunkProvider chunkProvider = mc.theWorld.getChunkProvider();
	        if (chunkProvider instanceof ChunkProviderClient) {
	            for (Chunk chunk : (List<Chunk>) ReflectionHelper.getPrivateValue(ChunkProviderClient.class, (ChunkProviderClient) chunkProvider, 3)) {
	                for (Object entityObj : chunk.chunkTileEntityMap.values()) {
	                    if (entityObj instanceof TileEntity) {
	                        out.add((TileEntity)entityObj);
	                    }
	                }
	            }
	        }
	        return out;
	    }
	
}
