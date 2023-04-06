package com.epherical.numiscurrency;

import com.epherical.octoecon.api.BalanceProvider;
import com.epherical.octoecon.api.Currency;
import com.epherical.octoecon.api.transaction.Transaction;
import com.epherical.octoecon.api.user.UniqueUser;
import com.epherical.octoecon.api.user.User;
import com.glisco.numismaticoverhaul.ModComponents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class NumismaticBalanceProvider implements BalanceProvider {

    private MinecraftServer server;

    @Override
    public double getBalance(User user) {
        if (user instanceof UniqueUser uniqueUser) {
            ServerPlayer player = server.getPlayerList().getPlayer(uniqueUser.getUserID());
            if (player != null)  {
                return ModComponents.CURRENCY.get(player).getValue();
            }
        }
        return 0;
    }

    @Override
    public Transaction setBalance(User user, double v, Currency currency) {
        if (user instanceof UniqueUser uniqueUser) {
            ServerPlayer player = server.getPlayerList().getPlayer(uniqueUser.getUserID());
            if (player != null)  {
                ModComponents.CURRENCY.get(player).setValue((long) v);
                return new NumismaticTransaction(v, currency, user, "Set virtual currency balance", Transaction.Response.SUCCESS, Transaction.Type.SET);
            }
        }
        return new NumismaticTransaction(0, currency, user, "Failed Set balance, not a UniqueUser", Transaction.Response.FAIL, Transaction.Type.SET);
    }

    @Override
    public Transaction sendTo(User from, User to, double v, Currency currency) {
        if (from instanceof UniqueUser from1 && to instanceof UniqueUser to1) {
            Transaction transaction = from1.withdrawMoney(currency, v, "Sending money from " + from1.getIdentity() + " to " + from.getIdentity() + ".");
            from.depositMoney(currency, v, to.getIdentity() + " received money from " + from.getIdentity() + ".");
            return transaction;
        }
        return new NumismaticTransaction(0, currency, from, "Failed Set balance, not a UniqueUser", Transaction.Response.FAIL, Transaction.Type.SET);
    }

    @Override
    public Transaction deposit(User user, double v, String s, Currency currency) {
        if (user instanceof UniqueUser uniqueUser) {
            ServerPlayer player = server.getPlayerList().getPlayer(uniqueUser.getUserID());
            if (player != null)  {
                ModComponents.CURRENCY.get(player).modify((long) v);
                return new NumismaticTransaction(v, currency, user, s, Transaction.Response.SUCCESS, Transaction.Type.DEPOSIT);
            }
        }
        return new NumismaticTransaction(0, currency, user, "Failed deposit, not a UniqueUser", Transaction.Response.FAIL, Transaction.Type.DEPOSIT);
    }

    @Override
    public Transaction withdraw(User user, double v, String s, Currency currency) {
        if (user instanceof UniqueUser uniqueUser) {
            ServerPlayer player = server.getPlayerList().getPlayer(uniqueUser.getUserID());
            if (player != null)  {
                ModComponents.CURRENCY.get(player).modify((long) -v);
                return new NumismaticTransaction(v, currency, user, s, Transaction.Response.SUCCESS, Transaction.Type.WITHDRAW);
            }
        }
        return new NumismaticTransaction(0, currency, user, "Failed withdraw, not a UniqueUser", Transaction.Response.FAIL, Transaction.Type.WITHDRAW);
    }


    public void setServer(MinecraftServer server) {
        this.server = server;
    }
}
