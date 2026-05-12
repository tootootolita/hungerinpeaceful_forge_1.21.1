package com.example.peacefulhunger;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PeacefulHungerMod.MODID)
public class PeacefulHungerMod {

    public static final String MODID = "peacefulhunger";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PeacefulHungerMod(FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // サーバー側・tickの終わりのみ処理
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (player.level().getDifficulty() != Difficulty.PEACEFUL) return;

        FoodData foodData = player.getFoodData();

        // 消耗を加算
        foodData.addExhaustion(0.005F);//0.005F

        // Saturationが0のときFoodLevelを削る処理
        float exhaustion = foodData.getExhaustionLevel();
        if (exhaustion >= 4.0F) {
            float saturation = foodData.getSaturationLevel();
            if (saturation > 0.0F) {
                foodData.setSaturation(Math.max(0.0F, saturation - 1.0F));
            } else if (foodData.getFoodLevel() > 1) {
                foodData.setFoodLevel(foodData.getFoodLevel() - 1);
            }
        }
    }
}