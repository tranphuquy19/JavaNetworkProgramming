package tk.doraneko.socketio.java.client;

import java.util.Scanner;

/**
 * @author tranphuquy19@gmail.com
 * @since 18/10/2019
 */
public class Client {
    public static void main(String[] args) {
        SocketSingleton socketSingleton = new SocketSingleton();
        socketSingleton.getSocket();
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
