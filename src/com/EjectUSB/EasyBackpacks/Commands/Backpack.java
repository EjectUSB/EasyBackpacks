package com.EjectUSB.EasyBackpacks.Commands;

import com.EjectUSB.EasyBackpacks.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.IOException;

import static com.EjectUSB.EasyBackpacks.Utils.InventoryStringDeSerializer.fromBase64;
import static com.EjectUSB.EasyBackpacks.Utils.InventoryStringDeSerializer.toBase64;

public class Backpack implements CommandExecutor {
    private Main plugin;

    public Backpack(Main pl) {
        plugin = pl;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!p.hasPermission(plugin.EBAcess)) {
                p.sendMessage(ChatColor.RED + "You do not have permission to use /backpack!");
                return true;
            }
            YamlConfiguration pd = plugin.getData(p).getYamlConfiguration();
            if(pd.get("BackpackExisting").equals(false)) {
                Inventory backpack = Bukkit.createInventory(null, 36, p.getName() + "'s Backpack.");
                pd.set("Backpack", toBase64(backpack));
                pd.set("BackpackExisting", true);
                plugin.getData(p).saveData();
                p.openInventory(backpack);
                return true;
            }
            try {
                Inventory backpack2 = fromBase64(pd.get("Backpack").toString(), p.getName() + "'s Backpack.");
                p.openInventory(backpack2);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }


        }else {
            sender.sendMessage(ChatColor.RED + "You must be an player to execute /backpack!");
        }
        return true;
    }
}
