package net.mitask.emcgenesis.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mitask.emcgenesis.EMCGenesis;
import net.mitask.emcgenesis.gui.AlchemicalChestGUI;
import net.mitask.emcgenesis.state.AlchemyBagState;
import net.mitask.emcgenesis.state.StateManager;
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
    public ItemStack use(ItemStack stack, World world, PlayerEntity playerEntity) {
        if(world.isRemote && EMCGenesis.isClient()) return stack;

        AlchemyBagState state = getState(playerEntity);
        if(state != null) new AlchemicalChestGUI(playerEntity.inventory, state).openGUI(playerEntity);

        return stack;
    }

    public AlchemyBagState getState(PlayerEntity playerEntity) {
        return StateManager.getOrCreateState(
                AlchemyBagState.class,
                "emcgenesis_alchemybag_" + color + "_" + StateManager.generateUUID(playerEntity)
        );
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
        LIGHTBLUE,
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