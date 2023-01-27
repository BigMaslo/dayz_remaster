package com.github.dayzminecraft.dayzminecraft.BouH_.items;

import com.github.dayzminecraft.dayzminecraft.common.effects.Effect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPainkiller extends ItemFood {

    public ItemPainkiller() {
        super(0, 0, false);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        if (!world.isRemote) {
            for (Object o : player.getActivePotionEffects()) {
                PotionEffect potionEffect = (PotionEffect) o;
                if (potionEffect.getPotionID() != Effect.bleedingId && potionEffect.getPotionID() != Effect.zombificationId) {
                    player.removePotionEffect(potionEffect.getPotionID());
                }
            }
        }
        return stack;
    }
}
