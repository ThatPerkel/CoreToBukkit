package org.ships.implementation.bukkit;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.Optional;

public interface VaultService {

    static Optional<Double> getBalance(OfflinePlayer user){
        Optional<Economy> opEco = getEconomy();
        if(!opEco.isPresent()){
            return Optional.empty();
        }
        return Optional.of(opEco.get().getBalance(user));
    }

    static void setBalance(OfflinePlayer user, BigDecimal price){
        setBalance(user, price.doubleValue());
    }

    static void setBalance(OfflinePlayer user, double price){
        Optional<Economy> opEco = getEconomy();
        if(!opEco.isPresent()){
            return;
        }
        opEco.get().depositPlayer(user, (-opEco.get().getBalance(user)) + price);
    }

    static Optional<Economy> getEconomy(){
        if(Bukkit.getPluginManager().getPlugin("vault") == null){
            return Optional.empty();
        }
        return Optional.of(Bukkit.getServicesManager().getRegistration(Economy.class).getProvider());
    }
}
