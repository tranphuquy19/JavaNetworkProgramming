package tk.doraneko.udp.sendfile.simple;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * @author tranphuquy19@gmail.com
 * @since 18/10/2019
 */
public class Server {
    private static int PIECES_OFF_FILE_SIZE = 1024 * 32; // a piece is 32KB

    private DatagramSocket datagramSocket;

    private int port;

    public Server(int port) throws IOException {
        this.port = port;
        open();
        start();
    }

    private void open() throws IOException {
        datagramSocket = new DatagramSocket(port);
        System.out.println("Server is listening on port" + port);
    }

    private void start(){

    }
}
