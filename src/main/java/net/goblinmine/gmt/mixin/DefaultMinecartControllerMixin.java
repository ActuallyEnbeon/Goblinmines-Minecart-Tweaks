package net.goblinmine.gmt.mixin;

import net.goblinmine.gmt.GoblinminesMinecartTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// TODO: add support for ExperimentalMinecartController
@Mixin(DefaultMinecartController.class)
public class DefaultMinecartControllerMixin {
    @Redirect(method = "moveOnRail", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean checkForNewPoweredRailTypes(BlockState state, Block block) {
        return state.isOf(GoblinminesMinecartTweaks.COPPER_RAIL_BLOCK) || state.isOf(block);
    }

    @Unique
    private double maxSpeed = 8.0;

    @Redirect(method = "moveOnRail", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;getMaxSpeed(Lnet/minecraft/server/world/ServerWorld;)D"))
    public double increaseMaxSpeed(AbstractMinecartEntity instance, ServerWorld world) {
        double speed = maxSpeed;

        BlockState blockState = world.getBlockState(instance.getBlockPos());
        if (blockState.isOf(Blocks.POWERED_RAIL)) {
            // speed = 24.0;
            speed = world.getGameRules().getInt(GoblinminesMinecartTweaks.POWERED_RAIL_SPEED);
        } else if (blockState.isOf(GoblinminesMinecartTweaks.COPPER_RAIL_BLOCK)) {
            // speed = 10.0;
            speed = world.getGameRules().getInt(GoblinminesMinecartTweaks.COPPER_RAIL_SPEED);
        }

        maxSpeed = speed;
        return speed / (instance.isTouchingWater() ? 40.0 : 20.0);
    }
}
