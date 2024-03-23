package net.heavenell.heavenbot.gamechat;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.heavenell.heavenbot.setting.HeavenBotSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class AutoAccept implements ClientReceiveMessageEvents.Game{
    private static final HeavenBotSetting setting = HeavenBotSetting.getInstance();
    private static boolean response = false;
    @Override
    public void onReceiveGameMessage(Text message, boolean overlay) {
        String messageString = Text.Serialization.toJsonString(message).toLowerCase();
        if (messageString.contains("has sent a request to teleport to you") && setting.isAutoAccept()) {
            response = true;
        }
    }

    public static void sendchat(MinecraftClient client) {
        if (response) {
            client.player.networkHandler.sendChatCommand("tpaccept");
            response = false;
        }
    }
}
