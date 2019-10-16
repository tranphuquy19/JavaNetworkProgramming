package tk.doraneko.commons;

import java.io.Serializable;

/**
 * Thông tin file cần gửi
 * @author tranphuquy19@gmail.com
 * @since 13/10/2019
 */
public class FileInfo implements Serializable {
    private static final long SERIAL_VERSION_UID = 1L;

    private String destinationDirectory;
    private String sourceDirectory;
    private String fileName;
    private long fileSize;
    private int piecesOfFile;
    private int lastByteLength;
    private byte[] data;
    private String status;

    public static String getCureentWorkingDir() {
        return System.getProperty("user.dir");
    }

    public static long getSerialVersionUid() {
        return SERIAL_VERSION_UID;
    }

    public String getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getPiecesOfFile() {
        return piecesOfFile;
    }

    public void setPiecesOfFile(int piecesOfFile) {
        this.piecesOfFile = piecesOfFile;
    }

    public int getLastByteLength() {
        return lastByteLength;
    }

    public void setLastByteLength(int lastByteLength) {
        this.lastByteLength = lastByteLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
