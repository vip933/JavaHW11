package Zappy.java.bse208.hw5;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter your nickname: ");
            String nickname = br.readLine();

            try (Socket socket = new Socket(ServerInfo.HOST, ServerInfo.PORT);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {

                // Sending name to the server.
                // Can be implemented to login/password check.
                out.writeUTF(nickname);
                out.flush();

                // Was user accepted by the server?
                Thread.sleep(2000);
                if (in.readBoolean()) {
                    System.out.println("Sorry, this user is already in the chat!");
                    return;
                } else {
                    System.out.println("Successful login!");
                }

                // Reading new messages from user.
                while (!socket.isOutputShutdown()) {
                    System.out.print(nickname + ", enter your message: ");
                    String clientCommand = br.readLine();
                    // Sending it to the server.
                    out.writeUTF(nickname + ": " + clientCommand);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("Problems while reading message. Check Client.java");
        } catch (InterruptedException e) {
            System.err.println("Idk wtf");
        }
    }
}
