package com.EjectUSB.EasyBackpacks;

import com.EjectUSB.EasyBackpacks.Commands.Backpack;
import com.EjectUSB.EasyBackpacks.Listeners.Listeners;
import com.EjectUSB.EasyBackpacks.Utils.Playerdata;
import com.sun.istack.internal.NotNull;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    private final Map<UUID, Playerdata> playerDataMap = new HashMap<>();
    private File playerDataFolder;

    public Permission EBAcess = new Permission("easybackpacks.access");

    @NotNull
    public Map<UUID, Playerdata> getPlayerDataMap() {
        return playerDataMap;
    }

    @Override
    public void onEnable() {
        createPlayerDataDir();
        getServer().getPluginManager().registerEvents(new Listeners(this), this);
        getCommand("backpack").setExecutor(new Backpack(this));
        getLogger().log(Level.INFO, "Plugin made by EjectUSB. Also known as gamingforit and Merlin");
    }
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin made by EjectUSB. Also known as gamingforit and Merlin");
    }

    public void createPlayerDataDir(){
        playerDataFolder = new File(getDataFolder(),"playerdata");
        playerDataFolder.mkdirs();
    }

    @NotNull
    private Playerdata createPlayerData(Player player){
        UUID playerUUID = player.getUniqueId();
        Playerdata playerData = new Playerdata(playerUUID, new File(playerDataFolder, playerUUID.toString()+".yml"));
        if (!playerData.isLoaded())
            throw new InstantiationError(String.format("Couldn't load data of player '%s'", playerUUID.toString()));
        return playerData;
    }


    public void loadPlayerData(Player player){
        playerDataMap.put(player.getUniqueId(), createPlayerData(player));
    }

    @NotNull
    public Playerdata getData(Player player){
        return playerDataMap.computeIfAbsent(player.getUniqueId(), data -> createPlayerData(player));
    }
}