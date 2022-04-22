package Zappy.java.bse208.hw5;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static Socket clientDialog;

    public ClientHandler(Socket socket) {
        ClientHandler.clientDialog = socket;
    }

    @Override
    public void run() {
        try {
            // Do not put it in try with resources!!!
            // Closed in other place!!!
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();
                System.out.println(entry);
            }
            clientDialog.close();
        } catch (IOException e) {
            System.err.println("Problem while sending message from user to server. Check ClientHandler.java");
        }
    }
}