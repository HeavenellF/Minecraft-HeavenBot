package net.heavenell.heavenbot;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.heavenell.heavenbot.Event.AttackCowCallback;
import net.heavenell.heavenbot.Event.KeyInputHandler;
import net.heavenell.heavenbot.Event.OnDeathAutoRespawn;
import net.heavenell.heavenbot.Event.RegisterKey;
import net.heavenell.heavenbot.gamechat.*;
import net.heavenell.heavenbot.thread.threadCaller;

public class HeavenBotClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		KeyInputHandler.register();
		RegisterKey.register();

		AttackEntityCallback.EVENT.register(new AttackCowCallback());

		ClientReceiveMessageEvents.CHAT.register(new AutoResponse());
		ClientReceiveMessageEvents.CHAT.register(new AutoFarewell());
		ClientReceiveMessageEvents.CHAT.register(new ReviveOnCall());
		ClientReceiveMessageEvents.GAME.register(new AutoAccept());
		ClientReceiveMessageEvents.GAME.register(new AutoGreeting());

		AutoResponse.loadResponses();

		AutoGreeting.loadGreetings();

		AutoAccept.sendchat();
		AttackCowCallback.CowHitting();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			AutoRoll.roll();
			OnDeathAutoRespawn.onDeath();
		});

		ClientReceiveMessageEvents.CHAT.register(new threadCaller());
	}
}