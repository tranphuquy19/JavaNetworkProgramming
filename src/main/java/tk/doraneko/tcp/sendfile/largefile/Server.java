package tk.doraneko.tcp.sendfile.largefile;

import tk.doraneko.commons.FileInfo;

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

        private byte[] readStream() throws IOException {
            byte[] dataBuffer = null;
            int b = 0;
            String bufferLength = "";
            while ((b = dataInputStream.read()) != 4) {
                bufferLength += (char) b;
            }
            int dataLength = Integer.parseInt(bufferLength);
            dataBuffer = new byte[dataLength];

            int byteRead = 0;
            int byteOffset = 0;

            while (byteOffset < dataLength) {
                byteRead = dataInputStream.read(dataBuffer, byteOffset, dataLength - byteOffset);
                byteOffset += byteRead;
            }

            return dataBuffer;
        }

        private byte[] createDataPacket(byte[] cmd, byte[] data) throws UnsupportedEncodingException {
            byte[] packet = null;

            byte[] initialize = new byte[1];
            initialize[0] = 2;
            byte[] separator = new byte[1];
            separator[0] = 4;
            byte[] dataLength = String.valueOf(data.length).getBytes("UTF8");
            packet = new byte[initialize.length + cmd.length + separator.length + dataLength.length + data.length];

            System.arraycopy(initialize, 0, packet, 0, initialize.length);
            System.arraycopy(cmd, 0, packet, initialize.length, cmd.length);
            System.arraycopy(dataLength, 0, packet, initialize.length + cmd.length, dataLength.length);
            System.arraycopy(separator, 0, packet, initialize.length + cmd.length + dataLength.length, separator.length);
            System.arraycopy(data, 0, packet, initialize.length + cmd.length + dataLength.length + separator.length, data.length);

            return packet;
        }

        public void run() {
            RandomAccessFile randomAccessFile = null;
            long current_file_pointer = 0;
            boolean loop_break = false;

            while (!socket.isClosed()) {
                byte[] initialize = new byte[1];
                try {
                    dataInputStream.read(initialize, 0, initialize.length);
                    if (initialize[0] == 2) {
                        byte[] buffer = new byte[3];
                        dataInputStream.read(buffer, 0, buffer.length);
                        byte[] dataIn = readStream();
                        switch (Integer.parseInt(new String(buffer))) {
                            case 124:
                                randomAccessFile = new RandomAccessFile(FileInfo.getCureentWorkingDir() + "/res/files/" + new String(dataIn), "rw");
                                dataOutputStream.write(createDataPacket("125".getBytes("UTF8"), String.valueOf(current_file_pointer).getBytes("UTF8")));
                                dataOutputStream.flush();
                                break;
                            case 126:
                                randomAccessFile.seek(current_file_pointer);
                                randomAccessFile.write(dataIn);
                                current_file_pointer = randomAccessFile.getFilePointer();
                                System.out.println("Download percentage: " + (int) (current_file_pointer / randomAccessFile.length() * 100) + "%");
                                dataOutputStream.write(createDataPacket("125".getBytes("UTF8"), String.valueOf(current_file_pointer).getBytes("UTF8")));
                                dataOutputStream.flush();
                                break;
                            case 127:
                                if ("Close".equals(new String(dataIn))) {
                                    loop_break = true;
                                }
                                break;
                        }
                    } //end if
                    if (loop_break == true) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //end while
        }

    }
}
