package net.mitask.emcgenesis.util;

import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@SuppressWarnings("unused")
public class Player extends PersistentState {
    private long EMC;
    private List<String> learnt;

    public Player(String id) {
        super(id);
    }

    public void setEMC(long EMC) {
        this.EMC = EMC;
        markDirty();
    }

    public void addLearnt(String item) {
        this.learnt.add(item);
        markDirty();
    }
    public void addLearnt(Item item) {
        addLearnt(ItemUtil.toStringId(item));
    }
    public void addLearnt(ItemStack item) {
        addLearnt(ItemUtil.toStringId(item));
    }

    public boolean isLearnt(String item) {
        return learnt.contains(item);
    }
    public boolean isLearnt(Item item) {
        return isLearnt(ItemUtil.toStringId(item));
    }
    public boolean isLearnt(ItemStack item) {
        return isLearnt(ItemUtil.toStringId(item));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        EMC = nbt.getLong("emc");
        learnt = Arrays.stream(nbt.getString("learnt").split(",")).toList();
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putLong("emc", EMC);
        nbt.putString("learnt", learnt.stream().map(Object::toString).collect(Collectors.joining(",")));
    }
}
