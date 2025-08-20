package fr.razi;

import fr.razi.db.BoxDataDao;
import fr.razi.db.DatabaseManager;
import fr.razi.db.PlayerDataDao;
import org.bukkit.plugin.java.JavaPlugin;

public class Tycoon extends JavaPlugin {

    @Override
    public void onEnable() {
	    getLogger().info("Plugin enabled.");

        DatabaseManager.init(this);

        PlayerDataDao.createTableIfNotExists();
        PlayerDataDao.createIndexesIfNotExists();

        BoxDataDao.createTableIfNotExists();
        BoxDataDao.createIndexesIfNotExists();

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

}
