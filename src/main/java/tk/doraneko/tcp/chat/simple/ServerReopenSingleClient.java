package tk.doraneko.tcp.chat.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server hoạt động trở lại khi client ngắt kết nối
 * @author tranphuquy19@gmail.com
 * @since 22/08/2019
 */
public class ServerReopenSingleClient implements Runnable {
    private Socket socket;
    private ServerSocket serverSocket;
    private Thread thread;
    private BufferedReader bufferedReader;

    public ServerReopenSingleClient(int port) {
        System.out.println("Server type: " + getClass().getSimpleName());
        System.out.println("Server is starting in port: " + port);

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is started!");
            System.out.println("serverSocket = " + serverSocket);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    private void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
    }

    private void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

    private void open() throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        while (thread != null) {
            System.out.println("Waiting for client...");
            try {
                socket = serverSocket.accept();
                System.out.println("Client accepted: ");
                System.out.println("socket = " + socket);
                open();
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    System.out.println(line);
                    if(line.trim().equals(".bye")) break;
                    if(line.trim().equals(".stop")){
                        close();
                        stop();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final int port = 16057;
        ServerReopenSingleClient server;
        if(args.length != 1){
            server = new ServerReopenSingleClient(port);
        }else{
            int newPort = Integer.parseInt(args[0]);
            server = new ServerReopenSingleClient(newPort);
        }
    }
}
