package net.mitask.emcgenesis.event.common;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mitask.emcgenesis.item.*;
import net.mitask.emcgenesis.item.armor.DarkMatterArmor;
import net.mitask.emcgenesis.item.armor.GemArmor;
import net.mitask.emcgenesis.item.armor.RedMatterArmor;
import net.mitask.emcgenesis.util.items.BaseArmorItem;
import net.mitask.emcgenesis.util.items.BaseItem;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemListener {
    public static final List<BaseItem> ITEMS = new ArrayList<>();
    public static final List<AlchemyBag> ALCHEMY_BAGS = new ArrayList<>();
    public static final List<KleinStar> KLEIN_STARS = new ArrayList<>();
    public static final List<BaseArmorItem> ARMOR = new ArrayList<>();

    @EventListener
    private static void registerItems(ItemRegistryEvent event) {
        ITEMS.add(new PhilosopherStone("philosopher_stone"));
        ITEMS.add(new Fuel("mobius_fuel"));
        ITEMS.add(new Matter("red_matter"));
        ITEMS.add(new Fuel("aeternalis_fuel"));
        ITEMS.add(new TransmutationTablet("transmutation_tablet"));
        ITEMS.add(new Fuel("alchemical_coal"));
        ITEMS.add(new TomeOfKnowledge("tome_of_knowledge"));
        ITEMS.add(new Matter("dark_matter"));
        ITEMS.add(new CovalenceDust("covalence_dust_low"));
        ITEMS.add(new CovalenceDust("covalence_dust_medium"));
        ITEMS.add(new CovalenceDust("covalence_dust_high"));

        for (AlchemyBag.BagColor color : AlchemyBag.BagColor.values()) {
            ALCHEMY_BAGS.add(new AlchemyBag(color));
        }

        for (int i = 1; i <= 6; i++) {
            KLEIN_STARS.add(new KleinStar("klein_star_" + i));
        }

        for (BaseArmorItem.Slot slot : BaseArmorItem.Slot.values()) {
            ARMOR.add(new GemArmor("gem_armor", slot));
            ARMOR.add(new RedMatterArmor("redmatter_armor", slot));
            ARMOR.add(new DarkMatterArmor("darkmatter_armor", slot));
        }

        AccessoryRegister.add("ring");
        AccessoryRegister.add("ring");
    }
}
