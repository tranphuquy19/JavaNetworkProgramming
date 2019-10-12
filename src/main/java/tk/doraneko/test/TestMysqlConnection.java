package tk.doraneko.test;

import tk.doraneko.commons.ConsoleColors;

import java.sql.*;

/**
 * Created by @tranphuquy19 on 13/10/2019
 * Email:       tranphuquy19@gmail.com
 */
public class TestMysqlConnection {
    public static void main(String[] args) throws SQLException {
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        String connectionString = "jdbc:mysql://localhost:3306/test_connection";
        String user = "root";
        String password = "root@123";

        connection = DriverManager.getConnection(connectionString, user, password);
        System.out.println(ConsoleColors.RED_BOLD + "Connect to DB successfully" + ConsoleColors.RESET);
        statement = connection.createStatement();

        resultSet = statement.executeQuery("SELECT VERSION()"); //lấy version của DB

        if (resultSet.next()) {
            System.out.println(ConsoleColors.BLUE_BOLD + "DB Ver: " + resultSet.getString(1) + ConsoleColors.RESET);
        }
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
