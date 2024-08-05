package net.mitask.emcgenesis.util;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

@Getter
@Setter
public class Player extends PersistentState {
    private long EMC;

    public Player(String id) {
        super(id);
    }

    public void setEMC(long EMC) {
        this.EMC = EMC;
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        EMC = nbt.getLong("emc");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putLong("emc", EMC);
    }
}
