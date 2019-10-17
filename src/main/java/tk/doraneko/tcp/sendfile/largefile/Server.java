package tk.doraneko.tcp.sendfile.largefile;

import tk.doraneko.tcp.sendfile.largefile.models.Packet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author tranphuquy19@gmail.com
 * @since 14/10/2019
 */
public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Thread thread;
    private ServerThread serverThread;

    private int port;
    private String workingDir;

    public Server(int port, String workingDir) throws IOException {
        this.port = port;
        this.workingDir = workingDir;
        open();
        start();
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

    private void open() throws IOException {
        serverSocket = new ServerSocket(this.port);
        System.out.println("Server is open on port: " + this.port);
    }

    private void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    private void addThread(Socket socket) throws IOException {
        System.out.println("Client is accepted");
        System.out.println("Socket: " + socket);
        serverThread = new ServerThread(socket);
        serverThread.open();
        serverThread.start();
    }

    private class ServerThread extends Thread {
        private Socket socket;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void open() throws IOException {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }

        public void run() {
            System.out.println("Listening client");
            RandomAccessFile randomAccessFile = null;
            long current_file_pointer = 0;
            String fileName = "";
            long fileLength = 1;
            boolean loop_break = false;

            while (!socket.isClosed()) {
                try {
                    if (dataInputStream.readByte() == Packet.INITIALIZE) {
                        int b = 0;
                        byte[] cmd_buff = new byte[3];
                        dataInputStream.read(cmd_buff, 0, cmd_buff.length);
                        byte[] recv_data = Packet.readStream(dataInputStream);
                        System.out.println(new String(cmd_buff));
                        switch (new String(cmd_buff)) {
                            case Packet.COMMAND_SEND_FILE_NAME:
                                fileName = new String(recv_data);
                                randomAccessFile = new RandomAccessFile(workingDir + "/res/files/" + fileName, "rw");
                                dataOutputStream.write(Packet.createDataPacket(Packet.COMMAND_REQUEST_SEND_FILE_DATA, String.valueOf(current_file_pointer).getBytes("UTF8")));
                                dataOutputStream.flush();
                                break;
                            case Packet.COMMAND_SEND_FILE_LENGTH:
                                fileLength = Long.parseLong(new String(recv_data));
                                break;
                            case Packet.COMMAND_SEND_FILE_DATA:
                                randomAccessFile.seek(current_file_pointer);
                                randomAccessFile.write(recv_data);
                                current_file_pointer = randomAccessFile.getFilePointer();
                                float percent = (float) (current_file_pointer / fileLength) * 100;
                                System.out.println("Download percentage: " + percent);
                                dataOutputStream.flush();
                                break;
                            case Packet.COMMAND_SEND_FINISH:
                                if ("Close".equals(new String(recv_data))) {
                                    loop_break = true;
                                }
                                break;
                        }
                    }
                    if (loop_break) {
                        randomAccessFile.close();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //end while
        }

    }

    public static void main(String[] args) throws IOException {
        new Server(16057, "C:/Users/Tran Phu Quy/Documents/IntelliJ IDEA Projects/JavaNetworkProgramming/");
    }
}
