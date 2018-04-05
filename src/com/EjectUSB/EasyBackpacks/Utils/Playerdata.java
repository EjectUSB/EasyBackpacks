package com.EjectUSB.EasyBackpacks.Utils;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Playerdata {
    @NotNull private UUID playerUUID;
    @NotNull private File dataFile;

    @Nullable
    private static YamlConfiguration yamlConfiguration;

    public Playerdata(@NotNull UUID playerUUID, @NotNull File dataFile) {
        this.playerUUID = playerUUID;
        this.dataFile = dataFile;
        setupYaml();
    }

    private void setupYaml(){
        yamlConfiguration = YamlConfiguration.loadConfiguration(dataFile);
        if (!dataFile.exists()) {
            yamlConfiguration.set("Player.uuid", playerUUID.toString());
            yamlConfiguration.set("BackpackExisting", false);
            saveData();
        }
    }

    public boolean isLoaded(){
        return yamlConfiguration != null;
    }

    public void saveData() {
        try {
            yamlConfiguration.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}