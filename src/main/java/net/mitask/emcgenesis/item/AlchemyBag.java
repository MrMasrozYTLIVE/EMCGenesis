package net.mitask.emcgenesis.item;

import net.mitask.emcgenesis.util.items.BaseItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Locale;

public class AlchemyBag extends BaseItem {
    String color;

    public AlchemyBag(BagColor color) {
        super("alchemy_bag_" + color.name().toLowerCase(Locale.ROOT));
        setTranslationKey(Identifier.of(ID.getNamespace(), "alchemy_bag"));
        setMaxCount(1);
        this.color = color.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String getTexturePrefix() {
        return super.getTexturePrefix() + "alchemy_bags/";
    }
    @Override
    public Identifier getItemTexture() {
        return Identifier.of(ID.getNamespace(), getTexturePrefix() + color);
    }

    public enum BagColor {
        BLACK,
        BLUE,
        BROWN,
        CYAN,
        GRAY,
        GREEN,
        LIGHT_BLUE,
        LIME,
        MAGENTA,
        ORANGE,
        PINK,
        PURPLE,
        RED,
        SILVER,
        WHITE,
        YELLOW
    }
}