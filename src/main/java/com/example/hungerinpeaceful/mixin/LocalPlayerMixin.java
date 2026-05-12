package com.example.hungerinpeaceful.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void onAiStepTail(CallbackInfo ci) {
        LocalPlayer self = (LocalPlayer)(Object)this;
        if (self.level().getDifficulty() != Difficulty.PEACEFUL) return;

        // サーバーから同期された正しいFoodLevelを維持
        // LocalPlayerはサーバーから受け取ったデータを持っているので
        // aiStepによる上書きを防ぐためここで何もしない
        // → PlayerMixinのredirectで既にブロック済み
    }
}