package net.heavenell.heavenbot.Event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AttackCowCallback implements AttackEntityCallback {
    static boolean i = false;
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (entity instanceof CowEntity && !world.isClient()) {
            i = true;
        }
        return ActionResult.PASS;
    }

    public static void  registerCowHitting(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (i) {
                // This happens when our custom key is pressed
                client.player.networkHandler.sendChatMessage("I just hit a Cow");
                i = false;
            }
        });
    }
    public static void register(){
        registerCowHitting();
    }
}
