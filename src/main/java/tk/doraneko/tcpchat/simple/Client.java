package tk.doraneko.tcpchat.simple;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by @tranphuquy19 on 22/08/2019
 * Email:       tranphuquy19@gmail.com
 */
public class Client {
    private Socket socket;
    private Scanner sc;
    private PrintWriter printWriter;

    public Client(String serverIP, int serverPort) {
        System.out.println("Establishing connection...");
        try {
            socket = new Socket(serverIP, serverPort);
            System.out.println("You connect to the server successfully");
            System.out.println("Connected: " + socket);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = "";
        while (!line.equals(".bye")) {
            line = sc.nextLine();
            printWriter.println(line);
        }
    }

    private void start() throws IOException {
        sc = new Scanner(System.in);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private void stop() throws IOException {
        if (socket != null) socket.close();
    }

    public static void main(String[] args) {
        final String _serverIP = "0.0.0.0";
        final int _serverPort = 16057;
        Client client;

        if (args.length != 2) {
            System.err.println("Connect to server using DEFAULT_IP and DEFAULT_IP");
            client = new Client(_serverIP, _serverPort);
        }else{
            String serverIP = args[0];
            int serverPort = Integer.parseInt(args[1]);
            client = new Client(serverIP, serverPort);
        }
    }
}
