package net.mitask.emcgenesis.event.common;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.mitask.emcgenesis.item.BaseItem;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;

public class TextureListener {
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        ItemListener.ITEMS.forEach(this::registerTexture);
    }

    private void registerTexture(BaseItem item) {
        item.setTexture(item.ID.withPrefixedPath("item/"));
    }
}
