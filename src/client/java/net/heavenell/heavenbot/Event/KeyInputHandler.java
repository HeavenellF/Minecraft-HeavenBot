package net.heavenell.heavenbot.Event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.NetworkEncryptionUtils;
import net.minecraft.network.message.LastSeenMessagesCollector;
import net.minecraft.network.message.MessageBody;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
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
                EntityType.COW.spawn(client.getServer().getOverworld(), null,null,client.player.getBlockPos(), SpawnReason.TRIGGERED, true, true);
//                client.player.sendAbilitiesUpdate();
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