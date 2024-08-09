package net.mitask.emcgenesis.util;

import lombok.Getter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import net.mitask.emcgenesis.EMCGenesis;
import net.mitask.emcgenesis.packet.PlayerStatePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@SuppressWarnings("unused")
public class Player extends PersistentState {
    public PlayerEntity player;
    private long EMC = 0;
    private List<String> learnt = new ArrayList<>();

    public Player(String id) {
        super(id);
    }

    public Player(long EMC, List<String> learnt) {
        super("S2C-Cache");
        this.EMC = EMC;
        this.learnt = learnt;
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

    @Override
    public void setDirty(boolean dirty) {
        super.setDirty(dirty);

        if(EMCGenesis.ENV == EnvType.SERVER) {
            sendPlayerState();
        }
    }

    @Environment(EnvType.SERVER)
    public void sendPlayerState() {
        PacketHelper.sendTo(player, new PlayerStatePacket(EMC, learnt));
    }
}
