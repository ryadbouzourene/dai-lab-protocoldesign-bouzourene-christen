package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.nio.charset.*;
import java.util.Arrays;

public class Client {
    private final int SERVER_PORT = 4444;
    private final String SERVER_IP ;
    private final Charset ENCODING = StandardCharsets.UTF_8;

    public Client() {
        SERVER_IP = "localhost";
    }

    public Client(String serverIpAddress) {
        SERVER_IP = serverIpAddress;
    }

    public static void main(String[] args) {
        // Create a new client and run it
        // Client client = new Client("127.0.0.1");
        Client client = new Client();
        client.run();
    }

    /**
     * 
     */
    private void run() {
        try (Socket socket = new Socket(SERVER_IP,SERVER_PORT)) {
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream(),ENCODING));
            var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), ENCODING));
            var scanner = new BufferedReader(new InputStreamReader(System.in, ENCODING));

            String sendingLine = "";
            String receptingLine ="";

            // Display welcome message
            while((receptingLine=in.readLine()) != null ){
                if (receptingLine.isEmpty()) break ;
                System.out.println(receptingLine);
            }

            while(true) {
                System.out.print("> ");

                sendingLine = scanner.readLine();
                out.write(sendingLine + "\n");
                out.flush();

                receptingLine = in.readLine();

                String[] responseParts = receptingLine.split(" ");
                String responseType = responseParts[0];
                String responseContent = String.join(" ", Arrays.copyOfRange(responseParts, 1, responseParts.length));
                
                switch (responseType) {
                    case "RESULT":
                        System.out.print("The result of the operation is : " + responseContent);
                        break;
                    case "ERROR" :
                        System.out.print("An error occured : " + responseContent);
                        break ;
                    case "GOODBYE" :
                        System.out.println("The connexion has closed");
                        return;
                    default:
                        System.out.print("Unknown message : " + receptingLine);
                        break;
                }

                System.out.println();
           }
        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }
    }
}