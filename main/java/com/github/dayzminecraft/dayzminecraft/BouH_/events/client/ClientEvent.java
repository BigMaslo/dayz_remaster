package com.github.dayzminecraft.dayzminecraft.BouH_.events.client;

import com.github.dayzminecraft.dayzminecraft.BouH_.en_data.Thirst;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.GunBase;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.render.GunItemRender;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.NetworkHandler;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.packets.gun.PacketReloading;
import com.github.dayzminecraft.dayzminecraft.DayZ;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ClientEvent {
    public static ClientEvent instance = new ClientEvent();
    public boolean isDown;
    public int currentItem;

    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent ev) {
        ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof GunBase) {
            if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
                GunBase aGunBase = (GunBase) stack.getItem();
                if (aGunBase.canReload(player, stack)) {
                    NetworkHandler.NETWORK.sendToServer(new PacketReloading());
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (player != null) {
            player.capabilities.setFlySpeed(0.15f);
            ItemStack stack = player.getHeldItem();
            if (this.currentItem != player.inventory.currentItem) {
                this.currentItem = player.inventory.currentItem;
                this.isDown = true;
            }
            if (GunItemRender.instance.recoil > 0) {
                GunItemRender.instance.recoil -= 1;
            }
            if (mc.currentScreen == null) {
                if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof GunBase) {
                    GunBase aGunBase = ((GunBase) stack.getItem());
                    if (Mouse.isButtonDown(1) && !this.isDown) {
                        if (aGunBase.canShoot(stack)) {
                            aGunBase.startShooting(player, stack);
                        } else if (aGunBase.getAmmo(stack) == 0) {
                            if (aGunBase.canReload(player, stack)) {
                                NetworkHandler.NETWORK.sendToServer(new PacketReloading());
                            }
                        }
                        if (!aGunBase.isAutomatic()) {
                            this.isDown = true;
                        }
                    }
                }
                if (!Mouse.isButtonDown(1) && this.isDown) {
                    this.isDown = false;
                }
            } else {
                this.isDown = true;
            }
        }
    }

    public static ResourceLocation cross = new ResourceLocation(DayZ.meta.modId + ":textures/gui/cross.png");
    public static ResourceLocation thirst = new ResourceLocation(DayZ.meta.modId + ":textures/gui/thirst.png");
    public static ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent.Pre ev) {
        int scaledWidth = ev.resolution.getScaledWidth();
        int scaledHeight = ev.resolution.getScaledHeight();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (!player.capabilities.isCreativeMode) {
            net.minecraft.client.renderer.entity.RenderManager.debugBoundingBox = false;
        }
        if (ev.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            ev.setCanceled(true);
            mc.fontRenderer.drawStringWithShadow("DayZ++ Alpha 0.4", 12, 12, 0xffffff);
            if (player.getHeldItem() != null) {
                if (player.getHeldItem().hasTagCompound() && player.getHeldItem().getItem() instanceof GunBase) {
                    GunBase aGunBase = (GunBase) player.getHeldItem().getItem();
                    String string = (aGunBase.getAmmo(player.getHeldItem()) + " / " + aGunBase.getMaxAmmo());
                    mc.fontRenderer.drawStringWithShadow(string, scaledWidth / 2 + 98, scaledHeight - 14, aGunBase.isReloading(player.getHeldItem()) ? 0x00ff00 : 0xffffff);
                    GL11.glColor3f(1, 1, 1);
                }
            }
            GL11.glPushMatrix();
            OpenGlHelper.glBlendFunc(775, 769, 1, 0);
            mc.getTextureManager().bindTexture(ClientEvent.cross);
            mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 6, scaledHeight / 2 - 7, 0, 0, 16, 16);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glPopMatrix();
        }
        if (ev.type == RenderGameOverlayEvent.ElementType.FOOD) {
            Thirst thirst = Thirst.getThirst(player);
            for (int l3 = 0; l3 < 10; ++l3) {
                int i1 = thirst.getThirst();
                int l1 = scaledWidth / 2 + 91;
                int i2 = scaledHeight - 49;
                int k5 = i2;
                int i4 = 16;
                mc.getTextureManager().bindTexture(ClientEvent.thirst);

                if (thirst.thirstSaturationLevel <= 0.0F && player.ticksExisted % (i1 * 3 + 1) == 0) {
                    k5 = i2 + (player.worldObj.rand.nextInt(3) - 1);
                }

                int k4 = l1 - l3 * 8 - 9;
                mc.ingameGUI.drawTexturedModalRect(k4, k5, 0, 0, 9, 9);

                if (l3 * 2 + 1 < i1) {
                    mc.ingameGUI.drawTexturedModalRect(k4, k5, 36, 0, 9, 9);
                }

                if (l3 * 2 + 1 == i1) {
                    mc.ingameGUI.drawTexturedModalRect(k4, k5, 45, 0, 9, 9);
                }
            }
            mc.getTextureManager().bindTexture(ClientEvent.icons);
        }
    }
}
