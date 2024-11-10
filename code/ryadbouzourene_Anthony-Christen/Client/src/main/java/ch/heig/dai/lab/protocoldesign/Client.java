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
import java.util.Scanner;

public class Client {

    private final int SERVER_PORT = 4444;
    private final Inet4Address SERVER_IP ;
    private final Charset ENCODING = StandardCharsets.UTF_8;

    public Client() throws UnknownHostException {
        // Initialise SERVER_IP avec l'adresse IP souhaitée
        SERVER_IP = (Inet4Address) InetAddress.getByName("10.193.24.113"); 
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

    private void run() {
        try(Socket socket = new Socket(SERVER_IP,SERVER_PORT);
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream(),ENCODING));
            var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), ENCODING));
            var scanner = new Scanner(System.in)){
            
             
            String sendingLine = "";
            String receptingLine ="";

            //message de bienvenue 
            for(int i = 0 ; i < 3 ; ++i){
                receptingLine=in.readLine();
                System.out.println(receptingLine);
            }
            
            while(true){

                sendingLine = scanner.nextLine();
                out.write(sendingLine + "\n");
                out.flush();

                receptingLine = in.readLine();
                System.out.println(receptingLine);
                if(receptingLine.equalsIgnoreCase("GOODBYE")){
                    break;
                }

           }

            
            
        } catch (IOException e) {
            System.out.println("Client: exc.: " + e);
        }

    }
}