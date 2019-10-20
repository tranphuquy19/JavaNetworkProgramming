package tk.doraneko.socketio.java.client;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * @author tranphuquy19@gmail.com
 * @since 18/10/2019
 */
public class SocketSingleton {
    private Socket mSocket;

    public SocketSingleton() {
        try {
            mSocket = IO.socket(Constains.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return mSocket;
    }

}
