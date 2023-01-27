package com.github.dayzminecraft.dayzminecraft.common.blocks;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import com.github.dayzminecraft.dayzminecraft.common.misc.LootManager;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.Random;

public class BlockChestDayZ extends BlockChest {
    public BlockChestDayZ(int isTrapped) {
        super(isTrapped);
        setBlockUnbreakable();
        setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        setCreativeTab(DayZ.creativeTab);
        setTickRandomly(true);
    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        if (world.getClosestPlayer(x, y, z, 32) == null) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity != null && (isEmpty((TileEntityChest) tileEntity))) {
                WeightedRandomChestContent.generateChestContents(random, LootManager.loot, (TileEntityChest) tileEntity, random.nextInt(5) + 1);
            }
        }
    }

    public void onBlockPlacedBy(World p_149689_1_, int x, int y, int z, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        super.onBlockPlacedBy(p_149689_1_, x, y, z, p_149689_5_, p_149689_6_);
        TileEntity tileEntity = p_149689_1_.getTileEntity(x, y, z);
        if (tileEntity != null && (isEmpty((TileEntityChest) tileEntity))) {
            WeightedRandomChestContent.generateChestContents(p_149689_1_.rand, LootManager.loot, (TileEntityChest) tileEntity, p_149689_1_.rand.nextInt(5) + 1);
        }
    }

    private boolean isEmpty(TileEntityChest tileEntityChest) {
        for (int slot = 0; slot < 27; slot++) {
            if (tileEntityChest.getStackInSlot(slot) != null) {
                return false;
            }
        }
        return true;
    }
}