package tk.doraneko.test.threelayerpattern.utils;

import java.sql.*;

/**
 * @author tranphuquy19@gmail.com
 * @since 16/10/2019
 */
public class MySqlDataAccessHelper {
    public Connection connection;

    public void displayError(SQLException e) {
        System.out.println(" Error Message:" + e.getMessage());
        System.out.println(" SQL State:" + e.getSQLState());
        System.out.println(" Error Code:" + e.getErrorCode());
    }

    public void open() {
        try {
            String connectionString = "jdbc:mysql://localhost:3306/user_db";
            String user = "root";
            String password = "root@123";

            connection = DriverManager.getConnection(connectionString, user, password);
        } catch (SQLException e) {
            displayError(e);
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            displayError(e);
        }
    }

    public ResultSet excuteQuery(String cmd) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(cmd);
        } catch (SQLException e) {
            displayError(e);
        }
        return resultSet;
    }
}
