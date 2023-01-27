package com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.render.player;

import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.GunBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class ModelPlayer extends ModelBiped {
    public ModelPlayer(float p_i1148_1_) {
        super(p_i1148_1_, 0.0F, 64, 32);
    }

    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        EntityPlayer player = (EntityPlayer) p_78087_7_;
        if (this.isSneak) {
            this.bipedBody.rotateAngleX = 0.4F;
            this.bipedRightLeg.rotationPointZ = 3.5F;
            this.bipedLeftLeg.rotationPointZ = 3.5F;
            this.bipedRightLeg.rotationPointY = 8.5F;
            this.bipedLeftLeg.rotationPointY = 8.5F;
        }
        this.bipedLeftLeg.rotateAngleZ = 0;
        this.bipedRightLeg.rotateAngleZ = 0;
        if (player.getHeldItem() != null) {
            float f3 = 0.0F;
            float f4 = 0.0F;
            if (player.getHeldItem().hasTagCompound()) {
                if (player.getHeldItem().getItem() instanceof GunBase) {
                    GunBase gunBase = (GunBase) player.getHeldItem().getItem();
                    if (!gunBase.isReloading(player.getHeldItem())) {
                        this.bipedRightArm.rotateAngleZ = 0.0F;
                        this.bipedLeftArm.rotateAngleZ = 0.0F;
                        this.bipedLeftArm.rotateAngleY = 0.1F - f3 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
                        this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2.0f) + this.bipedHead.rotateAngleX;
                        this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2.0f) + this.bipedHead.rotateAngleX;
                        this.bipedRightArm.rotateAngleY = -(0.1F - f3 * 0.7F) + this.bipedHead.rotateAngleY;
                        this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
                        this.bipedLeftArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
                        this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.01F + 0.01F;
                        this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
                        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.07F) * 0.01F;
                    }
                }
            }
        }
    }
}
