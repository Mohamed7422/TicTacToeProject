package tictactoeclientapplication.network;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import tictactoeclientapplication.utils.OnResponse;

public class ClientSocket {

    private static ClientSocket instance;

    private static Socket sc;
    public DataInputStream ear;
    private PrintStream mouth;
    private static Thread thread;
    private OnResponse onResponse;

    private ClientSocket() {
    }

    public static ClientSocket getInstance() {
        if (instance == null) {
            System.out.println("ClientSocket: new instance");
            instance = new ClientSocket();
        }
        return instance;
    }

    public void openConnection() throws IOException {
        //ProgressIndicatorClass.show();
        try {
            //sc = new Socket("192.168.1.3", 5000);
            //sc = new Socket("192.168.1.9", 5000);
            //sc = new Socket("156.196.113.64", 5000);
            sc = new Socket("10.175.240.234", 5000);
            ear = new DataInputStream(sc.getInputStream());
            mouth = new PrintStream(sc.getOutputStream());
            mouth.println(getUsername());
            System.out.println("ClientSocket: connected");
            startListen();
        } catch (IOException ex) {
            System.out.println("ClientSocket: can't connect");
            throw ex;
        }
    }

    private void startListen() {
        thread = new Thread(() -> {
            try {
                System.out.println("ClientSocket: start listening");
                while (true) {
                    String recievedMsg = ear.readLine();
                    this.onResponse.onResponse(recievedMsg);
                    if (recievedMsg == null) {
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println("ClientSocket:  can't listening");
                //throw ex;
            } finally {
                closeEveryThing();
                System.out.println("ClientSocket: final listening");
            }
        });
        thread.start();
    }

    public void say(String msg, OnResponse onResponse) {
        System.out.println("ClientSocket: say " + getUsername() + ":" + msg);
        mouth.println(getUsername() + ":" + msg);
        this.onResponse = onResponse;
    }

    public void say(String msg) {
        System.out.println("ClientSocket: say without waiting response");
        mouth.println(getUsername() + ":" + msg);
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
            System.out.println("ClientSocket: close everything");
        } catch (IOException ex) {
            System.out.println("ClientSocket: can't close everything");
        }
    }

    public static String getUsername() {
        String authed = "no-name";
        File f = new File("auth.txt");
        FileInputStream ear = null;
        try {
            ear = new FileInputStream(f);
            byte[] b = new byte[ear.available()];
            ear.read(b);
            if (new String(b).split(":")[0].equals("logedin")) {
                authed = new String(b).split(":")[1].trim();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        } catch (IOException ex) {
            System.out.println("IOException");
        } finally {
            try {
                ear.close();
            } catch (IOException ex) {
                System.out.println("IOException");
            }
            return authed;
        }
    }

}
