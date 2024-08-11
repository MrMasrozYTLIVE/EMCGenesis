package net.mitask.emcgenesis.util.items;

import net.mitask.emcgenesis.EMCGenesis;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BaseItem extends TemplateItem {
    public final Identifier ID;

    public BaseItem(Identifier id) {
        super(id);
        setTranslationKey(id);
        ID = id;
    }

    public BaseItem(String id) {
        this(EMCGenesis.NAMESPACE.id(id));
    }

    public String getTexturePrefix() {
        return "item/";
    }
    public Identifier getItemTexture() {
        return ID.withPrefixedPath(getTexturePrefix());
    }
}
