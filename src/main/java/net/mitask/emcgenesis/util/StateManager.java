package net.mitask.emcgenesis.util;

import net.minecraft.world.PersistentState;
import net.mitask.emcgenesis.EMCGenesisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("StateManager");

    @SuppressWarnings("unchecked")
    public static <T extends PersistentState> T getOrCreateState(Class<T> clazz, String id) {
        T state = (T) EMCGenesisClient.getWorld().getOrCreateState(clazz, id);
        if(state == null) {
            try {
                state = clazz.getConstructor(String.class).newInstance(id);
            } catch (Exception e) {
                LOGGER.error("Error while creating new instance of " + clazz.getName(), e);
                return null;
            }
            EMCGenesisClient.getWorld().setState(id, state);
            state.markDirty();
        }

        return state;
    }
}
