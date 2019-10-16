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
    private String workingDir;

    public Server(int port, String workingDir) throws IOException {
        this.port = port;
        this.workingDir = workingDir;
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

    private void addThread(Socket socket) throws IOException{
        System.out.println("Client is accepted");
        System.out.println("Socket:" + socket);
        serverThread = new ServerThread(socket);
        serverThread.open();
        serverThread.start();
    }

    /**
     * Thread nhận các file theo từng client
     */
    private class ServerThread extends Thread {
        private Socket socket;
        private DataInputStream dataInputStream;
        private ObjectInputStream objectInputStream;
        private ObjectOutputStream objectOutputStream;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void open() throws IOException {
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
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

        /**
         * Lưu file vào hệ thống, mặc định đường dẫn là <b style="color: red"><i>"./res/files/<filePath>"</i></b>
         *
         * @param fileInfo
         */
        private void createFile(FileInfo fileInfo) {
            BufferedOutputStream bufferedOutputStream = null;
            try {
                File fileReceive = new File(Server.this.workingDir + "/res/files/" + fileInfo.getFileName());

                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileReceive));
                bufferedOutputStream.write(fileInfo.getData());
                bufferedOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(bufferedOutputStream);
            }
        }

        public void run() {
            while (!socket.isClosed()) {
                try {
                    System.out.println("Mess from client: " + dataInputStream.readUTF());

                    // receive file info
                    FileInfo fileInfo = (FileInfo) objectInputStream.readObject();

                    if (fileInfo != null) {
                        createFile(fileInfo);
                    }

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

    public static void main(String[] args) throws IOException {
        final String workingDir = "/home/tranphuquy19/Documents/NetworkProgramming/";
        new Server(16057, workingDir);
    }
}
