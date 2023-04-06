package com.epherical.numiscurrency;

import com.epherical.octoecon.api.event.EconomyEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class NumismaticCurrency implements ModInitializer {

    public static final ResourceLocation COINS = new ResourceLocation("numismatic-overhaul", "currency");
    public static Coins COINS_CURRENCY;

    @Override
    public void onInitialize() {
        NumismaticBalanceProvider provider = new NumismaticBalanceProvider();
        COINS_CURRENCY = new Coins(provider);
        ServerLifecycleEvents.SERVER_STARTING.register(provider::setServer);

        EconomyEvents.CURRENCY_ADD_EVENT.register(() -> {
            return List.of(COINS_CURRENCY);
        });
    }
}
