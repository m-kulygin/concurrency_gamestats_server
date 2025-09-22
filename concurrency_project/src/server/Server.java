package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    public static final int PORT = 8080;
    public static LinkedList<ClientHandler> serverList = new LinkedList<>();
    private static ServerStats stats;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        stats = new ServerStats();
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new ClientHandler(socket, serverList.size(), stats));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
