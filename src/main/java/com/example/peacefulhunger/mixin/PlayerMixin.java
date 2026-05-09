package com.example.peacefulhunger.mixin;

//import com.example.peacefulhunger.PeacefulHungerMod;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;setSaturation(F)V")
    )
    private void redirectSetSaturation(FoodData foodData, float saturation) {
        Player self = (Player)(Object)this;

        // クライアント側はピースフルのとき完全にブロック（サーバー・クライアント両方）
        if (self.level().getDifficulty() == Difficulty.PEACEFUL) return;
        // ピースフル以外は通常通り実行
        foodData.setSaturation(saturation);

    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;setFoodLevel(I)V")
    )
    private void redirectSetFoodLevel(FoodData foodData, int level) {
        Player self = (Player)(Object)this;
    
        // ピースフルのときは完全にブロック（サーバー・クライアント両方）
        if (self.level().getDifficulty() == Difficulty.PEACEFUL) return;
    
        // ピースフル以外は通常通り実行
        foodData.setFoodLevel(level);
    }
}