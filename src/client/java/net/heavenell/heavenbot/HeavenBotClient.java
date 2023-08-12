package net.heavenell.heavenbot;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.heavenell.heavenbot.Event.AttackCowCallback;
import net.heavenell.heavenbot.Event.KeyInputHandler;

import java.security.Key;

public class HeavenBotClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		AttackEntityCallback.EVENT.register(new AttackCowCallback());

		AttackCowCallback.register();
		KeyInputHandler.register();
	}
}