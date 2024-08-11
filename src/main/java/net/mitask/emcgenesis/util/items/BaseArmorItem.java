package net.mitask.emcgenesis.util.items;

import net.minecraft.item.ArmorItem;
import net.mitask.emcgenesis.EMCGenesis;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Locale;

public class BaseArmorItem extends TemplateArmorItem implements ArmorTextureProvider {
    public final Slot SLOT;
    public final ArmorType TYPE;
    public final Identifier ID;

    public BaseArmorItem(Identifier id, ArmorType type, Slot slot) {
        super(id, type.type, 0, slot.slot);
        setTranslationKey(id);
        ID = id;
        SLOT = slot;
        TYPE = type;
    }

    public BaseArmorItem(String id, ArmorType type, Slot slot) {
        this(EMCGenesis.NAMESPACE.id(id + "_" + slot.name().toLowerCase(Locale.ROOT)), type, slot);
    }

    public String getTextureLocation() {
        return "item/";
    }
    public Identifier getItemTexture() {
        return Identifier.of(ID.getNamespace(), getTextureLocation() + SLOT.name().toLowerCase(Locale.ROOT));
    }

    @Override
    public Identifier getTexture(ArmorItem armor) {
        return ID.getNamespace().id(TYPE.name().toLowerCase(Locale.ROOT));
    }

    public enum Slot {
        HEAD(0),
        CHEST(1),
        LEGS(2),
        FEET(3);

        final int slot;

        Slot(int slot) {
            this.slot = slot;
        }
    }

    public enum ArmorType {
        DARKMATTER(3),
        GEM(3),
        REDMATTER(3);

        final int type;
        ArmorType(int type) {
            this.type = type;
        }
    }
}
