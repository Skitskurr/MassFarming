package com.versuchdrei.massfarming;
 
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * the main class of mass farming
 * @author VersuchDrei
 * @version 1.0
 */
public class Main extends JavaPlugin {
	 
   @Override
   public void onEnable() {
	   saveDefaultConfig();
	   Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
   }
   
}