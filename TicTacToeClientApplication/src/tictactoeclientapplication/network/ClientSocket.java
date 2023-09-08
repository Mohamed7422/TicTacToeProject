/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclientapplication.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoeclientapplication.utils.OnResponse;

public class ClientSocket {

    private static ClientSocket instance;

    private static boolean isConnected;

    private static Socket sc;
    private static DataInputStream ear;
    private static PrintStream mouth;
    private static Thread thread;
    private OnResponse onResponse;

    private ClientSocket() {
    }

    public static ClientSocket getInstance() {
        if (ClientSocket.instance == null) {
            ClientSocket.instance = new ClientSocket();
        }

        return ClientSocket.instance;
    }

    public void openConnection() throws IOException {
        try {

            //sc = new Socket("192.168.137.1", 5000);
            sc = new Socket("192.168.1.9", 5000);

            ear = new DataInputStream(sc.getInputStream());
            mouth = new PrintStream(sc.getOutputStream());
            System.out.println("client: connected");

            startListen();
        } catch (IOException ex) {
            throw ex;
        }
    }

    public void startListen() {
        thread = new Thread(()-> {
            try {
                while (true) {
                    String recievedMsg = ear.readLine();
                    this.onResponse.onResponse(recievedMsg);
                    if (recievedMsg == null) {
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println("listening error");
            }finally{
                closeEveryThing();
                System.out.println("connection closed");
            }
        });
        thread.start();
    }

    public void say(String msg, OnResponse onResponse) {
        mouth.println(msg);//login:moha:1235
        this.onResponse = onResponse;
    }

    public boolean isConnected() {
        if (sc != null) {
            return !(sc.isClosed());
        }
        return false;
    }

    public void closeEveryThing() {
        try {
            ear.close();
            mouth.flush();
            mouth.close();
            thread.stop();
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
