package tk.doraneko.tcp.chat.simple;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client gửi nhận strings đến TCP Server
 * @author tranphuquy19@gmail.com
 * @since 22/08/2019
 */
public class Client {
    private Socket socket;
    private Scanner sc;
    private PrintWriter printWriter;

    /**
     * Thiết lập kết nối đến server theo IP và port của server
     * @param serverIP IP của Server
     * @param serverPort Port của Server
     */
    public Client(String serverIP, int serverPort) throws IOException{
        System.out.println("Establishing connection...");
        socket = new Socket(serverIP, serverPort);
        System.out.println("You connect to the server successfully");
        System.out.println("Connected: " + socket);
        start();
        listenServer();
        String line = "";
        while (!line.equals(".bye") && !line.equals(".stop")) {
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

    private void listenServer() {
        new ClientListeningServer(socket).start();
    }

    private class ClientListeningServer extends Thread {
        private Socket socket;
        private BufferedReader bufferedReader;

        public ClientListeningServer(Socket socket) {
            this.socket = socket;
            try {
                open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void open() throws IOException{
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        @Override
        public void run() {
            super.run();
            String line = "";
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param args Start client with: <b> args[] ={hostID, hostPort} or by default hostID="0.0.0.0" &amp; hostPort=16057 </b>
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final String _serverIP = "0.0.0.0";
        final int _serverPort = 16057;

        if (args.length != 2) {
            System.err.println("Connect to server using DEFAULT_IP and DEFAULT_PORT");
            new Client(_serverIP, _serverPort);
        } else {
            String serverIP = args[0];
            int serverPort = Integer.parseInt(args[1]);
            new Client(serverIP, serverPort);
        }
    }
}
