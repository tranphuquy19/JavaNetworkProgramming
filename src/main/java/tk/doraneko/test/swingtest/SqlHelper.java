package tk.doraneko.test.swingtest;

import tk.doraneko.commons.ConsoleColors;

import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tranphuquy19@gmail.com
 * @since 16/10/2019
 */
public class SqlHelper {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private String connectionString = "jdbc:mysql://localhost:3306/user_db";
    private String user = "root";
    private String password = "root@123";

    private int columnCount;

    public SqlHelper() {
        openConnection();
    }

    public SqlHelper(String connectionString) {
        this.connectionString = connectionString;
        openConnection();
    }

    private void openConnection() {
        try {
            connection = DriverManager.getConnection(connectionString, user, password);
            System.out.println(ConsoleColors.RED_BOLD + "Connect to DB successfully" + ConsoleColors.RESET);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Connect to DB false" + ConsoleColors.RESET);
            e.printStackTrace();
        }
    }

    public String[] getTiles(String sql) {
        String[] titles = null;
        try {
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            columnCount = resultSetMetaData.getColumnCount();
            titles = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                titles[i - 1] = resultSetMetaData.getColumnName(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(titles.toString());
        return titles;
    }

    public Object[][] getRows(String sql) {
        ArrayList<Object[]> rowResult = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Object[] cells = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    cells[i - 1] = resultSet.getObject(i);
                }
                rowResult.add(cells);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] rows = new Object[rowResult.size()][];
        for (int i = 0; i < rowResult.size(); i++) {
            Object[] row = rowResult.get(i);
            rows[i] = row;
            System.out.println(row);
        }
        return rows;
    }
}
