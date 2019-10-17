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

        int packet_offset = 0;

        //Append init to the packet
        System.arraycopy(initialize, 0, packet, packet_offset, initialize.length);
        packet_offset += initialize.length;

        //Append a separator
        System.arraycopy(separator, 0, packet, packet_offset, separator.length);
        packet_offset += separator.length;

        //Append a command
        System.arraycopy(cmd, 0, packet, packet_offset, cmd.length);
        packet_offset += cmd.length;

        //Append length of data
        System.arraycopy(data_length, 0, packet, packet_offset, data_length.length);
        packet_offset += data_length.length;

        //Append a separator
        System.arraycopy(separator, 0, packet, packet_offset, separator.length);
        packet_offset += separator.length;

        //Append data
        System.arraycopy(data, 0, packet, packet_offset, data.length);

        return packet;
    }

    public static byte[] readStream(DataInputStream dataInputStream) throws IOException, NullPointerException {
        byte[] data_buff = null;
        int b = 0;
        String buff_length = "";
        while ((b = dataInputStream.readByte()) != SEPARATOR) {
            buff_length += (char) b;
        }
        int data_length = Integer.parseInt(buff_length);
        if (data_length == 0) {
            throw new NullPointerException();
        }

        data_buff = new byte[Integer.parseInt(buff_length)];
        int byte_read = 0;
        int byte_offset = 0;
        while (byte_offset < data_length) {
            /**
             * dataInputStream.read() return về tổng số bytes trong luồng, và -1 nếu gập kí tự kết thúc (has reached the end)
             * dataInputStream.read(byte b[], int offset, int length)
             *      <b>b</b> - bytes dữ liệu stream trả về
             *      <b>offset</b> - phần bù tính từ vị trí 0 của b
             *      <b>length</b> - số bytes tối đa cho việc đọc
             */
            byte_read = dataInputStream.read(data_buff, byte_offset, data_length - byte_offset);
            byte_offset += byte_read;
        } //end while
        return data_buff;
    }
}
