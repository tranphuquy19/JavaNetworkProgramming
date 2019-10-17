package tk.doraneko.tcp.chat.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server multi thread đáp ứng nhiều client cùng 1 lúc
 * respone về client mess: OK
 * @author tranphuquy19@gmail.com
 * @since 22/08/2019
 */
public class ServerMultiClient implements Runnable {
    private ServerSocket serverSocket;
    private Thread thread;
    private ServerThread serverThread;

    private int port;

    public ServerMultiClient(int port) throws IOException{
        this.port = port;

        System.out.println("Server type: " + getClass().getSimpleName());
        System.out.println("Server is starting in port: " + port);
        open();
        start();
    }

    private void open() throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println("Server is started!");
        System.out.println("serverSocket = " + serverSocket);
        System.out.println("Waiting for client...");
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

    private void addThread(Socket socket) throws IOException {
        System.out.println("Client accepted: ");
        System.out.println("socket = " + socket);
        serverThread = new ServerThread(this, socket);
        serverThread.open();
        serverThread.start();
    }

    /**
     * Main func
     *
     * @param args Start Server with serverPort = <b>args[0]</b> or by default serverPort = <b>16057</b>
     */
    public static void main(String[] args) throws IOException {
        final int port = 16057;
        if (args.length != 1) {
            System.out.println("Server start is default port: " + port);
            new ServerMultiClient(port);
        } else {
            int newPort = Integer.parseInt(args[0]);
            System.out.println("Server start in new port: " + newPort);
            new ServerMultiClient(newPort);
        }
    }

    /**
     * Class sinh các threads phục vụ từng client
     */
    private class ServerThread extends Thread {
        private Socket socket;
        private ServerMultiClient serverMultiClient;
        private BufferedReader bufferedReader;
        private int endPointPort = -1;
        private PrintWriter printWriter;

        public ServerThread(ServerMultiClient server, Socket socket) {
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
