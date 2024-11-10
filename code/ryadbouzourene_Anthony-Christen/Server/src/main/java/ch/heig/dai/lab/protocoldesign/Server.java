/**
 * Server class that handles client connections to process mathematical operations
 * over a TCP connection. The server listens on a specific port, parses client
 * commands, performs operations, and sends responses back to te client.
 *
 * @Authors Bouzourène Ryad & Christen Anthony
 * @Date    2024-11-08
 */
package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Server {
    // ------------------------------------------------------------------------------
    // Attributes
    // ------------------------------------------------------------------------------
    private final int SERVER_PORT = 4444;
    private final Charset ENCODING = StandardCharsets.UTF_8;
    private final int INACTIVITY_TIMEOUT = 5 * 60 * 1000; // milliseconds

    // ------------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------------
    /**
     * Main method to start a server instance.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    /**
     * Starts the server and listens for client connections.
     * For each client connection, it creates a new socket and calls handleClient().
     */
    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    handleClient(socket);
                } catch (IOException e) {
                    printMessage("Socket: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            printMessage("Server socket: " + e.getMessage());
        }
    }

    /**
     * Handles communication with a connected client, parsing commands and
     * responding with operations results.
     * @param socket The client socket.
     * @throws IOException If an I/O error occurs during communication.
     */
    private void handleClient(Socket socket) throws IOException {
        socket.setSoTimeout(INACTIVITY_TIMEOUT);

        var in = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), ENCODING)
        );

        var out = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), ENCODING)
        );

        out.write(generateWelcomeMessage());
        out.flush();

        String clientCommand;

        try {
            while ((clientCommand = in.readLine()) != null) {
                String[] commandArgs = clientCommand.trim().split(" ");

                if (commandArgs[0].equalsIgnoreCase("EXIT")) {
                    out.write(Message.GOODBYE.formatMessage());
                    out.flush();
                    break;
                }

                double result;

                try {
                    BinaryOperation operation = parseOperation(commandArgs[0]);
                    evaluateNbOperands(commandArgs, 2);

                    double operand1 = parseOperand(commandArgs[1]);
                    double operand2 = parseOperand(commandArgs[2]);

                    result = operation.apply(operand1, operand2);
                } catch (IllegalArgumentException e) {
                    out.write(e.getMessage());
                    out.flush();
                    continue;
                }

                out.write(Message.RESULT.formatMessage(Double.toString(result)));
                out.flush();
            }
        } catch (SocketTimeoutException e) {
            out.write(Message.GOODBYE.formatMessage());
            out.flush();
            printMessage("Socket: Connexion closed due to inactivity");
        }
    }

    /**
     * Prints a message to the server console with a "[Server]" prefix.
     * @param message The message to print.
     */
    private static void printMessage(String message) {
        System.out.println("[Server] " + message);
    }

    /**
     * Generates a welcome message, listing available operations.
     * @return The welcome message.
     */
    private static String generateWelcomeMessage() {
        StringBuilder result = new StringBuilder("Welcome ! Here is the list of the available operations :\n");

        for (BinaryOperation operation : BinaryOperation.values()) {
            result.append("\t- ").append(operation.getFormat()).append("\n");
        }

        result.append("\n");

        return result.toString();
    }

    /**
     * Parses a command string to retrieve the corresponding operation.
     * @param operation The name of the operation.
     * @return The corresponding operation.
     * @throws IllegalArgumentException If the operation name is unsupported.
     */
    private static BinaryOperation parseOperation(String operation) {
        try {
            return BinaryOperation.fromString(operation);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(Message.ERROR.formatMessage(
                    "Unsupported operation '" + operation + "'" ));
        }
    }

    /**
     * Parses an operand string and converts it to a double.
     * @param operand The operand as a string.
     * @return The operand converted to a double.
     * @throws IllegalArgumentException If the operand is invalid.
     */
    private static double parseOperand(String operand) {
        try {
            return Double.parseDouble(operand);
        } catch (Exception e) {
            throw new IllegalArgumentException(Message.ERROR.formatMessage(
                    "Invalid operand '" + operand + "'"));
        }
    }

    /**
     * Validates that the number of operands provided matches the expected amount.
     * @param args       The command arguments array.
     * @param nbExpected The expected number of operands.
     * @throws IllegalArgumentException If the number of operands is incorrect.
     */
    private static void evaluateNbOperands(String[] args, int nbExpected) {
        int nbOperands = args.length - 1;

        if (nbOperands != nbExpected) {
            throw new IllegalArgumentException(Message.ERROR.formatMessage("Wrong " +
                    "number of operands. Expected " + nbExpected + " but got " + nbOperands));
        }
    }
}
