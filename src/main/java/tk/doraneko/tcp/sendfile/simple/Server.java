package tk.doraneko.tcp.sendfile.simple;

import java.net.ServerSocket;

/**
 * Created by @tranphuquy19 on 13/10/2019
 * Email:       tranphuquy19@gmail.com
 */
public class Server implements Runnable{
    private ServerSocket serverSocket;
    private Thread thread;
    private ServerThread serverThread;

    private int port;
    public Server(int port){
        this.port = port;
        open();
    }

    private void open(){

    }

    @Override
    public void run() {

    }


    private class ServerThread extends Thread{

    }
}
