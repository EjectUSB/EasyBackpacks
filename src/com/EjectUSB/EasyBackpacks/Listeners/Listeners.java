package com.EjectUSB.EasyBackpacks.Listeners;

import com.EjectUSB.EasyBackpacks.Main;
import com.EjectUSB.EasyBackpacks.Utils.InventoryStringDeSerializer;
import com.sun.istack.internal.NotNull;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class Listeners implements Listener {
    @NotNull
    private Main main;

    public Listeners(@NotNull Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        main.loadPlayerData(event.getPlayer());
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        YamlConfiguration pd = main.getData(p).getYamlConfiguration();
        Inventory inv = e.getInventory();
        if(!inv.getTitle().equalsIgnoreCase(p.getName() + "'s Backpack.")) {
            return;
        }
        pd.set("Backpack", InventoryStringDeSerializer.toBase64(inv));
        main.getData(p).saveData();
        return;
    }
}
