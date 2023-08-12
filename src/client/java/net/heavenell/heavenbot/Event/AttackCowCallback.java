package net.heavenell.heavenbot.Event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AttackCowCallback implements AttackEntityCallback {
    static boolean SheepHitBool = false;
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (entity instanceof SheepEntity) {
            SheepHitBool = true;
            player.sendMessage(Text.literal("You just hit a Cow!"));
        }
        return ActionResult.PASS;
    }

    public static void  CowHitting(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (SheepHitBool) {
                // This happens when our custom key is pressed
                client.player.networkHandler.sendChatMessage("I just hit a Cow!");
                SheepHitBool = false;
            }
        });
    }
}
