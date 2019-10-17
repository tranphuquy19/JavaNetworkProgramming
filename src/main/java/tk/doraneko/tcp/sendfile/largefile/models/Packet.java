package tk.doraneko.tcp.sendfile.largefile.models;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author tranphuquy19@gmail.com
 * @since 14/10/2019
 */
public class Packet {
    public final static byte INITIALIZE = 2;
    public final static byte SEPARATOR = 4;

    //length: 3bytes
    public final static String COMMAND_SEND_TEXT = "122";
    public final static String COMMAND_SEND_FILE = "123";
    public final static String COMMAND_SEND_FILE_NAME = "124";
    public final static String COMMAND_SEND_FILE_LENGTH = "125";
    public final static String COMMAND_REQUEST_SEND_FILE_DATA = "126";
    public final static String COMMAND_SEND_FILE_DATA = "127";
    public final static String COMMAND_SEND_FINISH = "128";


    public static byte[] createDataPacket(String command, byte[] data) throws IOException {
        if (data.length == 0) throw new IOException();

        //packet =  [INITIALIZE(1bytes)-SEPARATOR(1bytes)-COMMAND(1bytes)-DATA_LENGTH(1byte)-DATA(data_length(bytes)]
        byte[] initialize = new byte[1];
        initialize[0] = INITIALIZE;
        byte[] separator = new byte[1];
        separator[0] = SEPARATOR;

        byte[] cmd = command.getBytes();
        byte[] data_length = String.valueOf(data.length).getBytes("UTF8");
        byte[] packet = new byte[initialize.length + separator.length + cmd.length + data_length.length + separator.length + data.length];

        int packetOffset = 0;

        //Append init to the packet
        System.arraycopy(initialize, 0, packet, packetOffset, initialize.length);
        packetOffset += initialize.length;

        //Append a separator
        System.arraycopy(separator, 0, packet, packetOffset, separator.length);
        packetOffset += separator.length;

        //Append a command
        System.arraycopy(cmd, 0, packet, packetOffset, cmd.length);
        packetOffset += cmd.length;

        //Append length of data
        System.arraycopy(data_length, 0, packet, packetOffset, data_length.length);
        packetOffset += data_length.length;

        //Append a separator
        System.arraycopy(separator, 0, packet, packetOffset, separator.length);
        packetOffset += separator.length;

        //Append data
        System.arraycopy(data, 0, packet, packetOffset, data.length);

        return packet;
    }

    public static byte[] readStream(DataInputStream dataInputStream) throws IOException, NullPointerException {
        byte[] dataBuff = null;
        int b = 0;
        String buffLength = "";
        while ((b = dataInputStream.readByte()) != SEPARATOR) {
            buffLength += (char) b;
        }
        int dataLength = Integer.parseInt(buffLength);
        if (dataLength == 0) {
            throw new NullPointerException();
        }

        dataBuff = new byte[Integer.parseInt(buffLength)];
        int byteRead = 0;
        int byteOffset = 0;
        while (byteOffset < dataLength) {
            /**
             * dataInputStream.read() return về tổng số bytes trong luồng, và -1 nếu gập kí tự kết thúc (has reached the end)
             * dataInputStream.read(byte b[], int offset, int length)
             *      <b>b</b> - bytes dữ liệu stream trả về
             *      <b>offset</b> - phần bù tính từ vị trí 0 của b
             *      <b>length</b> - số bytes tối đa cho việc đọc
             */
            byteRead = dataInputStream.read(dataBuff, byteOffset, dataLength - byteOffset);
            byteOffset += byteRead;
        } //end while
        return dataBuff;
    }
}
