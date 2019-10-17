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
            long currentFilePointer = 0;
            String fileName = "";
            long fileLength = 1;
            boolean loopBreak = false;

            while (!socket.isClosed()) {
                try {
                    if (dataInputStream.readByte() == Packet.INITIALIZE) {
                        int b = 0;
                        dataInputStream.readByte(); // tieu thu SEPARATOR
                        byte[] cmdBuff = new byte[3];
                        dataInputStream.read(cmdBuff, 0, cmdBuff.length);
                        byte[] recvData = Packet.readStream(dataInputStream);
                        switch (new String(cmdBuff)) {
                            case Packet.COMMAND_SEND_FILE_NAME:
                                fileName = new String(recvData);
                                randomAccessFile = new RandomAccessFile(workingDir + "/res/files/" + fileName, "rw");
                                dataOutputStream.write(Packet.createDataPacket(Packet.COMMAND_REQUEST_SEND_FILE_DATA, String.valueOf(currentFilePointer).getBytes("UTF8")));
                                dataOutputStream.flush();
                                break;
                            case Packet.COMMAND_SEND_FILE_LENGTH:
                                fileLength = Long.parseLong(new String(recvData));
                                break;
                            case Packet.COMMAND_SEND_FILE_DATA:
                                randomAccessFile.seek(currentFilePointer);
                                randomAccessFile.write(recvData);
                                currentFilePointer = randomAccessFile.getFilePointer();
                                float percent = ((float) currentFilePointer / fileLength) * 100;
                                System.out.println("Download percentage: " + percent + "%");
                                dataOutputStream.write(Packet.createDataPacket(Packet.COMMAND_REQUEST_SEND_FILE_DATA, String.valueOf(currentFilePointer).getBytes("UTF8")));
                                dataOutputStream.flush();
                                break;
                            case Packet.COMMAND_SEND_FINISH:
                                if ("Close".equals(new String(recvData))) {
                                    loopBreak = true;
                                }
                                break;
                        }
                    }
                    if (loopBreak) {
                        randomAccessFile.close();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //end while
        }

    }

    /**
     * Mặc định nơi lưu file là workingDir + "/res/files/"
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        new Server(16057, "/home/tranphuquy19/Documents/JavaNetworkProgramming");
    }
}
