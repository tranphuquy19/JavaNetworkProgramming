package tk.doraneko.tcpchat.simple;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by @tranphuquy19 on 22/08/2019
 * Email:       tranphuquy19@gmail.com
 */

/**
 * Server accept single client
 */
public class TcpServerSingleClient {
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;

    public TcpServerSingleClient(int port) {
        System.out.println("Server type: " + getClass().getSimpleName());
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
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
                if(line.trim().equals(".bye")) break;
            }
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() throws IOException {
        if(socket != null){
            socket.close();
        }
        if(bufferedReader != null){
            bufferedReader.close();
        }
        if(serverSocket != null){
            serverSocket.close();
        }
    }

    private void open() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void main(String[] args) {
        final int port = 16057;
        TcpServerSingleClient server;
        if(args.length != 1){
            System.out.println("Server start is default port: " + port);
            server = new TcpServerSingleClient(port);
        }else{
            int newPort = Integer.parseInt(args[0]);
            System.out.println("Server start in new port: " + newPort);
            server = new TcpServerSingleClient(newPort);
        }
    }
}
