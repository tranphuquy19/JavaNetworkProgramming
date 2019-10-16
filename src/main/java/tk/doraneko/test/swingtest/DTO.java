package tk.doraneko.test.swingtest;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tranphuquy19@gmail.com
 * @since 17/10/2019
 */
public class DTO {
    private Object[] titles;
    private Object[][] rows;

    private ResultSetMetaData resultSetMetaData;
    private ResultSet resultSet;

    public DTO(ResultSetMetaData resultSetMetaData, ResultSet resultSet) {
        this.resultSet = resultSet;
        this.resultSetMetaData = resultSetMetaData;
    }

    public Object[] getTitles() {
        Object[] titles = null;
//        try {
//            titles = new Object[resultSetMetaData.getColumnCount()];
//            for (int i = 1; i < titles.length; i++) {
//                titles[i - 1] = resultSetMetaData.getColumnName(i);
//            }
//            System.out.println(titles.length);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(titles.toString());
        return titles;
    }

    public Object[][] getRows() {
        Object[][] rows = null;

        List<Object[]> listRows = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Object[] row = new Object[resultSetMetaData.getColumnCount()];
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                listRows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rows = (Object[][]) listRows.toArray();
        return rows;
    }
}