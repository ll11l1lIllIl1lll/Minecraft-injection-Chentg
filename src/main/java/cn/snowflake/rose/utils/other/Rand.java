package cn.snowflake.rose.utils.other;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.commons.codec.digest.DigestUtils;

public class Rand {
    private final TileEntity[] tiles = {new TileEntityPiston(), new TileEntityNote(), new TileEntityMobSpawner(), new TileEntityCommandBlock(), new TileEntityBeacon()};
    private final Entity[] entityes = {new EntityBlaze(null), new EntityCreeper(null), new EntityGhast(null), new EntitySlime(null), new EntitySpider(null)};
    private final int randCoordCorr;
    public Rand() {
        randCoordCorr = 15;
    }

    private static String[] formatColors = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static final String[] splashes = {
    };

    public static int num() {
        return num(-1337, 1337);
    }

    public static int num(int max) {
        return num(0, max);
    }
    public static int num(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static String str(String in) {
        return DigestUtils.md5Hex(in);
    }

    public static String str() {
        return str(UUID.randomUUID().toString());
    }

    public static boolean bool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static int[] coords(int[] c, int r) {
        return new int[] { num(c[0] - r, c[0] + r), num(c[1] - r, c[1] + r), num(c[2] - r, c[2] + r) };
    }

    public static String formatColor() {
        return "ยง" + formatColors[num(formatColors.length)];
    }

    public static String formatSplash() {
        return formatColor().concat(splash());
    }

    public static String splash() {
        return splashes[num(splashes.length)];
    }

    public int x() {
        int[] pos = new int[3];
        pos[0] = (int) Minecraft.getMinecraft().thePlayer.posX;
        pos[1] = (int) Minecraft.getMinecraft().thePlayer.posY;
        pos[2] = (int) Minecraft.getMinecraft().thePlayer.posZ;
        return num(pos[0] - randCoordCorr, pos[0] + randCoordCorr);
    }
    public int y() {
        int[] pos = new int[3];
        pos[0] = (int) Minecraft.getMinecraft().thePlayer.posX;
        pos[1] = (int) Minecraft.getMinecraft().thePlayer.posY;
        pos[2] = (int) Minecraft.getMinecraft().thePlayer.posZ;
        return num(pos[1] - randCoordCorr, pos[1] + randCoordCorr);
    }
    public int z() {
        int[] pos = new int[3];
        pos[0] = (int) Minecraft.getMinecraft().thePlayer.posX;
        pos[1] = (int) Minecraft.getMinecraft().thePlayer.posY;
        pos[2] = (int) Minecraft.getMinecraft().thePlayer.posZ;
        return num(pos[2] - randCoordCorr, pos[2] + randCoordCorr);
    }
    public Entity ent() {
        return entityes[num(entityes.length)];
    }
    public TileEntity tile() {
        return tiles[num(tiles.length)];
    }
    public ItemStack item() {
        ItemStack[] stacks = {new ItemStack(Items.diamond)};
        return stacks[num(stacks.length)];
    }
    public NBTTagCompound nbt() {
        NBTTagCompound randNbt = new NBTTagCompound();
        randNbt.setString(str(), str());
        return randNbt;
    }
}
