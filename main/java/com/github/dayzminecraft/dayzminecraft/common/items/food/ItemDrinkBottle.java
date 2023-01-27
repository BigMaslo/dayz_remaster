package com.github.dayzminecraft.dayzminecraft.common.items.food;

import com.github.dayzminecraft.dayzminecraft.BouH_.en_data.Thirst;
import com.github.dayzminecraft.dayzminecraft.DayZ;
import com.github.dayzminecraft.dayzminecraft.common.items.ItemMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemDrinkBottle extends ItemMod {
    private final int healAmount;
    private boolean isAlcohol;
    private IIcon emptyIcon;

    public ItemDrinkBottle(int healAmount, boolean isAlcohol) {
        super();
        this.healAmount = healAmount;
        this.isAlcohol = isAlcohol;
        setMaxDamage(3);
    }

    @Override
    public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        Thirst thirst = Thirst.getThirst(entityPlayer);
        thirst.addThirst(this.healAmount, 0.25f);
        itemStack.damageItem(1, entityPlayer);
        if (isAlcohol) {
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 30 * 20, 1));
        }
        world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        return itemStack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.drink;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemStack) {
        return 32;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (itemStack.getItemDamage() != 3) {
            entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }

    public Item isAlcohol(boolean isAlcohol) {
        this.isAlcohol = isAlcohol;
        return this;
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        if (damage != getMaxDamage()) return this.itemIcon;
        else return emptyIcon;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(DayZ.meta.modId + ":" + getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
        emptyIcon = register.registerIcon(DayZ.meta.modId + ":" + getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1) + "-empty");
    }

    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return getIconFromDamage(stack.getItemDamage());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        if (itemStack.getItemDamage() == itemStack.getMaxDamage()) {
            return getUnlocalizedName() + "-empty";
        }
        return getUnlocalizedName();
    }
}
