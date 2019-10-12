package tk.doraneko.udpchat.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by @tranphuquy19 on 12/10/2019
 * Email:       tranphuquy19@gmail.com
 */
public class UdpClient {
    private DatagramSocket datagramSocket;
    private InetAddress serverAddress;

    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    private String host;
    private int port;

    private byte[] bufferIn = new byte[1024];
    private byte[] bufferOut = new byte[1024];

    public UdpClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;

        datagramSocket = new DatagramSocket();

        serverAddress = InetAddress.getByName(host);

        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        start();
    }

    private void start() throws IOException {
        while (true) {
            System.out.print("Enter your mess: ");
            String messOut = bufferedReader.readLine();
            bufferOut = messOut.getBytes();

            DatagramPacket dataOut = new DatagramPacket(bufferOut, bufferOut.length, serverAddress, port);
            datagramSocket.send(dataOut);

            DatagramPacket dataIn = new DatagramPacket(bufferIn, bufferIn.length);
            datagramSocket.receive(dataIn);

            String messIn = new String(dataIn.getData());
            System.out.println("Mess from server: " + messIn);
        }
    }

    public static void main(String[] args) throws IOException {
        new UdpClient("localhost", 16057);
    }

}
