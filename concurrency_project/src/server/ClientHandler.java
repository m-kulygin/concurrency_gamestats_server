package server;

import util.ClientAction;

import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private int id;
    private final ServerStats serverStats;

    public ClientHandler(Socket socket, int id, ServerStats serverStats) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.id = id;
        this.serverStats = serverStats;
        start();
    }

    @Override
    public void run() {
        String word;
        try {
            while (true) {
                word = in.readLine();
                Time current = new Time(System.currentTimeMillis());
                if (word.equals("stop")) {
                    break;
                }
                int action_ordinal = Integer.parseInt(word);
                if (ClientAction.ATTACK.ordinal() == action_ordinal) {
                    synchronized (serverStats) {
                        serverStats.attackCount++;
                        System.out.println("Client " + id + " attacked at " + current);
                    }
                } else if (ClientAction.MOVE.ordinal() == action_ordinal) {
                    synchronized (serverStats) {
                        serverStats.moveCount++;
                        System.out.println("Client " + id + " moved at " + current);
                    }
                }
                else if (ClientAction.BUY.ordinal() == action_ordinal) {
                    synchronized (serverStats) {
                        serverStats.buyCount++;
                        System.out.println("Client " + id + " bought smth at " + current);
                    }
                }
                else {
                    System.out.println("Unknown command. Client " + id + ": " + word);
                }
                /*for (ClientHandler vr : Server.serverList) {
                    vr.send(word);
                }*/
            }

        } catch (IOException e) {
        }
    }

    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
