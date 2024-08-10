package net.mitask.emcgenesis.event.common;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.mitask.emcgenesis.item.*;
import net.mitask.emcgenesis.util.BaseItem;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class ItemListener {
    public static final List<BaseItem> ITEMS = new ArrayList<>();
    public static PhilosopherStone PHILOSOPHERSTONE;
    public static MobiusFuel MOBIUSFUEL;
    public static RedMatter REDMATTER;
    public static AeternalisFuel AETERNALISFUEL;
    public static AlchemyPad ALCHEMYPAD;
    public static AlchemicalFuel ALCHEMICALFUEL;
    public static TomeOfKnowledge TOMEOFKNOWLEDGE;
    public static DarkMatter DARKMATTER;

    @EventListener
    private static void registerItems(ItemRegistryEvent event) {
        ITEMS.add(PHILOSOPHERSTONE = new PhilosopherStone("philosopher_stone"));
        ITEMS.add(MOBIUSFUEL = new MobiusFuel("mobius_fuel"));
        ITEMS.add(REDMATTER = new RedMatter("red_matter"));
        ITEMS.add(AETERNALISFUEL = new AeternalisFuel("aeternalis_fuel"));
        ITEMS.add(ALCHEMYPAD = new AlchemyPad("alchemy_pad"));
        ITEMS.add(ALCHEMICALFUEL = new AlchemicalFuel("alchemical_fuel"));
        ITEMS.add(TOMEOFKNOWLEDGE = new TomeOfKnowledge("tome_of_knowledge"));
        ITEMS.add(DARKMATTER = new DarkMatter("dark_matter"));

        AccessoryRegister.add("ring");
        AccessoryRegister.add("ring");
    }
}
