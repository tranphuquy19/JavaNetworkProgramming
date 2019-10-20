package tk.doraneko.test;

import tk.doraneko.socketio.java.client.SocketSingleton;

/**
 * @author tranphuquy19@gmail.com
 * @since 18/10/2019
 */
public class TestSocketIO {
    public static void main(String[] args) {
        SocketSingleton socketClient = new SocketSingleton();
        socketClient.getSocket();
    }
}
