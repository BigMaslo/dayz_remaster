package com.github.dayzminecraft.dayzminecraft.client.entities;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityBandit;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityCrawler;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBandit extends RenderBiped {
    public RenderBandit() {
        super(new ModelBiped(), 0.5F, 1.0F);
    }

    public void renderBandit(EntityBandit entityCrawler, double x, double y, double z, float yaw, float partialTickTime) {
        super.doRender(entityCrawler, x, y, z, yaw, partialTickTime);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime) {
        renderBandit((EntityBandit) entity, x, y, z, yaw, partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(DayZ.meta.modId + ":textures/entities/bandit.png");
    }
}
