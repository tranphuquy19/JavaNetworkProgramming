package tk.doraneko.tcp.sendfile.simple;

import tk.doraneko.commons.FileInfo;

import java.io.*;
import java.net.Socket;

/**
 * @author tranphuquy19@gmail.com
 * @since 14/10/2019
 */
public class Client {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private String host;
    private int port;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        start();
    }

    private void start() throws IOException {
        socket = new Socket(host, port);
        System.out.println("Connect to server successfully");
    }

    private FileInfo getFileInfo(String filePath) throws IOException, FileNotFoundException {
        FileInfo fileInfo = null;
        BufferedInputStream bufferedInputStream = null;

        File sourceFile = new File(filePath);
        bufferedInputStream = new BufferedInputStream(new FileInputStream(sourceFile));

        fileInfo = new FileInfo();
        byte[] fileBytes = new byte[(int) sourceFile.length()];

        bufferedInputStream.read(fileBytes, 0, fileBytes.length);

        fileInfo.setFileName(sourceFile.getName());
        fileInfo.setData(fileBytes);

        return fileInfo;
    }

    /**
     * Gửi một file qua Server
     * @param filePath đường dẫn của file trên hệ thống
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     */
    public void sendFile(String filePath) throws IOException, ClassNotFoundException, FileNotFoundException {
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF("Hello from client!");

        FileInfo fileInfo = getFileInfo(filePath);

        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(fileInfo);

        objectInputStream = new ObjectInputStream(socket.getInputStream());
        fileInfo = (FileInfo) objectInputStream.readObject();
        if (fileInfo != null) {
            System.out.println(fileInfo.getStatus());
        }
    }

    /**
     * @param args : <b> args[] ={hostID, hostPort, filePath} or {filePath} </b>
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = "0.0.0.0";
        int hostPort = 16057;
        String filePath = null;

        if (args.length == 0) {
            System.out.println("Please enter file path");
            return;
        }

        if (args.length == 1) {
            filePath = args[0];
        } else {
            host = args[0];
            hostPort = Integer.parseInt(args[1]);
            filePath = args[2];
        }
        Client client = new Client(host, hostPort);
        client.sendFile(filePath);
    }
}
