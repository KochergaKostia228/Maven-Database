package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class NoticeWriter {
    private static final String URL = "jdbc:postgresql://localhost:5437/test-db";
    private static final String USER = "sa";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            String insertQuery = "INSERT INTO notice (message, type, processed) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery);

            Random rand = new Random();
            while (true){
                String message;
                String type;
                boolean processed = false;

                if (rand.nextBoolean()) {
                    message = "Новое сообщение от " + LocalDateTime.now();
                    type = "INFO";
                } else {
                    message = "Произошла ошибка в " + LocalDateTime.now();
                    type = "WARN";
                }

                stmt.setString(1, message);
                stmt.setString(2, type);
                stmt.setBoolean(3, processed);
                stmt.executeUpdate();

                Thread.sleep(1000);
            }

        } catch (SQLException | InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
