package tk.doraneko.tcp.sendfile.simple;

import tk.doraneko.commons.FileInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server cho phép client gửi nhiều file cùng một lúc, sử dụng thread.
 * Ưu: dễ cấu hình. Nhược: ngốm bộ nhớ (heap) nếu dung lượng file lớn,
 * lỗi khi dung lượng file lớn hơn dung lượng khả dụng của pc
 *
 * @author tranphuquy19@gmail.com
 * @since 13/10/2019
 */
public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Thread thread;
    private ServerThread serverThread;

    private int port;

    public Server(int port) throws IOException {
        this.port = port;
        open();
        start();
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
        System.out.println("Client accepted");
        System.out.println("Socket:" + socket);
        serverThread = new ServerThread(socket);
        //serverThread.open();
        serverThread.start();
    }

    private class ServerThread extends Thread {
        private Socket socket;
        private DataInputStream dataInputStream;
        private ObjectInputStream objectInputStream;
        private ObjectOutputStream objectOutputStream;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void open() {

        }

        public void close(Socket socket) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void close(InputStream inputStream) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void close(OutputStream outputStream) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void createFile(FileInfo fileInfo) {
            BufferedOutputStream bufferedOutputStream = null;
            try {
                File fileReceive = new File(FileInfo.getCureentWorkingDir() + fileInfo.getFileName());

                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileReceive));
                bufferedOutputStream.write(fileInfo.getData());
                bufferedOutputStream.flush();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                close(bufferedOutputStream);
            }
        }

        public void run() {
            while (true) {
                try {
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    System.out.println("Mess from client: " + dataInputStream.readUTF());

                    // receive file info
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    FileInfo fileInfo = (FileInfo) objectInputStream.readObject();

                    if (fileInfo != null) {
                        createFile(fileInfo);
                    }

                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    fileInfo.setStatus("success");
                    objectOutputStream.writeObject(fileInfo);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    close(objectInputStream);
                    close(objectOutputStream);
                    close(dataInputStream);
                    close(socket);
                }
            }
        }
    }
}
