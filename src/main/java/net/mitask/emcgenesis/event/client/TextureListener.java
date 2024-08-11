package net.mitask.emcgenesis.event.client;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.mitask.emcgenesis.event.common.ItemListener;
import net.mitask.emcgenesis.util.items.BaseArmorItem;
import net.mitask.emcgenesis.util.items.BaseItem;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;

public class TextureListener {
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        ItemListener.ITEMS.forEach(this::registerTexture);
        ItemListener.ALCHEMY_BAGS.forEach(this::registerTexture);
        ItemListener.KLEIN_STARS.forEach(this::registerTexture);
        ItemListener.ARMOR.forEach(this::registerTexture);
    }

    private void registerTexture(BaseItem item) {
        item.setTexture(item.getItemTexture());
    }
    private void registerTexture(BaseArmorItem item) {
        item.setTexture(item.getItemTexture());
    }
}
