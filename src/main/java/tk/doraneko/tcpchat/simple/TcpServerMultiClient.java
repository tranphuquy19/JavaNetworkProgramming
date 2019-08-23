package tk.doraneko.tcpchat.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by @tranphuquy19 on 22/08/2019
 * Email:       tranphuquy19@gmail.com
 */

/**
 * Server multi thread đáp ứng nhiều client cùng 1 lúc
 * respone về client mess: OK
 */
public class TcpServerMultiClient implements Runnable {
    private ServerSocket serverSocket;
    private Thread thread;
    private ServerThread serverThread;

    public TcpServerMultiClient(int port) {
        System.out.println("Server type: " + getClass().getSimpleName());
        System.out.println("Server is starting in port: " + port);
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is started!");
            System.out.println("serverSocket = " + serverSocket);
            System.out.println("Waiting for client...");
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

    @Override
    public void run() {
        while (thread != null) {
            try {
                addThread(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addThread(Socket socket) {
        System.out.println("Client accepted: ");
        System.out.println("socket = " + socket);
        serverThread = new ServerThread(this, socket);
        try {
            serverThread.open();
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final int port = 16057;
        TcpServerMultiClient server;
        if (args.length != 1) {
            System.out.println("Server start is default port: " + port);
            server = new TcpServerMultiClient(port);
        } else {
            int newPort = Integer.parseInt(args[0]);
            System.out.println("Server start in new port: " + newPort);
            server = new TcpServerMultiClient(newPort);
        }
    }

    private class ServerThread extends Thread {
        private Socket socket;
        private TcpServerMultiClient serverMultiClient;
        private BufferedReader bufferedReader;
        private int endPointPort = -1;
        private PrintWriter printWriter;

        public ServerThread(TcpServerMultiClient server, Socket socket) {
            this.serverMultiClient = server;
            this.socket = socket;
            this.endPointPort = socket.getPort();
        }

        public void open() throws IOException {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        }

        public void close() throws IOException {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        public void run() {
            System.out.println("Server connected client port: " + this.endPointPort);
            String line = "";
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println("Client[" + endPointPort + "] send: " + line);
                    printWriter.println("Server send to [" + endPointPort + "] : OK");
                    if (line.trim().equals(".bye")) {
                        close();
                        break;
                    }
                    if (line.trim().equals(".stop")) {
                        close();
                        serverMultiClient.stop();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
