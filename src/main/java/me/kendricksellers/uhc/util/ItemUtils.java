package me.kendricksellers.uhc.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class ItemUtils {

    public static ItemStack createItemStack(Material material, int quantity, String name, String... lore) {
        ItemStack itemStack = new ItemStack(material, quantity);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int quantity, short damage, String name, String... lore) {
        ItemStack itemStack = new ItemStack(material, quantity, damage);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static void updateGUILore(ItemStack itemStack, boolean enabled) {
        ItemMeta meta = itemStack.getItemMeta();
        String lore = "";
        if (enabled) {
            lore = ChatColor.GREEN + "Enabled";
            meta.setLore(Collections.singletonList(lore));
        } else {
            lore = ChatColor.RED + "Disabled";
            meta.setLore(Collections.singletonList(lore));
        }
        itemStack.setItemMeta(meta);
    }
}
