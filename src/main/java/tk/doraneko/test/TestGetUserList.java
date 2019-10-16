package tk.doraneko.test;

import tk.doraneko.commons.ConsoleColors;

import java.sql.*;

/**
 * @author tranphuquy19@gmail.com
 * @since 16/10/2019
 */
public class TestGetUserList {
    public static void main(String[] args) throws SQLException {
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        String connectionString = "jdbc:mysql://localhost:3306/user_db";
        String user = "root";
        String password = "root@123";

        connection = DriverManager.getConnection(connectionString, user, password);
        System.out.println(ConsoleColors.RED_BOLD + "Connect to DB successfully" + ConsoleColors.RESET);
        statement = connection.createStatement();

        resultSet = statement.executeQuery("SELECT * FROM user_tb"); //lấy version của DB
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        int colNum = resultSetMetaData.getColumnCount();

        while (resultSet.next()) {
//            String resultString = resultSet.getInt("id") + " | " + resultSet.getString("name") + " | " + resultSet.getString("address") + " | " + resultSet.getInt("total");
//            System.out.println(resultString);
            for (int i = 1; i <= colNum; i++) {
                System.out.print(resultSet.getObject(i) + " ");
            }
            System.out.println("");
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

