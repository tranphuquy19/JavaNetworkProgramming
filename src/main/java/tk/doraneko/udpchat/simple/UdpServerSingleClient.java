package tk.doraneko.udpchat.simple;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by @tranphuquy19 on 12/10/2019
 * Email:       tranphuquy19@gmail.com
 */
public class UdpServerSingleClient {
    private DatagramSocket datagramSocket;

    private byte[] bufferIn = new byte[1024];
    private byte[] bufferOut = new byte[1024];

    public UdpServerSingleClient(int port) throws SocketException, IOException {
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
        new UdpServerSingleClient(16057);
    }
}
