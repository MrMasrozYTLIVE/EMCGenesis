package net.mitask.emcgenesis.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AlchemicalChestHandler extends ScreenHandler {
    private final Inventory inventory;
    private final int rows;
    private final int width;

    public AlchemicalChestHandler(Inventory playerInventory, Inventory inventory) {
        this.inventory = inventory;
        this.width = 13;
        this.rows = inventory.size() / width;
        
        int index = 0;
        int delta = 18;

        int posX = 12;
        int posY = 5;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < 8; y++) {
                this.addSlot(new Slot(inventory, index++, posX + (delta * x), posY + (delta * y)));
            }
        }

        index = 9;
        posX = 48;
        posY = 152;
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInventory, index++, posX + (delta * x), posY + (delta * y)));
            }
        }

        posY = 210;
        for(int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInventory, x, posX + (delta * x), posY));
        }
    }

    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public ItemStack getStackInSlot(int slotIndex) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.slots.get(slotIndex);
        if (var3 != null && var3.hasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if (slotIndex < this.rows * width) {
                this.method_2081(var4, this.rows * width, this.slots.size(), true);
            } else {
                this.method_2081(var4, 0, this.rows * width, false);
            }

            if (var4.count == 0) {
                var3.setStack(null);
            } else {
                var3.markDirty();
            }
        }

        return var2;
    }
}
