package tk.doraneko.test.threelayerpattern.dao;

import tk.doraneko.test.threelayerpattern.dto.UserDTO;
import tk.doraneko.test.threelayerpattern.utils.MySqlDataAccessHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author tranphuquy19@gmail.com
 * @since 16/10/2019
 */
public class UserDAO {
    public static ArrayList<UserDTO> getAllUser() {
        ArrayList<UserDTO> userList = new ArrayList<>();

        String sql = "select * from user_tb";
        MySqlDataAccessHelper mySqlDataAccessHelper = new MySqlDataAccessHelper();
        try {
            mySqlDataAccessHelper.open();
            ResultSet resultSet = mySqlDataAccessHelper.excuteQuery(sql);
            while (resultSet.next()) {
                UserDTO user = new UserDTO();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAddress(resultSet.getString("address"));
                user.setTotal(resultSet.getInt("total"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
}
