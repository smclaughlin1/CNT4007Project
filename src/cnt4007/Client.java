package cnt4007;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

// Client for each peer
// Will connect to the peers that have started previously
public class Client {
    private static class ServerNotReadyException extends Exception {
        public ServerNotReadyException(String message) {
            super(message);
        }
    }

    // This allows each client connection instance to run as its own thread
    // This allows a 'single client' to connect to multiple different servers
    private static class ClientConnection implements Runnable {
        private final String host;
        private final int port;
        private final String clientId;
        // Reference to store exceptions that occur during connection
        private final AtomicReference<Exception> exceptionRef;

        // Constructor
        public ClientConnection(String host, int port, String clientId, AtomicReference<Exception> exceptionRef) {
            this.host = host;
            this.port = port;
            this.clientId = clientId;
            this.exceptionRef = exceptionRef;
        }

        @Override
        public void run() {
            try {

                // Establish connectin with server
                Socket socket = new Socket(host, port);
                System.out.println("Connected to the server");

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                String serverMessage;

                // Handshake sent by client
                writer.println("P2PFILESHARINGPROJ0000000000" + clientId);
                serverMessage = reader.readLine();

                // Checking if this was the expected server
                if (!serverMessage.equals("P2PFILESHARINGPROJ00000000001001")) {
                    System.out.println("Received: " + serverMessage + "\n Expecting: P2PFILESHARINGPROJ0000000000" + clientId);
                }

                Date time = new Date();
                String serverID = serverMessage.substring(28);
                System.out.println("[" + time + "] Peer " + clientId + " makes a connection to Peer " + serverID);
                System.out.println("Received: " + serverMessage);

                while ((serverMessage = userInput.readLine()) != null) {

                    // TODO: Connect to Message.java
                    writer.println(serverMessage);
                    String response = reader.readLine();
                    System.out.println("Server says: " + response);
                    if (serverMessage.equals("exit")) break;
                }

                socket.close();

            }catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                // Sets the custom "ServerNotReadyException"
                // Client is connecting to server that has not been started yet
                exceptionRef.set(new ServerNotReadyException("Server is not ready yet."));
            }
        }
    }

    public static void main(String[] args) {

        // TODO: Create a for loop to connect to all peers that were started prior

        // Define how many times you want to retry
        final int MAX_RETRIES = 5;
        // Wait 5 seconds before retrying
        final int RETRY_DELAY_MS = 5000;

        String host = "localhost";

        // port will need to be configurable
        // will be set by parameters most likely
        int port = 4444;
        int retryCount = 0;
        AtomicReference<Exception> exceptionRef = new AtomicReference<>(null);
        // Handle possible delays in previous peer servers starting up
        while (retryCount < MAX_RETRIES) {

                // Starts thread connection
                // Will start ClientConnection for each peer client as they connect to servers
                Thread connectionThread = new Thread(new ClientConnection(host, port, args[0],exceptionRef));
                connectionThread.start();
                try {
                    // Waits for connection to finish
                    connectionThread.join();
                }   catch (InterruptedException ex) {
                    System.out.println("Thread was interrupted: " + ex.getMessage());
                    return;
                }

                // Checks if the thread has an exception
                // Will retry is an exception is found after 5 secs
                // until it connects or MAX_RETRIES has been reached
                Exception caughtException = exceptionRef.get();
                if (caughtException instanceof ServerNotReadyException) {
                    System.out.println(caughtException.getMessage());
                    retryCount++;

                    if (retryCount < MAX_RETRIES) {
                        System.out.println("Retrying to connect in " + RETRY_DELAY_MS + "ms...");
                        try {
                            Thread.sleep(RETRY_DELAY_MS);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(ie);
                        }
                    } else {
                        System.out.println("Max retries reached. Exiting...");
                    }
                } else if (caughtException != null) {
                    System.out.println("Unexpected error: " + caughtException.getMessage());
                    return;
                } else {
                    break;
                }
            }
        }
    }
}
