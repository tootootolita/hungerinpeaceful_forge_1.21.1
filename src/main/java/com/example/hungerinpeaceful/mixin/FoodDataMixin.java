package com.example.hungerinpeaceful.mixin;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(FoodData.class)
public class FoodDataMixin {

    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getDifficulty()Lnet/minecraft/world/Difficulty;"
        )
    )
    private Difficulty redirectGetDifficulty(net.minecraft.world.level.Level level, Player player) {
        Difficulty difficulty = level.getDifficulty();
        if (difficulty == Difficulty.PEACEFUL) {
            return Difficulty.EASY;
        }
        return difficulty;
    }


}