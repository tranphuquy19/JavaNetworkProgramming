package tk.doraneko.tcp.sendfile.largefile;

import tk.doraneko.tcp.sendfile.largefile.models.Packet;

import java.io.*;
import java.net.Socket;

/**
 * @author tranphuquy19@gmail.com
 * @since 14/10/2019
 */
public class Client {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    private final int MAX_LENGTH_SEND = 20000;

    private String host;
    private int port;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        start();
    }

    private void start() throws IOException {
        socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        System.out.println("Connect to server successfully");
    }

    public void sendFile(String filePath) throws IOException {
        File sourceFile = new File(filePath);
        byte[] temp = Packet.createDataPacket(Packet.COMMAND_SEND_FILE, sourceFile.getName().getBytes("UTF8"));
        dataOutputStream.write(temp);
        dataOutputStream.write(Packet.createDataPacket(Packet.COMMAND_SEND_FILE_NAME, sourceFile.getName().getBytes("UTF8")));
        RandomAccessFile randomAccessFile = new RandomAccessFile(sourceFile, "r");

        dataOutputStream.write(Packet.createDataPacket(Packet.COMMAND_SEND_FILE_LENGTH, String.valueOf(randomAccessFile.length()).getBytes("UTF8")));
        long current_file_pointer;
        boolean loop_break = false;

        while (!socket.isClosed()) {
            if (dataInputStream.readByte() == Packet.INITIALIZE) {
                int b = 0;
                dataInputStream.readByte(); // tiêu thụ SEPARATOR
                byte[] cmd_buff = new byte[3];
                dataInputStream.read(cmd_buff, 0, cmd_buff.length);

                byte[] recv_buff = Packet.readStream(dataInputStream);

                switch (new String(cmd_buff)) {
                    case Packet.COMMAND_REQUEST_SEND_FILE_DATA:
                        current_file_pointer = Long.valueOf(new String(recv_buff));
                        long residual_len = randomAccessFile.length() - current_file_pointer; //phần dư ra sau khi cắt file
                        int buff_len = (int) (residual_len < MAX_LENGTH_SEND ? residual_len : MAX_LENGTH_SEND); // tính toán phần data phải gửi
                        byte[] temp_buff = new byte[buff_len];
                        if (current_file_pointer != randomAccessFile.length()) { // == nếu con tro file nằm cuối file
                            randomAccessFile.seek(current_file_pointer);
                            randomAccessFile.read(temp_buff, 0, temp_buff.length); // fill vào mảng

                            dataOutputStream.write(Packet.createDataPacket(Packet.COMMAND_SEND_FILE_DATA, temp_buff));
                            dataOutputStream.flush();

                            float percent = ((float) (current_file_pointer + temp_buff.length) / randomAccessFile.length()) * 100;
                            System.out.println("Upload percentage: " + percent + "%");
                        } else {
                            loop_break = true;
                        }
                        break;
                }// end switch
            }
            if (loop_break == true) {
                dataOutputStream.write(Packet.createDataPacket(Packet.COMMAND_SEND_FINISH, "Close".getBytes("UTF8")));
                dataOutputStream.flush();
                randomAccessFile.close();
                socket.close();
                break;
            }
        }// end while
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("0.0.0.0", 16057);
        if (!args[0].isEmpty()) {
            client.sendFile(args[0]);
        }
    }
}
