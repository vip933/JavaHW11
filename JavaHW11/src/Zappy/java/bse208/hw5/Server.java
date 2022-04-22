package Zappy.java.bse208.hw5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final ExecutorService executeIt = Executors.newFixedThreadPool(10);
    private static final Set<String> names = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(ServerInfo.PORT)) {
            while (!server.isClosed()) {
                Socket client = server.accept();

                // Do not put it in try with resources!!!
                // Closed in other place!!!
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());

                String name = in.readUTF();
                if (names.add(name)) {
                    // Indicates that there was no problem.
                    out.writeBoolean(false);
                    out.flush();
                    executeIt.execute(new ClientHandler(client));
                    System.out.println(name + " connected!");
                } else {
                    // Indicates that there was a problem.
                    out.writeBoolean(true);
                    out.flush();
                    client.close();
                    System.out.println("This name is already present: " + name);
                }
            }
            executeIt.shutdown();
        } catch (IOException e) {
            System.err.println("Problem while receiving message on server. Check Server.java");
        }
    }
}