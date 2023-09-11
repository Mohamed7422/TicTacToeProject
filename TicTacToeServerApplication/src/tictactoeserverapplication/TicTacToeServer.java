package tictactoeserverapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
                waiter = server.accept();
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
            System.out.println("one user accepted");
            ear = new DataInputStream(waiter.getInputStream());
            mouth = new PrintStream(waiter.getOutputStream());
            this.id = ID_GENERATOR++;
            ChatHandler.clients.add(this);
            th = new Thread(() -> {
                while (true) {
                    System.out.println("listening...");
                    try {
                        String msg = ear.readLine();
                        if (msg == null) {
                            break;
                        }
                        System.out.println(msg);
                        String[] split = msg.split(":");
                        switch (split[0]) {
                            case "login"://login:m7md:123456
                                //send to all new list of clients
                                String name = split[1];
                                String pass = split[2];

                                if (login(name, pass)) {
                                    sendToClient(id, split[0] + "-success");//or"-fail"
                                    //change status here
                                    changeStatusOfPlayer(name, "Online");
                                    List<Player> list = getOnlinePlayers();
                                    String response = "get-players-success";
                                    for (Player p : list) {
                                        response += ":" + p.getName();
                                    }
                                    System.out.println(response);
                                    if (list.size() != 0) {
                                        sendToAllClients(response);
                                    } else {
                                        sendToAllClients("get-players-fail");
                                    }
                                } else {
                                    sendToClient(id, split[0] + "-fail");
                                }

                                break;
                            case "test":
                                System.out.println("i'm here to test");
                                sendToAllClients("test send to all");
                                break;
                            case "logout":////logout:ahmed
                                System.out.println("from server: " +msg);
                                //change status of user in DB to "offline"
                                //send to all new list of clients
                                //getOnlinePlayers(1,"Online");
                                
                                changeStatusOfPlayer(split[1], "Offline");
                                List<Player> list = getOnlinePlayers();
                                String response = "get-players-success";
                                for (Player p : list) {
                                    response += ":" + p.getName();
                                }
                                System.out.println(response);
                                if (list.size() != 0) {
                                    System.out.println("hello:  "+response);
                                    sendToAllClients(response);
                                } else {
                                    sendToAllClients("get-players-fail");
                                }

                                break;
                            case "signup":
                                //signUp()
                                if (signUp(split[1], split[2])) {
                                    sendToClient(id, split[0] + "-success");//or"-fail"

                                } else {
                                    sendToClient(id, split[0] + "-fail");
                                }

                                break;
                            case "get-players":
                                //getOnlinePlayers("hanaa", "Offline");//change hanaa's status to offline
                                List<Player> list1 = getOnlinePlayers();
                                String response1 = split[0] + "-success";
                                for (Player p : list1) {
                                    response1 += ":" + p.getName();
                                }
                                System.out.println(response1);
                                if (list1.size() != 0) {
                                    sendToClient(id, response1);
                                } else {
                                    sendToClient(id, split[0] + "-fail");
                                }

                                break;
                            case "invite":
                                //invitation()
                                sendToClient(id, split[0] + "-success");//or"-fail"
                                break;
                            case "challenge"://callenge:username
                                //challenge()

                                break;
                        }

                    } catch (IOException ex) {
                        System.out.println("server: connection error from user" + this.id);
                        try {
                            ear.close();
                            mouth.close();
                        } catch (IOException ex1) {
                            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                        break;
                    }
                }
                System.out.println("connection with client has been closed");
                closeHandler();
            });
            th.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    void sendToClient(int id, String msg) {
        for (ChatHandler client : clients) {
            if (client.id == id) {
                client.mouth.println(msg);
            }
        }
    }

    void sendToAllClients(String msg) {
        for (ChatHandler client : clients) {
            client.mouth.println("user" + this.id + ": " + msg);
        }
    }

    void closeHandler() {
        try {
            ear.close();
            mouth.flush();
            mouth.close();
            th.stop();
        } catch (IOException ex) {
            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean signUp(String name, String pass) {

        return new DataBaseAccessLayer().insertPlayer(new Player(name, pass, 0, "online"));
    }

    private boolean login(String name, String pass) {
        return new DataBaseAccessLayer().signIn(name, pass);

    }

    private List<Player> getOnlinePlayers() {
        return new DataBaseAccessLayer().getOnlinePlayers();
    }

    private boolean changeStatusOfPlayer(String username, String Status) {
        return new DataBaseAccessLayer().updatePlayerStatus(username, Status);
    }

}
