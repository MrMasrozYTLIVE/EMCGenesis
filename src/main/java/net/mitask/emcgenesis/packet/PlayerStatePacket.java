package net.mitask.emcgenesis.packet;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.fabricmc.api.EnvType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.mitask.emcgenesis.EMCGenesis;
import net.mitask.emcgenesis.EMCGenesisClient;
import net.mitask.emcgenesis.api.EMCManager;
import net.mitask.emcgenesis.util.Player;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatePacket extends Packet implements IdentifiablePacket {
    public static final Identifier ID = Identifier.of(EMCGenesis.NAMESPACE, "player_state");

    public long EMC;
    public List<String> learnt = new ArrayList<>();

    @Override
    public void read(DataInputStream stream) {
        try {
            EMC = stream.readLong();
            learnt = Arrays.stream(stream.readUTF().split(",")).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(EMCGenesis.ENV == EnvType.CLIENT) {
            EMCGenesisClient.serverPlayerState = new Player(EMC, learnt);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeLong(EMC);
            stream.writeUTF(learnt.stream().map(Object::toString).collect(Collectors.joining(",")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        PlayerEntity playerEntity = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(playerEntity == null) return;
        Player player = EMCManager.PLAYER.getPlayer(playerEntity);
        EMC = player.getEMC();
        learnt = player.getLearnt();

        PacketHelper.sendTo(playerEntity, this);
    }

    @Override
    public int size() {
        return Long.BYTES + learnt.stream().mapToInt(String::length).sum();
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public static void register() {
        IdentifiablePacket.register(ID, true, true, PlayerStatePacket::new);
    }
}
