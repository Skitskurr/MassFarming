package com.skitskurr.massfarming;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.skitskurr.massfarming.utils.HelixIterator;
import com.skitskurr.massfarming.utils.ItemUtils;
import com.skitskurr.massfarming.utils.MaterialTag;

public class EventListener implements Listener {
	
	private static final String NAMESPACED_KEY = "skitskurr_massfarming";
	
	private static final String CONFIG_KEY_USE_PERMISSIONS = "usePermissions";
	private static final String CONFIG_KEY_REDUCE_DURABILITY = "reduceDurability";
	
	private static final String PERMISSION_MASS_FARMING = "skitskurr.massfarming";
	
	private final Main plugin;
	private final boolean usePermissions;
	private final boolean reduceDurability;
	private final Tag<Material> tagHoes;
	private final Tag<Material> tagSeeds;
	private final Map<Material, Plant> plants = new HashMap<>();
  
	public EventListener(final Main plugin) {
		this.plugin = plugin;
    
		final FileConfiguration config = plugin.getConfig();
		this.usePermissions = config.getBoolean(EventListener.CONFIG_KEY_USE_PERMISSIONS);
		this.reduceDurability = config.getBoolean(EventListener.CONFIG_KEY_REDUCE_DURABILITY);
   
		final NamespacedKey key = new NamespacedKey(this.plugin, EventListener.NAMESPACED_KEY);
		this.tagHoes = new MaterialTag(key, new Material[] { Material.WOODEN_HOE, Material.STONE_HOE,
				Material.IRON_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE });
   
		this.plants.put(Material.WHEAT_SEEDS, new Plant(Material.WHEAT));
		this.plants.put(Material.POTATO, new Plant(Material.POTATOES));
		this.plants.put(Material.CARROT, new Plant(Material.CARROTS));
		this.plants.put(Material.BEETROOT_SEEDS, new Plant(Material.BEETROOTS));
		this.plants.put(Material.MELON_SEEDS, new Plant(Material.MELON_STEM, true));
		this.plants.put(Material.PUMPKIN_SEEDS, new Plant(Material.PUMPKIN_STEM, true));
		

		this.tagSeeds = new MaterialTag(key, this.plants.keySet().toArray(new Material[0]));
	}  
	
	@EventHandler
	public void onInteract(final PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
   
		final Block block = event.getClickedBlock();
		if (block.getType() != Material.FARMLAND) {
			return;
		}
	   
		final Player player = event.getPlayer();
		if (!permissionCheck(player)) {
			return;
		}
 
		final PlayerInventory inventory = player.getInventory();
  
		final ItemStack tool = inventory.getItemInOffHand();
		if (!this.tagHoes.isTagged(tool.getType())) {
			return;
		}
   
		final ItemStack seed = inventory.getItemInMainHand();
		final Material seedType = seed.getType();
		if(!this.tagSeeds.isTagged(seedType)) {
			return;
		}
  
		final Plant plantResult = this.plants.get(seedType);
		final Material plantType = plantResult.getResult();
		final boolean onlyOther = plantResult.onlyOther();
		
		final HelixIterator helixIterator = new HelixIterator(block, 3);
		int amount = seed.getAmount();
		boolean other = false;
		
		while (helixIterator.hasNext() && amount > 1 && tool.getType() != Material.AIR) {
			final Block soil = helixIterator.next();
			
			if(onlyOther && (other = !other)) {
				continue;
			}
			
			if (soil.getType() != Material.FARMLAND) {
				continue;
			}
  
			final Block plant = soil.getRelative(BlockFace.UP);
			if (plant.getType() != Material.AIR) {
				continue;
			}
      
			// TODO: block place event to check if the player is allowed to build there
			
			plant.setType(plantType);
      
			seed.setAmount(--amount);
			inventory.setItemInMainHand(seed);
			if (this.reduceDurability) {
				ItemUtils.reduceDurability(tool);
				inventory.setItemInOffHand(tool);
			} 
		} 
	}
	
	private boolean permissionCheck(final Player player) {
		return !this.usePermissions || player.hasPermission(EventListener.PERMISSION_MASS_FARMING);
	}
}