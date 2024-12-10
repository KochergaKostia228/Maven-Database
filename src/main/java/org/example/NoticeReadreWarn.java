package org.example;

import java.sql.*;

public class NoticeReadreWarn {
    private static final String URL = "jdbc:postgresql://localhost:5437/test-db";
    private static final String USER = "sa";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String selectQuery = "SELECT id, message, type, processed FROM notice WHERE type = 'WARN' AND processed = false";
            String updateQuery = "UPDATE notice SET processed = true WHERE id = ?";

            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);

            while (true) {
                ResultSet rs = selectStmt.executeQuery();

                while (rs.next()) {
                    String message = rs.getString("message");
                    System.out.println("Message: " + message);

                    int id = rs.getInt("id");
                    updateStmt.setInt(1, id);
                    updateStmt.executeUpdate();
                }

                Thread.sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
