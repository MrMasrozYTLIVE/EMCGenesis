package net.mitask.emcgenesis.api;

import net.minecraft.entity.player.PlayerEntity;
import net.mitask.emcgenesis.util.Player;
import net.mitask.emcgenesis.util.StateManager;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class EMCManager {
    public static class PLAYER {
        public static void addEMC(PlayerEntity playerEntity, long amount) {
            Player player = getPlayer(playerEntity);
            player.setEMC(player.getEMC() + amount);
        }

        public static void setEMC(PlayerEntity playerEntity, long amount) {
            getPlayer(playerEntity).setEMC(amount);
        }

        public static void subtractEMC(PlayerEntity playerEntity, long amount) {
            Player player = getPlayer(playerEntity);
            player.setEMC(Math.max(player.getEMC() - amount, 0));
        }

        public static Long getPlayerEMC(PlayerEntity playerEntity) {
            return getPlayer(playerEntity).getEMC();
        }

        public static Player getPlayer(PlayerEntity playerEntity) {
            return StateManager.getOrCreateState(
                    Player.class,
                    "emcgenesis_player_" + UUID.nameUUIDFromBytes(playerEntity.name.getBytes(StandardCharsets.UTF_8))
            );
        }
    }
}
