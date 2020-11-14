package com.versuchdrei.massfarming.utils;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * a util class for reusable itemstack regarding methods
 * @author VersuchDrei
 * @version 1.0
 */
public class ItemUtils {
	
	private static Random random = new Random();
	
	public static void reduceDurability(final ItemStack item) {
		final ItemMeta meta = item.getItemMeta();
		if(!(meta instanceof Damageable)) {
			return;
		}
		
		// simulate unbreaking enchantment
		if(ItemUtils.random.nextInt(meta.getEnchantLevel(Enchantment.DURABILITY) + 1) != 0) {
			return;
		}
		
		final Damageable damageable = (Damageable) meta;
		final int newDamage = damageable.getDamage() + 1;
		
		if(newDamage >= item.getType().getMaxDurability()) {
			item.setType(Material.AIR);
		} else {
			damageable.setDamage(newDamage);
			item.setItemMeta(meta);
		}
	}

}
