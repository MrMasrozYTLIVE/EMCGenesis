package net.mitask.emcgenesis.event.client;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.mitask.emcgenesis.api.EMCManager;
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipRenderEvent;
import net.modificationstation.stationapi.mixin.item.client.DrawContextAccessor;

import java.util.Arrays;
import java.util.stream.IntStream;

public class EMCTooltip {
    @EventListener
    public void registerTooltip(TooltipRenderEvent event) {
        Item item = event.itemStack.getItem();
        long emc = EMCManager.ITEM.getEMC(item);
        if (event.isCanceled() || item == null || emc == 0) return;

        String[] newTooltip = new String[] { event.originalTooltip, "EMC: " + emc };
        Arrays.stream(newTooltip).mapToInt(event.textManager::getWidth).max().ifPresent(tooltipWidth -> {
            int tooltipX = event.mouseX - event.containerX + 12;
            int tooltipY = event.mouseY - event.containerY - 12;
            ((DrawContextAccessor) event.container).invokeFillGradient(tooltipX - 3, tooltipY - 3, tooltipX + tooltipWidth + 3, tooltipY + (8 * newTooltip.length) + (3 * newTooltip.length), -1073741824, -1073741824);
            IntStream.range(0, newTooltip.length).forEach(currentTooltip -> event.textManager.drawWithShadow(newTooltip[currentTooltip], tooltipX, tooltipY + (8 * currentTooltip) + (3 * currentTooltip), -1));
        });
        event.cancel();
    }
}
