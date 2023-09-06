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
    ChatHandler chatHandler;

    TicTacToeServer() {

    }

    public void run() {
        try {
            waiter = new Socket();
            System.out.println("Server has been opened");
            server = new ServerSocket(5000);
            while (true) {
                System.out.println("accepting...");
                waiter = server.accept();//////
                chatHandler = new ChatHandler(waiter);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    void closeServer() {
        try {
            if (chatHandler != null) {
                chatHandler.closeHandler();
            }
            waiter.close();
            server.close();
            System.out.println("Server has been closed");
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                //isConnected = waiter.isConnected();
                while (true) {
                    System.out.println("listening...");
                    try {
                        String msg = ear.readLine();//login:moha:12345
                        System.out.println(msg);
                        String[] split = msg.split(":");
                        System.out.println(split.length);
                        System.out.println(split[0]);//login - signup - challenge - accept
                        /*if(split[0].trim().equals("login")){
                            sendToClient(this.id,"loged in");
                        }*/
                        /*String[] split = msg.split(":");*/
                        switch(split[0]){
                            case "login":
                                //login()
                                sendToClient(id,split[0]+"-success");//or"-fail"
                                break;
                            case "signup":
                                //signUp()
                                sendToClient(id,split[0]+"-success");//or"-fail"
                                break;
                            case "invite"://inite:id
                                //invitation()
                                sendToClient(id,split[0]+"-success");//or"-fail"
                                break;
                        }
                        
                    } catch (IOException ex) {
                        System.out.println("server: connection error from user"+this.id);
                        try {
                            ear.close();
                            mouth.close();
                        } catch (IOException ex1) {
                            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                        break;
                    }
                }
            });
            th.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    

    
   void sendToClient(int id, String msg) {
        for (ChatHandler client : clients) {
            if(client.id == id){
                client.mouth.println(msg);
            }
        }
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
