package tk.doraneko.udp.chat.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author tranphuquy19@gmail.com
 * @since 12/10/2019
 */
public class Server {
    private DatagramSocket datagramSocket;

    private byte[] bufferIn = new byte[1024];
    private byte[] bufferOut = new byte[1024];

    public Server(int port) throws IOException {
        datagramSocket = new DatagramSocket(port);
        System.out.println("Server is running");
        start();
    }

    private void start() throws IOException {
        while (true) {
            DatagramPacket dataIn = new DatagramPacket(bufferIn, bufferIn.length);
            datagramSocket.receive(dataIn);

            String messIn = new String(dataIn.getData());
            System.out.println("Mess from client: " + messIn);

            bufferOut = "Hello from server".getBytes();

            DatagramPacket dataOut = new DatagramPacket(bufferOut, bufferOut.length, dataIn.getAddress(), dataIn.getPort());

            datagramSocket.send(dataOut);
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(16057);
    }
}
