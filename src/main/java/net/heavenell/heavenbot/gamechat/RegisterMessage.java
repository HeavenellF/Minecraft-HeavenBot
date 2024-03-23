package net.heavenell.heavenbot.gamechat;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class RegisterMessage {
    public static void registerMessageOutput(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            AutoResponse.sendchat(client);
            AutoAccept.sendchat(client);
            AutoGreeting.sendchat(client);
            AutoFarewell.sendchat(client);
        });
    }
}
