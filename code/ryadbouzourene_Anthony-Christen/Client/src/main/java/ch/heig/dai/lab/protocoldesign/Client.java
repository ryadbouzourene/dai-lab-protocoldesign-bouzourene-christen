package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.net.UnknownHostException;

public class Client {

    private final int SERVER_PORT = 4444;
    private final Inet4Address SERVER_IP ;
    private final Charset ENCODING = StandardCharsets.UTF_8;

    public Client() throws UnknownHostException {
        // Initialise SERVER_IP avec l'adresse IP souhaitée
        SERVER_IP = (Inet4Address) InetAddress.getLocalHost(); 
    }

    public static void main(String[] args) {
        // Create a new client and run it
        
        try {
            Client client = new Client();
            client.run();

        } catch (UnknownHostException e) {
            System.out.println("Client error: " + e.getMessage());
        }
        
        
        
    }

    /**
     * 
     */
    private void run() {
        try(Socket socket = new Socket(SERVER_IP,SERVER_PORT);
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream(),ENCODING));
            var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), ENCODING));
            var scanner = new BufferedReader(new InputStreamReader(System.in, ENCODING))){
            
             
            String sendingLine = "";
            String receptingLine ="";

            //message de bienvenue 
            while((receptingLine=in.readLine()) != null ){
                if(receptingLine.isEmpty())break ;
                System.out.println(receptingLine);
            }
            
            outerloop:
            while(true){
                System.out.print("\n>");

                sendingLine = scanner.readLine();
                out.write(sendingLine + "\n");
                out.flush();

                receptingLine = in.readLine();
                String[] commandArgs = receptingLine.trim().split(" ");
                
                switch (commandArgs[0]) {

                    case "RESULT":
                        System.out.print("The result of the operation is : " + commandArgs[1]);
                        break;
                    case "ERROR" :
                        System.out.print("An error occured : ");
                        for (int i = 1; i < commandArgs.length; i++) {
                            System.out.print(commandArgs[i] + " ");
                        }
                        break ;
                    case "GOODBYE" :
                        System.out.println("The connexion has closed");
                        break outerloop ;
                    default:
                        System.out.print("Unknown message : ");
                        for (int i = 0; i < commandArgs.length; i++) {
                            System.out.print(commandArgs[i] + " ");
                        }
                        break;
                }
                
           }

            
            
        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }

    }
}