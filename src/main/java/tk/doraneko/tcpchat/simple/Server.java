package tk.doraneko.tcpchat.simple;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by @tranphuquy19 on 22/08/2019
 */
public class Server {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream dataInputStream = null;

    public Server(int port) {
        System.out.println("Server is starting in port: " + port);

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is started!");
            System.out.println("serverSocket = " + serverSocket);
            System.out.println("Waiting for client...");
            socket = serverSocket.accept();
            System.out.println("Client accepted: ");
            System.out.println("socket = " + socket);
            open();
            boolean done = false;
            while (!done) {
                String line = dataInputStream.readUTF();
                System.out.println(line);
                done = line.trim().equals(".bye");
            }
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (dataInputStream != null) {
            dataInputStream.close();
        }
    }

    private void open() throws IOException {
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public static void main(String[] args) {
        Server server;
        if(args.length != 1){
            System.out.println("Server start is default port: ");
        }
    }
}
