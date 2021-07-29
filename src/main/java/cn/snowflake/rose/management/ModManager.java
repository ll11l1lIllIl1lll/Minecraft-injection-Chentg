package cn.snowflake.rose.management;

import cn.snowflake.rose.mod.Module;
import cn.snowflake.rose.mod.mods.COMBAT.*;
import cn.snowflake.rose.mod.mods.FORGE.*;
import cn.snowflake.rose.mod.mods.FORGE.Furniture.ItemCreator;
import cn.snowflake.rose.mod.mods.FORGE.advancedsolarpanel.ASPCrash;
import cn.snowflake.rose.mod.mods.FORGE.ae2.CellViewer;
import cn.snowflake.rose.mod.mods.FORGE.carpentersblocks.CarpenterUse;
import cn.snowflake.rose.mod.mods.FORGE.galacticraft.GalacticFire;
import cn.snowflake.rose.mod.mods.FORGE.galacticraft.GalaxyTeleport;
import cn.snowflake.rose.mod.mods.FORGE.galacticraft.NoLimitSpin;
import cn.snowflake.rose.mod.mods.FORGE.mrf.RocketChaos;
import cn.snowflake.rose.mod.mods.FORGE.mtrm.MTRMEdit;
import cn.snowflake.rose.mod.mods.FORGE.nbtedit.NBTHack;
import cn.snowflake.rose.mod.mods.FORGE.taintedmagic.NoLimitAura;
import cn.snowflake.rose.mod.mods.FORGE.thaumcraft.AspectGod;
import cn.snowflake.rose.mod.mods.FORGE.MagicRecipe;
import cn.snowflake.rose.mod.mods.FORGE.thaumcraft.ForceScanned;
import cn.snowflake.rose.mod.mods.FORGE.thaumcraft.ResearchGod;
import cn.snowflake.rose.mod.mods.MOVEMENT.*;
import cn.snowflake.rose.mod.mods.PLAYER.*;
import cn.snowflake.rose.mod.mods.RENDER.*;
import cn.snowflake.rose.mod.mods.WORLD.*;

import java.util.ArrayList;

public class ModManager {
    public static ArrayList<Module> modList = new ArrayList();

    public ModManager() {
        //COMBAT
        addMod(new Aimbot());
        addMod(new Aura());
        addMod(new Regen());
        addMod(new NoRecoil());
        addMod(new Criticals());
        addMod(new FastBow());
        addMod(new AntiAim());
        addMod(new TPAura());
        addMod(new Velocity());
        addMod(new AutoFish());
        addMod(new Blink());
        //MOVEMENT
        addMod(new Sprint());
        addMod(new AirJump());
        addMod(new Fly());
        addMod(new VClipDown());
        addMod(new VClipUP());
        addMod(new FastClimb());
        addMod(new Scaffold());
        addMod(new Speed());
//        addMod(new TargetStrafe());
        addMod(new NoFall());
        addMod(new Jesus());
        addMod(new Step());
        addMod(new Teleport());
        
        //RENDER
        addMod(new HUD());
        addMod(new NoHurtcam());
        addMod(new ArrowESP());
        addMod(new CrossHair());
        addMod(new PlayerList());
        addMod(new Nametags());
        addMod(new FullBright());
        addMod(new ItemESP());
        addMod(new NameProtect());
        addMod(new ClickGui());
        addMod(new Tracers());
        addMod(new Chams());
        addMod(new ViewClip());
        addMod(new ESP2D());
        addMod(new ChestESP());
        addMod(new SimpleHUD());
        addMod(new BlockOverlay());
        addMod(new ESP());
        addMod(new Notifications());
        addMod(new NoRain());
        //PLAYER
        addMod(new NoSlowDown());
        addMod(new AutoTool());
        addMod(new NoWeb());
        addMod(new Stealer());
        addMod(new AntiEffect());
        addMod(new FastBreak());
        addMod(new Freecam());
        addMod(new FastEat());
        addMod(new FastPlace());
        addMod(new FastUse());
        addMod(new InvMove());
        addMod(new Timer());
        //WORLD
        addMod(new ServerCrasher());
        addMod(new Bot());
        addMod(new Xray());
        addMod(new NoFriend());
        addMod(new MineBot());
        addMod(new Spammer());
        addMod(new MCF());
        addMod(new FakeLag());
        addMod(new NoCommand());
        addMod(new Plugins());

        //FORGE
        addMod(new ScreenProtect());
        addMod(new SecretClose());
        addMod(new BreakInGui());
        addMod(new FakeCreative());

        addMod(new AntiBackpack());
        //AE2
        addMod(new CellViewer());
        //ThaumCraft
        addMod(new AspectGod());
        addMod(new ResearchGod());
        addMod(new ForceScanned());

        //NBTEdit
        addMod(new NBTHack());
        //MTRMHack
        addMod(new MTRMEdit());
        //NEI
        addMod(new NEISelect());

        //Furniture
        addMod(new ItemCreator());

        addMod(new MagicRecipe());
        addMod(new EIOXpGrab());
        addMod(new OneWayTicket());
        addMod(new FTBSatchelDupe());

        //MRF
        addMod(new RocketChaos());

        //taintedmagic
        addMod(new NoLimitAura());

        //Galcticraft
        addMod(new NoLimitSpin());
        addMod(new GalacticFire());
        addMod(new GalaxyTeleport());

        //Carpentersblocks
        addMod(new CarpenterUse());

        //advancedsolarpanel
        addMod(new ASPCrash());

        //PacketFlood
        addMod(new PacketFlood());

        ok = true;
    }
    public static boolean ok = false;

    public void addMod(Module m) {
        modList.add(m);
    }

    public static Module getModByName(String mod) {
        for (Module m : modList) {
            if (!m.getName().equalsIgnoreCase(mod)) continue;
            return m;
        }
        return null;
    }

    public ArrayList<Module> getModList() {
        return modList;
    }

    public ArrayList<Module> getModHidden() {
        ArrayList<Module> Hidden = new ArrayList();
        for(Module m : this.modList) {
            if (!(m == getModByClass(HUD.class))) {
                Hidden.add(m);
            }
        }
        return Hidden;
    }


    public static Module getModByClass(Class<? extends Module> cls) {
		for (Object m1 : modList) {
			Module m = (Module) m1;
		if (m.getClass() != cls)
			continue;
		return m;
	}
	return null;
	}

}