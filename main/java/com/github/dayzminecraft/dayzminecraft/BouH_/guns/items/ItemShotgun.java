package com.github.dayzminecraft.dayzminecraft.BouH_.guns.items;

import com.github.dayzminecraft.dayzminecraft.BouH_.guns.bullet.EntityBuckshot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemShotgun extends ItemCommonGun {

    public ItemShotgun(String weaponName, RenderType renderType, float inaccuracy, float recoil, float damage, int cdShoot, int maxAmmo, Item ammo, String reloadSound, String shootSound) {
        super(weaponName, renderType, inaccuracy, recoil, damage, cdShoot, maxAmmo, ammo, reloadSound, shootSound);
    }

    @Override
    protected void shoot(ItemStack stack, EntityPlayer player, float rotationPitch, float rotationYaw) {
        for (int i = 0; i < 12; i++) {
            EntityBuckshot entityBullet = new EntityBuckshot(player.worldObj, player, this.getInaccuracy(), this.getDamage());
            player.worldObj.spawnEntityInWorld(entityBullet);
        }
        this.consumeAmmo(stack);
    }
}
