/**
 * Client class that connects to a server to send and receive messages over a TCP
 * connection. This client interacts with the server by sending commands, receiving
 * responses, and displaying them.
 *
  @Authors Bouzourène Ryad & Christen Anthony
  @Date    2024-11-08
 */

package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.nio.charset.*;
import java.util.Arrays;

public class Client {

    // ------------------------------------------------------------------------------
    // Attributes
    // ------------------------------------------------------------------------------
    private final int SERVER_PORT = 4444;
    private final String SERVER_IP;
    private final Charset ENCODING = StandardCharsets.UTF_8;

    // ------------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------------
    /**
     * Default constructor that sets the server IP to "localhost"
     */
    public Client() {
        SERVER_IP = "localhost";
    }

    /**
     * Constructor that allows specifying a custom server IP address
     */
    public Client(String serverIpAddress) {
        SERVER_IP = serverIpAddress;
    }

    public static void main(String[] args) {
        // Uncomment the following line to connect to a custom IP address
        // Client client = new Client("127.0.0.1");
        Client client = new Client();
        client.run();
    }

    /**
     * Method to initiate and handle client-server communication
     */
    private void run() {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), ENCODING));
            var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), ENCODING));
            var scanner = new BufferedReader(new InputStreamReader(System.in, ENCODING));

            String sendingLine ;
            String receptingLine ;

            // Display the welcome message from the server until an empty line is received
            while ((receptingLine = in.readLine()) != null) {
                if (receptingLine.isEmpty()) break;
                System.out.println(receptingLine);
            }

            while (true) {
                System.out.print("> ");
                
                // Read user input, send it to the server, and flush the output
                sendingLine = scanner.readLine();
                out.write(sendingLine + "\n");
                out.flush();

                // Read the server's response
                receptingLine = in.readLine();

                // Split the response into type and content parts
                String[] responseParts = receptingLine.split(" ");
                String responseType = responseParts[0];
                String responseContent = String.join(" ", Arrays.copyOfRange(responseParts, 1, responseParts.length));

                // Handle the response based on its type
                switch (responseType) {
                    case "RESULT":
                        System.out.print("The result of the operation is : " + responseContent);
                        break;
                    case "ERROR":
                        System.out.print("An error occurred : " + responseContent);
                        break;
                    case "GOODBYE":
                        System.out.println("The connection has closed");
                        return; // Exit the program when server says goodbye
                    default:
                        System.out.print("Unknown message : " + receptingLine);
                        break;
                }

                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Client: Exception occurred: " + e);
        }
    }
}
