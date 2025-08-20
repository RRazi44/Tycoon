package fr.razi.db;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static String url;

    public static void init(JavaPlugin plugin) {
        File dataDir = plugin.getDataFolder(); // .../plugins/Tycoon
        if (!dataDir.exists() && !dataDir.mkdirs()) {
            throw new RuntimeException("Failed to create plugin data folder: " + dataDir.getAbsolutePath());
        }
        File dbFile = new File(dataDir, "data/tycoon.db");
        File parent = dbFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new RuntimeException("Failed to create database folder: " + parent.getAbsolutePath());
        }
        url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
    }

    public static Connection getConnection() throws SQLException{
        if(url == null){
            throw new IllegalStateException("DatabaseManager not initialized.");
        }
        Connection connection = DriverManager.getConnection(url);
        try(Statement statement = connection.createStatement()){
            statement.execute("PRAGMA foreigns_keys = ON;");
            statement.execute("PRAGMA journal_mode = WAL;");
            statement.execute("PRAGMA synchronous = NORMAL;");
        }
        return connection;
    }

    public static String getUrl(){
        return url;
    }

}
