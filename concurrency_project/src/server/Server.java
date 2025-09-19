package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    public static final int PORT = 8080;
    public static LinkedList<ClientHandler> serverList = new LinkedList<>(); // список всех нитей
    private static ServerStats stats;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        stats = new ServerStats();
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {
                    serverList.add(new ClientHandler(socket, serverList.size(), stats)); // добавить новое соединенние в список
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его при завершении работы:
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
