package net.flytre.slime_sling;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.IdentityHashMap;

public class SlimeBounceHandler {

    public static final IdentityHashMap<Entity, SlimeBounceHandler> BOUNCING_ENTITIES = new IdentityHashMap<>();


    public final LivingEntity entity;
    private int timer;
    private boolean wasInAir;
    private double bounce;
    private int bounceTick;

    private double lastXVelocity;
    private double lastZVelocity;

    public SlimeBounceHandler(LivingEntity entity, double bounce) {
        this.entity = entity;
        this.timer = 0;
        this.wasInAir = false;
        this.bounce = bounce;

        this.bounceTick = bounce != 0 ? entity.age : 0;
        BOUNCING_ENTITIES.put(entity, this);
    }

    public static void addBounceHandler(LivingEntity entity) {
        addBounceHandler(entity, 0d);
    }

    public static void addBounceHandler(LivingEntity entity, double bounce) {
        // only supports actual players as it uses the PlayerTick event
        if (!(entity instanceof PlayerEntity))
            return;

        SlimeBounceHandler handler = BOUNCING_ENTITIES.get(entity);
        if (handler == null) {
            new SlimeBounceHandler(entity, bounce);
        } else if (bounce != 0) {
            // updated bounce if needed
            handler.bounce = bounce;
            handler.bounceTick = entity.age;
        }
    }

    public boolean tickMixin(PlayerEntity player) {
        if (player == this.entity && !player.isFallFlying()) {
            if (player.age == this.bounceTick) {
                Vec3d vec3d = player.getVelocity();
                player.setVelocity(vec3d.x, this.bounce, vec3d.z);
                this.bounceTick = 0;
            }
            // preserve motion
            if (!this.entity.isOnGround() && this.entity.age != this.bounceTick) {
                if (this.lastXVelocity != this.entity.getVelocity().x || this.lastZVelocity != this.entity.getVelocity().z) {
                    double f = 0.91d + 0.025d;
                    //System.out.println((entityLiving.worldObj.isRemote ? "client: " : "server: ") + entityLiving.motionX);
                    Vec3d vec3d = this.entity.getVelocity();
                    player.setVelocity(vec3d.x / f, vec3d.y, vec3d.z / f);
                    this.entity.setOnGround(false);
                    this.lastXVelocity = this.entity.getVelocity().x;
                    this.lastZVelocity = this.entity.getVelocity().z;
                }
            }

            // timing the effect out
            if (this.wasInAir && this.entity.isOnGround()) {

                if (this.timer == 0) {
                    this.timer = this.entity.age;
                } else if (this.entity.age - this.timer > 5) {
                    BOUNCING_ENTITIES.remove(this.entity);
                    return true;
                }
            } else {
                this.timer = 0;
                this.wasInAir = true;
            }
        }
        return false;
    }
}
