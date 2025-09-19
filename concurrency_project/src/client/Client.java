package client;

import util.ClientAction;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {

    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedReader in;
    private BufferedWriter out;

    @Override
    public void run() {
        try {
            try {
                System.out.println("Thread started: " + this.getName());
                clientSocket = new Socket("localhost", 8080);
                System.out.println("Client connected in thread: " + this.getName());
                System.out.println("Socket: " + clientSocket);
                reader = new BufferedReader(new InputStreamReader(System.in));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                while (true) {

                    out.write(ClientAction.ATTACK.ordinal() + "\n");
                    out.flush();
//                    String serverWord = in.readLine();
//                    System.out.println("From server: " + serverWord);
                    Thread.sleep(1000);

                    out.write(ClientAction.MOVE.ordinal() + "\n");
                    out.flush();
//                    serverWord = in.readLine();
//                    System.out.println("From server: " + serverWord);
                    Thread.sleep(1000);

                    out.write(ClientAction.BUY.ordinal() + "\n");
                    out.flush();
//                    serverWord = in.readLine();
//                    System.out.println("From server: " + serverWord);
                    Thread.sleep(1000);
                }
            } finally {
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }
    }

}
