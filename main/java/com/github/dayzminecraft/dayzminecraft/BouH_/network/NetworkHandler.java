package com.github.dayzminecraft.dayzminecraft.BouH_.network;

import com.github.dayzminecraft.dayzminecraft.BouH_.network.packets.data.PacketThirst;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.packets.gun.PacketReloading;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.packets.gun.PacketShoot;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;


public final class NetworkHandler {
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("zombieplague2");
    private static short id;

    public static void registerPackets() throws IllegalAccessException {
        NetworkHandler.register(PacketReloading.class, Side.SERVER);
        NetworkHandler.register(PacketShoot.class, Side.SERVER);
    }

    public static void registerPacketsClient() throws IllegalAccessException {
        NetworkHandler.register(PacketThirst.class, Side.CLIENT);
    }

    public static void sendToAllAround(SimplePacket packet, World world, double x, double y, double z, double distance) {
        NETWORK.sendToAllAround(packet, new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, distance));
    }

    private static void register(Class<? extends SimplePacket> packet, Side side) throws IllegalAccessException {
        try {
            NETWORK.registerMessage(packet.newInstance(), packet, id++, side);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}