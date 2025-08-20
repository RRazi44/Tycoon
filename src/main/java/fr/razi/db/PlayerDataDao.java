package fr.razi.db;

import fr.razi.data.PlayerData;

import java.sql.*;

import static fr.razi.db.DatabaseManager.getConnection;

public class PlayerDataDao {

    public static void createTableIfNotExists() {
        String sql =
                "CREATE TABLE IF NOT EXISTS player_data (" +
                        "  id TEXT PRIMARY KEY," +
                        "  box_id TEXT," +
                        "  rank TEXT NOT NULL," +
                        "  balance INTEGER NOT NULL," +
                        "  earning INTEGER NOT NULL," +
                        "  level INTEGER NOT NULL," +
                        "  FOREIGN KEY (box_id) REFERENCES boxes_data(id)" +
                        ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("[PlayerDataDao] createTableIfNotExists: " + e.getMessage());
        }
    }

    public static void createIndexesIfNotExists() {
        String idxBox = "CREATE INDEX IF NOT EXISTS idx_player_data_box_id ON player_data(box_id);";
        String idxRank = "CREATE INDEX IF NOT EXISTS idx_player_data_rank ON player_data(rank);";
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute(idxBox);
            st.execute(idxRank);
        } catch (SQLException e) {
            System.err.println("[PlayerDataDao] createIndexesIfNotExists: " + e.getMessage());
        }
    }

    public static void insert(String id, String boxId, String rank, long balance, long earning, int level) {
        String sql = "INSERT INTO player_data (id, box_id, rank, balance, earning, level) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            if (boxId == null) ps.setNull(2, Types.VARCHAR); else ps.setString(2, boxId);
            ps.setString(3, rank);
            ps.setLong(4, balance);
            ps.setLong(5, earning);
            ps.setInt(6, level);
            ps.executeUpdate();
            System.out.println("[PlayerDataDao] Inserted player: " + id);
        } catch (SQLException e) {
            System.err.println("[PlayerDataDao] insert: " + e.getMessage());
        }
    }

    public static void insert(PlayerData data) {
        insert(
                data.id().toString(),
                data.boxId() != null ? data.boxId().toString() : null,
                data.rank(),
                data.balance(),
                data.earning(),
                data.level()
        );
    }

    public static PlayerData findById(String id) {
        String sql = "SELECT id, box_id, rank, balance, earning, level FROM player_data WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new PlayerData(
                            java.util.UUID.fromString(rs.getString("id")),
                            rs.getString("box_id") != null ? java.util.UUID.fromString(rs.getString("box_id")) : null,
                            rs.getString("rank"),
                            rs.getLong("balance"),
                            rs.getLong("earning"),
                            rs.getInt("level")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[PlayerDataDao] findById: " + e.getMessage());
        }
        return null;
    }



}
