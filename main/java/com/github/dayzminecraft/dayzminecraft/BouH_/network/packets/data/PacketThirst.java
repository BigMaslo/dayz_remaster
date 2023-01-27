package com.github.dayzminecraft.dayzminecraft.BouH_.network.packets.data;

import com.github.dayzminecraft.dayzminecraft.BouH_.en_data.Thirst;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.SimplePacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;

public class PacketThirst extends SimplePacket {

    public PacketThirst() {
    }

    public PacketThirst(int thirst, float exhaustion) {
        buf().writeInt(thirst);
        buf().writeFloat(exhaustion);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        Thirst.getThirst(player).setThirst(buf().readInt());
        Thirst.getThirst(player).setSaturation(buf().readFloat());
    }
}
