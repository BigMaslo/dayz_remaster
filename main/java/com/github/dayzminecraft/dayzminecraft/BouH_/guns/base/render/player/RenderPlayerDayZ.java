package com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.render.player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderPlayer;

@SideOnly(Side.CLIENT)
public class RenderPlayerDayZ extends RenderPlayer {
    public RenderPlayerDayZ() {
        this.mainModel = new ModelPlayer(0.0F);
        this.modelBipedMain = (ModelPlayer) this.mainModel;
        this.modelArmorChestplate = new ModelPlayer(1.0F);
        this.modelArmor = new ModelPlayer(0.5F);
    }
}
