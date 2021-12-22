package net.flytre.slime_sling;

import net.flytre.slime_sling.network.BouncePacket;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BootsHandler {

    public static void handleBounce(LivingEntity entity, CallbackInfo ci) {
        if (!(entity.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof SlimeBootsItem))
            return;

        boolean isClient = entity.world.isClient;

        if (!entity.isSneaking() && entity.fallDistance > 2.0 && entity.isOnGround()) {
            entity.fallDistance = 0.0F;

            if (isClient) {
                entity.setVelocity(entity.getVelocity().x, entity.getVelocity().y * -0.9, entity.getVelocity().z);
                entity.setOnGround(false);
                double f = 0.91d + 0.04d;
                // only slow down half as much when bouncing
                entity.setVelocity(entity.getVelocity().x / f, entity.getVelocity().y, entity.getVelocity().z / f);
                if (entity instanceof ClientPlayerEntity)
                    ((ClientPlayerEntity) entity).networkHandler.sendPacket(new BouncePacket());
            } else {
                ci.cancel();
            }

            entity.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1f, 1f);
            SlimeBounceHandler.addBounceHandler(entity, entity.getVelocity().y);
        } else if (!isClient && entity.isSneaking()) {
            entity.fallDistance *= 0.2;
        }
    }
}
