package com.epherical.numiscurrency;

import com.epherical.octoecon.api.BalanceProvider;
import com.epherical.octoecon.api.VirtualCurrency;
import net.minecraft.network.chat.Component;

public class Coins implements VirtualCurrency {

    private BalanceProvider provider;

    public Coins(BalanceProvider provider) {
        this.provider = provider;
    }

    @Override
    public Component getCurrencySingularName() {
        return Component.nullToEmpty("Coin");
    }

    @Override
    public Component getCurrencyPluralName() {
        return Component.nullToEmpty("Coins");
    }

    @Override
    public Component getCurrencySymbol() {
        return Component.nullToEmpty("§");
    }

    @Override
    public Component format(double value) {
        return Component.nullToEmpty(String.valueOf(value));
    }

    @Override
    public Component format(double value, int decimalPlaces) {
        return Component.nullToEmpty(String.valueOf(value));
    }

    @Override
    public BalanceProvider balanceProvider() {
        return provider;
    }

    public void setProvider(BalanceProvider provider) {
        this.provider = provider;
    }

    @Override
    public String getIdentity() {
        return NumismaticCurrency.COINS.toString();
    }
}
