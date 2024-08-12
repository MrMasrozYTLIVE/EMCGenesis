package net.mitask.emcgenesis.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.mitask.emcgenesis.item.AlchemyBag;

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
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < width; x++) {
                this.addSlot(new CustomSlot(inventory, index++, posX + (delta * x), posY + (delta * y), playerInventory));
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

    static class CustomSlot extends Slot {
        private final PlayerInventory playerInventory;

        public CustomSlot(Inventory inventory, int index, int x, int y, Inventory playerInventory) {
            super(inventory, index, x, y);
            this.playerInventory = (PlayerInventory) playerInventory;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            if(stack != null && stack.getItem() instanceof AlchemyBag) return false;
            return super.canInsert(stack);
        }

        @Override
        public void setStack(ItemStack stack) {
            if(stack != null && stack.getItem() instanceof AlchemyBag) {
                boolean wasItemPutInFuckingInventory = false;

                for(int slot = 0; slot < 36; slot++) {
                    if(playerInventory.getStack(slot) != null) continue;

                    wasItemPutInFuckingInventory = true;
                    playerInventory.setStack(slot, stack);
                    break;
                }

                if(!wasItemPutInFuckingInventory) playerInventory.setCursorStack(stack);

                return;
            }

            super.setStack(stack);
        }
    }
}
