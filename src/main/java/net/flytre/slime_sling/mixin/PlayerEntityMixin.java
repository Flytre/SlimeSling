package net.flytre.slime_sling.mixin;


import net.flytre.slime_sling.SlimeBounceHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @SuppressWarnings("ConstantConditions")
    @Inject(method="tick", at = @At("TAIL"))
    public void slime_sling$bounce(CallbackInfo ci) {
        PlayerEntity me = (PlayerEntity)(Object) this;
        for(Entity entity : SlimeBounceHandler.BOUNCING_ENTITIES.keySet()) {
            if(entity == me) {
                if(SlimeBounceHandler.BOUNCING_ENTITIES.get(entity).tickMixin(me))
                    break;
            }
        }
    }

}
