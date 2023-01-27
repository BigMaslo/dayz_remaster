package com.github.dayzminecraft.dayzminecraft.BouH_.network.packets.gun;

import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.GunBase;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.SimplePacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class PacketReloading extends SimplePacket {
    public PacketReloading() {
    }

    @Override
    public void server(EntityPlayerMP player) {
        ItemStack stack = player.getHeldItem();
        if (stack != null && stack.hasTagCompound()) {
            if (stack.getItem() instanceof GunBase) {
                GunBase iGunBase = ((GunBase) stack.getItem());
                iGunBase.startReloading(player, stack);
            }
        }
    }
}
