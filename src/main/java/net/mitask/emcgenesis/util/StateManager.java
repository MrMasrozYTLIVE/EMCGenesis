package net.mitask.emcgenesis.util;

import net.fabricmc.api.EnvType;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.mitask.emcgenesis.EMCGenesis;
import net.mitask.emcgenesis.EMCGenesisClient;
import net.mitask.emcgenesis.EMCGenesisServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("StateManager");

    @SuppressWarnings("unchecked")
    public static <T extends PersistentState> T getOrCreateState(Class<T> clazz, String id) {
        World world = EMCGenesis.ENV == EnvType.CLIENT ? EMCGenesisClient.getWorld() : EMCGenesisServer.getOverWorld();
        T state = (T) world.getOrCreateState(clazz, id);

        if(state == null) {
            try {
                state = clazz.getConstructor(String.class).newInstance(id);
            } catch (Exception e) {
                LOGGER.error("Error while creating new instance of {}", clazz.getName(), e);
                return null;
            }
            world.setState(id, state);
            state.markDirty();
        }

        return state;

    }
}
