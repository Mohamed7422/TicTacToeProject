package tictactoeserverapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToeServer extends Thread {

    ServerSocket server;
    Socket waiter;
    boolean serverFlag;
    ChatHandler chatHandler;

    TicTacToeServer() {
        serverFlag = false;

    }

    public void run() {
        try {
            waiter = new Socket();
            System.out.println("Server has been opened");
            serverFlag = true;
            server = new ServerSocket(5000);
            while (true) {
                System.out.println("accepting...");
                waiter = server.accept();
                chatHandler = new ChatHandler(waiter);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//    /*void openServer() {
//        serverFlag = true;
//        start();
//        /*if (serverFlag) {
//            resume();
//        } else {
//            start();
//        }*/
//    }
    void closeServer() {
        try {
            if (chatHandler != null) {
                chatHandler.closeHandler();
            }
            waiter.close();
//            if (waiter != null) {
//                waiter.close();
//            }
            server.close();
            System.out.println("Server has been closed");
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        //serverFlag = false;

        stop();
    }
}

class ChatHandler {

    static int ID_GENERATOR = 1;
    int id;
    DataInputStream ear;
    PrintStream mouth;
    static Vector<ChatHandler> clients = new Vector<ChatHandler>();
    Thread th;
    //boolean handlerFlag;

    ChatHandler(Socket waiter) {
        try {
            //handlerFlag = true;
            System.out.println("one user accepted");
            ear = new DataInputStream(waiter.getInputStream());
            mouth = new PrintStream(waiter.getOutputStream());
            this.id = ID_GENERATOR++;
            ChatHandler.clients.add(this);
            //start();
            th = new Thread(() -> {
                while (true) {
                    try {
                        String msg = ear.readLine();
                        System.out.println("received from user" + this.id + ": " + msg);
                        sendToAllClients(msg);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            th.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void run() {

    }

    void sendToAllClients(String msg) {
        for (ChatHandler client : clients) {
            //System.out.println(client.id);
            client.mouth.println("user" + this.id + ": " + msg);
        }
    }

    void closeHandler() {
        th.stop();
    }
}
