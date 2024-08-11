package net.mitask.emcgenesis.event.client;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mitask.emcgenesis.EMCGenesisClient;
import net.mitask.emcgenesis.api.EMCManager;
import net.mitask.emcgenesis.config.Config;
import net.mitask.emcgenesis.state.Player;
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipBuildEvent;

public class EMCTooltip {
    @EventListener
    public void addEMCTooltip(TooltipBuildEvent event) {
        Item item = event.itemStack.getItem();
        ItemStack itemStack = event.itemStack;
        long emc = EMCManager.ITEM.getEMC(item, itemStack.getDamage());
        if (emc == 0) return;

        PlayerEntity playerEntity = event.inventory.player;
        Player player = playerEntity.world.isRemote ? EMCGenesisClient.serverPlayerState : EMCManager.PLAYER.getPlayer(playerEntity);
        boolean isLearnt = player.isLearnt(item);

        String checkMark = "§a" + Config.getCheckMark();
        boolean checkVisible = isLearnt && !Config.getCheckMark().isBlank();
        String danyMark = "§c" + Config.getDenyMark();
        boolean danyVisible = !isLearnt && !Config.getDenyMark().isBlank();

        String learningStatus = (checkVisible || danyVisible) ?
                    " (" + (isLearnt ? checkMark : danyMark) + "§r)" :
                    "";

        event.tooltip.add("§eEMC: §r" + String.format("%,d", emc) + learningStatus);
        if(itemStack.count > 1) event.tooltip.add("§eStack EMC: §r" + String.format("%,d", emc * itemStack.count));
    }
}
