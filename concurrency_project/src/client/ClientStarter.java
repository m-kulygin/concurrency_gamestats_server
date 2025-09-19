package client;

import java.util.Scanner;

public class ClientStarter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.close();

        for (int i = 0; i < n; i++) {
            new Client().start();
        }
    }

}
