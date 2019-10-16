package tk.doraneko.test.threelayerpattern.bus;

import tk.doraneko.test.threelayerpattern.dao.UserDAO;
import tk.doraneko.test.threelayerpattern.dto.UserDTO;

import java.util.ArrayList;

/**
 * @author tranphuquy19@gmail.com
 * @since 16/10/2019
 */
public class UserBUS {
    public static ArrayList<UserDTO> getAll(){
        return UserDAO.getAllUser();
    }
}
