package net.mitask.emcgenesis.util;

import net.minecraft.world.PersistentState;
import net.mitask.emcgenesis.EMCGenesisClient;

import java.util.logging.Logger;

public class StateManager {
    private static final Logger LOGGER = Logger.getLogger("StateManager");

    public static <T extends PersistentState> T getOrCreateState(Class<T> clazz, String id) {
        T state = (T) EMCGenesisClient.getWorld().getOrCreateState(clazz, id);
        if(state == null) {
            try {
                state = clazz.getConstructor(String.class).newInstance(id);
            } catch (Exception e) {
                LOGGER.throwing("StateManager", "getOrCreateState<%o>".formatted(clazz.getName()), e);
                return null;
            }
            EMCGenesisClient.getWorld().setState(id, state);
            state.markDirty();
        }

        return state;
    }
}
