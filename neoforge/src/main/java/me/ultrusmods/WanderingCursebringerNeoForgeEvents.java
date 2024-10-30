package me.ultrusmods;

import me.ultrusmods.wanderingcursebringer.command.CurseCommand;
import me.ultrusmods.wanderingcursebringer.curse.CurseManager;
import me.ultrusmods.wanderingcursebringer.platform.Services;
import me.ultrusmods.wanderingcursebringer.register.CurseRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class WanderingCursebringerNeoForgeEvents {
    private static final Component BED_MESSAGE = Component.translatable("sleep_reason.wanderingcursebringer.insomnia_curse");

    @SubscribeEvent
    public static void canPlayerSleepEvent(CanPlayerSleepEvent event) {
        Player player = event.getEntity();
        if (CurseManager.hasCurse(player, CurseRegistry.INSOMNIA)) {
            event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
            event.getEntity().displayClientMessage(BED_MESSAGE, true);
        }
    }

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        Player newPlayer = event.getEntity();
        Services.PLATFORM.getPlayerCurses(newPlayer).forEach((curse, level) -> curse.onCurseApplied(newPlayer, level));
    }


    @SubscribeEvent
    public static void registerCommandEvent(RegisterCommandsEvent event) {
        CurseCommand.register(
                event.getDispatcher(),
                event.getBuildContext()
        );
    }
}
