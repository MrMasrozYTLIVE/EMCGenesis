package net.mitask.emcgenesis.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.mitask.emcgenesis.EMCGenesisClient;
import net.mitask.emcgenesis.api.EMCManager;
import net.mitask.emcgenesis.state.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(InGameHud.class)
public class IngameHUDMixin extends DrawContext {
    @Inject(method = "render", at = @At(value = "TAIL"))
    public void emcgenesis_renderEMC(float bl, boolean i, int j, int par4, CallbackInfo ci) {
        Minecraft mc = EMCGenesisClient.getMinecraft();
        Player player = mc.player.world.isRemote ? EMCGenesisClient.serverPlayerState : EMCManager.PLAYER.getPlayer(mc.player);
        long EMC = player == null ? 0L : player.getEMC();

        if(!mc.options.debugHud) this.drawTextWithShadow(mc.textRenderer, "EMC: " + EMC, 2, 2, Color.WHITE.getRGB());
    }
}
