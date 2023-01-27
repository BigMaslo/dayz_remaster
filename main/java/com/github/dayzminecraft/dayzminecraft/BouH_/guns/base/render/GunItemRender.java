package com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.render;

import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.GunBase;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class GunItemRender implements IItemRenderer {
    public static GunItemRender instance = new GunItemRender();
    public int recoil;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemRendering, Object... data) {
        Tessellator tessellator = Tessellator.instance;
        EntityLivingBase entity = (EntityLivingBase) data[1];
        GunBase stack = (GunBase) itemRendering.getItem();
        if (stack != null && itemRendering.hasTagCompound()) {
            IIcon icon = entity.getItemIcon(itemRendering, 0);
            if (icon != null) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                        GL11.glPushMatrix();
                        GL11.glRotatef(5, 1, 0, 0);
                        GL11.glRotatef(5, 0, 1, 0);
                        GL11.glRotatef(-10, 0, 1, 1);
                        GL11.glRotatef(-5, 1, 1, 0);
                        GL11.glTranslatef(0.2f, 0.1f, -0.2f);
                        if (stack.isReloading(itemRendering)) {
                            GL11.glRotatef(-30, 1, 0, 1);
                            GL11.glRotatef(15, 0, 0, 1);
                            GL11.glRotatef(15, 0, 1, 0);
                        } else if (player.isSprinting()) {
                            if (stack.getShootTicks(itemRendering) <= 0) {
                                GL11.glRotatef(-20, 1, 0, 1);
                                GL11.glRotatef(15, 0, 1, 1);
                            }
                        }
                        if (stack.getGunRenderType() == GunBase.RenderType.Rifle) {
                            GL11.glScalef(1.25f, 1.25f, 1.25f);
                            GL11.glRotatef(10, 0, 1, 1);
                            GL11.glRotatef(-10, 0, 0, 1);
                            GL11.glTranslatef(-0.3f, -0.15f, -0.1f);
                        }
                        if (this.recoil > 0 && this.recoil < 4) {
                            GL11.glRotatef(stack.getRecoil() * 2.0f, 0, 0, 1);
                            GL11.glTranslatef(0.0f, -stack.getRecoil() * 0.02f, 0.00f);
                        }
                        this.renderWeapon(tessellator, icon);
                        GL11.glPopMatrix();
                    } else {
                        GL11.glPushMatrix();
                        GL11.glScalef(1.2f, 1.2f, 1.2f);
                        GL11.glTranslatef(0.3f, 0.2f, -0.5f);
                        GL11.glRotatef(320, 0, 1, 1);
                        GL11.glRotatef(90, 1, 0, 0);
                        GL11.glRotatef(300, 1, 1, 0);
                        if (stack.getGunRenderType() == GunBase.RenderType.Rifle) {
                            GL11.glScalef(1.3f, 1.3f, 1.3f);
                        }
                        this.renderWeapon(tessellator, icon);
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }

    private void renderWeapon(Tessellator tessellator, IIcon gunIcon) {
        ItemRenderer.renderItemIn2D(tessellator, gunIcon.getMaxU(), gunIcon.getMinV(), gunIcon.getMinU(), gunIcon.getMaxV(), gunIcon.getIconWidth(), gunIcon.getIconHeight(), 0.08f);
    }
}
