package fr.razi.db;

import fr.razi.data.BoxData;
import fr.razi.data.LocationData;

import static fr.razi.db.DatabaseManager.getConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoxDataDao {

    public static void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS boxes_data ("
                + " id TEXT PRIMARY KEY,"
                + " owner_id TEXT NOT NULL,"
                + " spawn_world TEXT NOT NULL,"
                + " spawn_x REAL NOT NULL,"
                + " spawn_y REAL NOT NULL,"
                + " spawn_z REAL NOT NULL,"
                + " spawn_yaw REAL NOT NULL,"
                + " spawn_pitch REAL NOT NULL,"
                + " size INTEGER NOT NULL,"
                + " total_money INTEGER NOT NULL,"
                + " earnings INTEGER NOT NULL,"
                + " FOREIGN KEY (owner_id) REFERENCES player_data(id)"
                + ");";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("[BoxDataDao] createTableIfNotExists: " + e.getMessage());
        }
    }

    public static void createIndexesIfNotExists() {
        String idxOwner = "CREATE INDEX IF NOT EXISTS idx_boxes_owner_id ON boxes_data(owner_id);";
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute(idxOwner);
        } catch (SQLException e) {
            System.err.println("[BoxDataDao] createIndexesIfNotExists: " + e.getMessage());
        }
    }

    public static void insert(String id,
                              String ownerId,
                              String spawnWorld,
                              double spawnX, double spawnY, double spawnZ,
                              float spawnYaw, float spawnPitch,
                              int size,
                              long totalMoney,
                              long earnings) {

        String sql = "INSERT INTO boxes_data "
                + "(id, owner_id, spawn_world, spawn_x, spawn_y, spawn_z, spawn_yaw, spawn_pitch, size, total_money, earnings) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, ownerId);
            pstmt.setString(3, spawnWorld);
            pstmt.setDouble(4, spawnX);
            pstmt.setDouble(5, spawnY);
            pstmt.setDouble(6, spawnZ);
            pstmt.setFloat(7, spawnYaw);
            pstmt.setFloat(8, spawnPitch);
            pstmt.setInt(9, size);
            pstmt.setLong(10, totalMoney);
            pstmt.setLong(11, earnings);

            pstmt.executeUpdate();
            System.out.println("[BoxDataDao] Inserted box: " + id);

        } catch (SQLException e) {
            System.err.println("[BoxDataDao] insert: " + e.getMessage());
        }
    }

    public static void insert(BoxData box) {
        LocationData s = box.spawnLocation();
        insert(
                box.id().toString(),
                box.ownerId().toString(),
                s.worldName(),
                s.x(), s.y(), s.z(),
                s.yaw(), s.pitch(),
                box.size(),
                box.totalMoney(),
                box.earnings()
        );
    }

    public static BoxData findById(String id) {
        String sql = "SELECT id, owner_id, spawn_world, spawn_x, spawn_y, spawn_z, "
                + "spawn_yaw, spawn_pitch, size, total_money, earnings "
                + "FROM boxes_data WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    LocationData spawn = new LocationData(
                            rs.getDouble("spawn_x"),
                            rs.getDouble("spawn_y"),
                            rs.getDouble("spawn_z"),
                            rs.getString("spawn_world"),
                            rs.getFloat("spawn_yaw"),
                            rs.getFloat("spawn_pitch")
                    );
                    return new BoxData(
                            java.util.UUID.fromString(rs.getString("id")),
                            java.util.UUID.fromString(rs.getString("owner_id")),
                            spawn,
                            rs.getInt("size"),
                            rs.getLong("total_money"),
                            rs.getLong("earnings")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[BoxDataDao] findById: " + e.getMessage());
        }
        return null;
    }

}
