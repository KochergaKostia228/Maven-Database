package org.example;

import java.sql.*;

public class NoticeReaderInfo {
    private static final String URL = "jdbc:postgresql://localhost:5437/test-db";
    private static final String USER = "sa";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String selectQuery = "SELECT id, message, type, processed FROM notice WHERE type = 'INFO' AND processed = false";
            String deleteQuery = "DELETE FROM notice WHERE id = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);

            while (true) {
                ResultSet rs = selectStmt.executeQuery();

                while (rs.next()) {
                    String message = rs.getString("message");
                    System.out.println("Message: " + message);

                    int id = rs.getInt("id");
                    deleteStmt.setInt(1, id);
                    deleteStmt.executeUpdate();
                }

                Thread.sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
