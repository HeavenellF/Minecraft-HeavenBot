package net.heavenell.heavenbot.gamechat;

import net.heavenell.heavenbot.Event.KeyInputHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

public class AutoRoll {
    private static int tickCounter = 0;
    public static ActionResult roll() {
        MinecraftClient mc = MinecraftClient.getInstance();
        tickCounter++;
        if (mc.player != null && KeyInputHandler.AutoRoll) {
            if (tickCounter >= 635) {
                // 620 ticks ≈ 31 seconds
                tickCounter = 0;
                sendRollCommandToChat();
            }
        }
        return ActionResult.PASS;
    }
    private static void sendRollCommandToChat() {
        MinecraftClient.getInstance().player.networkHandler.sendChatCommand("roll");
    }
}
