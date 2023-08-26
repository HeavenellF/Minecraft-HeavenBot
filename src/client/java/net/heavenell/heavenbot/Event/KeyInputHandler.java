package net.heavenell.heavenbot.Event;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.GameModeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class KeyInputHandler{
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.tutorialmod.tutorial";
    public static final String KEY_CATEGORY_TUTORIAL2 = "key.category.tutorialmod.tutorial2";
    public static final String KEY_DRINK_WATER = "key.tutorialmod.drink_water";
    public static final String KEY_DRINK_WATER2 = "key.tutorialmod.drink_water2";


    public static KeyBinding spectatorKey;
    public static KeyBinding survivalKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(spectatorKey.wasPressed()) {
                // This happens when our custom key is pressed
                client.player.networkHandler.sendChatMessage("/roll");
//                mc.interactionManager.setGameModes(GameMode.SURVIVAL, GameMode.CREATIVE);
//                client.interactionManager.setGameMode(GameMode.SURVIVAL);
//                client.player.sendAbilitiesUpdate();
            }
            else if (survivalKey.wasPressed()) {
//                PlayerAbilities playerAbilities = client.player.getAbilities();
//                playerAbilities.setFlySpeed(1f);
//                mc.interactionManager.setGameModes(GameMode.CREATIVE, GameMode.SURVIVAL);
//                client.interactionManager.setGameMode(GameMode.CREATIVE);
//                client.player.getAbilities().flying = true;
//                client.player.getAbilities().allowFlying = true;
//               client.player.getAbilities().invulnerable = true;
//                MinecraftClient.getInstance().player.networkHandler.sendPacket(new UpdatePlayerAbilitiesC2SPacket(MinecraftClient.getInstance().player.getAbilities()));
                MinecraftClient.getInstance().player.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(MinecraftClient.getInstance().player.getAbilities()));
            }
        });
    }
    public static void register() {
        spectatorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DRINK_WATER,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_TUTORIAL
        ));
        survivalKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DRINK_WATER2,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                KEY_CATEGORY_TUTORIAL2
        ));

        registerKeyInputs();
    }

}