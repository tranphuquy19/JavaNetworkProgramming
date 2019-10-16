package tk.doraneko.test.threelayerpattern.dto;

/**
 * @author tranphuquy19@gmail.com
 * @since 16/10/2019
 */
public class UserDTO {
    private int id;
    private String name;
    private String address;
    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String toString() {
        return this.id + " " + this.name + " " + this.address + " " + this.total;
    }
}
