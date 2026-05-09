package com.example.peacefulhunger.mixin;

//import com.example.peacefulhunger.PeacefulHungerMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.world.Difficulty;
//import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(method = "handleSetHealth", at = @At("TAIL"))
    private void onHandleSetHealth(ClientboundSetHealthPacket packet, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        if (mc.level.getDifficulty() != Difficulty.PEACEFUL) return;

        // サーバーから受け取った正しい値を強制セット
        mc.player.getFoodData().setFoodLevel(packet.getFood());
        mc.player.getFoodData().setSaturation(packet.getSaturation());


    }

    
}