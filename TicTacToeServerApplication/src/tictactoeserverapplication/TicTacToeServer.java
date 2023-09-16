package tictactoeserverapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
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
    String username;
    DataInputStream ear;
    PrintStream mouth;
    static Vector<ChatHandler> clients = new Vector<ChatHandler>();
    Thread th;

    ChatHandler(Socket waiter) {
        try {
            ear = new DataInputStream(waiter.getInputStream());
            username = ear.readLine();
            id = ID_GENERATOR++;
            System.out.println(username + " accepted");
            mouth = new PrintStream(waiter.getOutputStream());
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
                        switch (split[1]) {
                            case "login"://no-name:login:m7md:123456
                                String name = split[2];
                                String pass = split[3];
                                if (login(name, pass)) {
                                    sendToClient(this, split[1] + "-success");
                                    changeStatusOfPlayer(name, "Online");
                                    List<Player> list = getOnlinePlayers();
                                    String response = "get-players-success";
                                    for (Player p : list) {
                                        response += ":" + p.getName();
                                    }
                                    if (list.size() != 0) {
                                        sendToAllClients(response);
                                    }
                                } else {
                                    sendToClient(this, split[1] + "-fail");
                                }
                                break;
                            case "logout"://mohannad:logout
                                changeStatusOfPlayer(split[0], "Offline");
                                List<Player> list = getOnlinePlayers();
                                String response = "get-players-success";
                                for (Player p : list) {
                                    response += ":" + p.getName();
                                }
                                System.out.println(response);
                                if (list.size() != 0) {
                                    System.out.println("hello:  " + response);
                                    sendToAllClients(response);
                                } else {
                                    sendToAllClients("get-players-fail");
                                }
                                break;
                            case "signup"://no-name:signup:m7md:123456 //no-name:signup:hanaa:123456
                                if (signUp(split[2], split[3])) {
                                    sendToClient(this, split[1] + "-success");//or"-fail"
                                } else {
                                    sendToClient(this, split[1] + "-fail");
                                }
                                break;
                            case "get-players"://mohannad:get-players
                                List<Player> list1 = getOnlinePlayers();
                                String response1 = split[1] + "-success";
                                for (Player p : list1) {
                                    response1 += ":" + p.getName();
                                }
                                if (list1.size() != 0) {
                                    sendToClient(this, response1);
                                } else {
                                    sendToClient(this, split[1] + "-fail");
                                }
                                break;
                            case "challenge"://mohammed:challenge:mohannad
                                sendToClient(split[2], "invite:" + split[0]);
                                break;//order:username:data  
                            case "accept-challenge"://mohannad:accept-challenge:mohammed
                                sendToClient(split[2], "accepted-invite:" + split[0]);
                                changeStatusOfPlayer(split[0], "In-game");
                                changeStatusOfPlayer(split[2], "In-game");
                                break;
                            case "leave-game"://mohannad:accept-challenge:mohammed
                                sendToClient(split[2], "leave:nothing");
                                break;
                            case "store-game"://mohannad:store-game:playername:open:plsymb:opSymb:date:winningSymb:record
                                //sendToClient(split[2], "accepted-invite:" + split[0]);
                                DataBaseAccessLayer dao=new DataBaseAccessLayer();
                                boolean b = true;
                                if(split[8].trim().equals("false")){
                                    b = false;
                                }
                                dao.insertGame(split[2], split[3], split[4], split[5], split[6], split[7], b);
                                
                                break;
                            case "game-turn"://mohannad:game-turn:ahmed:1,2,X:1,2,X:1,2,X:1,2,X
                                //game-turn:ali:0,0,X
                                
                                String req = "your-turn";
                                for(int i=3;i<split.length;i++){
                                    req+= ":"+split[i];//your-turn:1,2,X:1,2,X:1,2,X:1,2,X
                                }
                                System.out.println("game-turn => "+req);
                                
                                sendToClient(split[2], req);
                                break;
                            case "add-point:":
                               // DataBaseAccessLayer dao=new DataBaseAccessLayer();
                                 new DataBaseAccessLayer().updatePlayerScore(username,5);
                                 
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

    void test() {
        for (ChatHandler client : clients) {
            System.out.println("lol: " + client.username);
        }
    }

    void sendToClient(String username, String msg) {
        //System.out.println("username: " + username);
        //System.out.println("msg: " + msg);

        for (ChatHandler client : clients) {
            //System.out.println("send to: " + client.username);
            if (client.username.trim().equals(username.trim())) {
                System.out.println("sent");
                client.mouth.println(msg);
                client.mouth.flush();
            }
        }
    }

    void sendToClient(int id, String msg) {

        for (ChatHandler client : clients) {
            if (client.id == id) {
                client.mouth.println(msg);
                client.mouth.flush();
            }
        }
    }

    void sendToClient(ChatHandler client, String msg) {
        client.mouth.println(msg);
        client.mouth.flush();
    }

    void sendToAllClients(String msg) {
        System.out.println("num of clients on server: " + clients.size());
        for (ChatHandler client : clients) {
            //System.out.println(client.username);
            client.mouth.println(msg);
            client.mouth.flush();
        }
    }

    void closeHandler() {
        try {
            ear.close();
            mouth.flush();
            mouth.close();
            th.stop();
            clients.remove(this);
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


    private int getPlayerToken(String username) {
        return new DataBaseAccessLayer().getToken(username);
    }

    private String getPlayerNameByToken(int token) {
        return new DataBaseAccessLayer().getUsername(token);
    }

    private void sendChallenge(String username) {

    }

}
