package net.flytre.slime_sling;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class SlimeSlingItem extends Item {
    public SlimeSlingItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStackIn = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.success(itemStackIn);
    }


    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof PlayerEntity))
            return;

        PlayerEntity player = (PlayerEntity) entity;
        if (!player.isOnGround())
            return;

        // copy chargeup code from bow \o/
        int i = this.getMaxUseTime(stack) - timeLeft;
        float f = i / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        f *= 4f;

        if (f > 6f)
            f = 6f;

        // check if player was targeting a block
        BlockHitResult mop = raycast(world, player, RaycastContext.FluidHandling.NONE);
        if (mop.getType() == HitResult.Type.BLOCK) {
            // we fling the inverted player look vector
            Vec3d vec = player.getRotationVector().normalize();
            player.addVelocity(vec.x * -f,
                    vec.y * -f / 3f,
                    vec.z * -f);

            if (player instanceof ServerPlayerEntity) {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                buf.writeDouble(player.getVelocity().x);
                buf.writeDouble(player.getVelocity().y);
                buf.writeDouble(player.getVelocity().z);
                buf.writeFloat(player.getYaw());
                buf.writeFloat(player.getPitch());
                ServerPlayNetworking.send((ServerPlayerEntity) player, SlimeSling.SLING_PACKET, buf);
            }

//            player.playSound(Sounds.SLIME_SLING.getSound(), 1f, 1f);
            player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1f, 1f);
            SlimeBounceHandler.addBounceHandler(player);
        }
    }

}
