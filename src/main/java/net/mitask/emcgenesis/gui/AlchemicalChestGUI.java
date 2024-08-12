package net.mitask.emcgenesis.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;import net.mitask.emcgenesis.EMCGenesisClient;
import org.lwjgl.opengl.GL11;

public class AlchemicalChestGUI extends HandledScreen {
    private final Inventory inventory;

    public AlchemicalChestGUI(Inventory playerInventory, Inventory inventory) {
        super(new AlchemicalChestHandler(playerInventory, inventory));
        this.inventory = inventory;
        this.field_155 = false;
        this.backgroundWidth = 256;
        this.backgroundHeight = 231;

        this.minecraft = EMCGenesisClient.getMinecraft();
    }

    public void openGUI(PlayerEntity playerEntity) {
        if(playerEntity instanceof ClientPlayerEntity) this.minecraft.setScreen(this);
        else if(playerEntity instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.method_314();
            serverPlayer.field_255.sendPacket(new OpenScreenS2CPacket(serverPlayer.field_260, 0, inventory.getName(), inventory.size()));
            this.container = new AlchemicalChestHandler(serverPlayer.inventory, inventory);
            this.container.syncId = serverPlayer.field_260;
            this.container.addListener(serverPlayer);
        }
    }

    @Override
    protected void drawBackground(float tickDelta) {
        int var2 = this.minecraft.textureManager.getTextureId("/assets/emcgenesis/stationapi/textures/gui/alchchest.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(var2);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}
