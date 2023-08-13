package net.heavenell.heavenbot;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.heavenell.heavenbot.Event.AttackCowCallback;
import net.heavenell.heavenbot.Event.KeyInputHandler;
import net.heavenell.heavenbot.gamechat.AutoAccept;
import net.heavenell.heavenbot.gamechat.AutoResponse;

public class HeavenBotClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		KeyInputHandler.register();

		AttackEntityCallback.EVENT.register(new AttackCowCallback());

		ClientReceiveMessageEvents.CHAT.register(new AutoResponse());
		ClientReceiveMessageEvents.GAME.register(new AutoAccept());

		AutoResponse.autoresponse();
		AutoResponse.loadResponses();

		AutoAccept.autoresponse();
		AttackCowCallback.CowHitting();
	}
}