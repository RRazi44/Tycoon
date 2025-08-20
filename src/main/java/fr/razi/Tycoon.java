package fr.razi;

import org.bukkit.plugin.java.JavaPlugin;

public class Tycoon extends JavaPlugin {

    @Override
    public void onEnable() {
	    getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {

        getLogger().info("Plugin disabled.");
    }

}
