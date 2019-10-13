package tk.doraneko.app;

import tk.doraneko.tcp.chat.simple.TcpClient;
import tk.doraneko.tcp.chat.simple.TcpServerMultiClient;
import tk.doraneko.tcp.chat.simple.TcpServerReopenSingleClient;
import tk.doraneko.tcp.chat.simple.TcpServerSingleClient;
import tk.doraneko.udp.chat.simple.UdpClient;
import tk.doraneko.udp.chat.simple.UdpServer;

/**
 * Created by @tranphuquy19 on 23/08/2019
 * Email:       tranphuquy19@gmail.com
 */
public enum Classs {

    TCP_SERVER_SINGLE_CLIENT(1, TcpServerSingleClient.class),
    TCP_SERVER_REOPEN_SINGLE_CLIENT(2, TcpServerReopenSingleClient.class),
    TCP_SERVER_MULTI_CLIENT(3, TcpServerMultiClient.class),
    TCP_CLIENT(4, TcpClient.class),

    UDP_SERVER_SINGLE_CLIENT(5, UdpServer.class),
    UDP_CLIENT(6, UdpClient.class);

    private final String URL_GIT_REMOTE_BASE = "https://github.com/tranphuquy19/JavaNetworkProgramming/blob/master/src/main/java/";

    private int index;
    private Class classEntity;

    Classs(int index, Class classEntity) {
        this.index = index;
        this.classEntity = classEntity;
    }

    public int getIndex() {
        return index;
    }

    public String getClassName() {
        return classEntity.getSimpleName();
    }

    public String getFullJavaPath(){
        return classEntity.getPackage().getName() + "." + getClassName();
    }

    public String getGitCode() {
        String url = URL_GIT_REMOTE_BASE + classEntity.getPackage().getName().replace(".", "/") + "/" + getClassName() + ".java";
        return url;
    }

    public Class getClassEntity() {
        return classEntity;
    }

    public static Classs findByIndex(int i) {
        Classs temp = null;
        for (Classs c : Classs.values()) {
            if (i == c.getIndex()) temp = c;
        }
        return temp;
    }

}
