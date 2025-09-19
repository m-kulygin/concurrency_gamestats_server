package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private int id;
    private ServerStats serverStats;

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
                if (word.equals("stop")) {
                    break;
                }
                System.out.println("Client " + id + ": " + word);
                // !!! СИНХРОНИЗИРОВАННО ОБНОВЛЯЕМ СТАТИСТИКУ
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
