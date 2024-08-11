package net.mitask.emcgenesis.state;

import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.PersistentState;

public class AlchemyBagState extends PersistentState implements Inventory {
    @Getter
    public ItemStack[] inventory;
    public int width = 13;
    public int height = 8;
    public int size = width * height;

    public AlchemyBagState(String id) {
        super(id);
        inventory = new ItemStack[size];
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtList nbtList = nbt.getList("Items");
        this.inventory = new ItemStack[this.inventory.length];

        for(int var3 = 0; var3 < nbtList.size(); ++var3) {
            NbtCompound var4 = (NbtCompound)nbtList.get(var3);
            int var5 = var4.getByte("Slot") & 255;
            if (var5 < this.inventory.length) {
                this.inventory[var5] = new ItemStack(var4);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        NbtList nbtList = new NbtList();

        for(int var3 = 0; var3 < this.inventory.length; ++var3) {
            if (this.inventory[var3] != null) {
                NbtCompound var4 = new NbtCompound();
                var4.putByte("Slot", (byte)var3);
                this.inventory[var3].writeNbt(var4);
                nbtList.add(var4);
            }
        }

        nbt.put("Items", nbtList);
    }

    @Override
    public int size() {
        if(inventory != null) return size;
        return 0;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack item = inventory[slot];
        if(item == null) return null;

        if(item.count <= amount) {
            inventory[slot] = null;
        } else {
            item = item.split(amount);
            if(inventory[slot].count == 0) inventory[slot] = null;
        }
        markDirty();

        return item;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory[slot] = stack;
        markDirty();
    }

    @Override
    public String getName() {
        return "Alchemical Bag";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return inventory != null && inventory.length != 1 && player.getHand() != null;
    }
}
