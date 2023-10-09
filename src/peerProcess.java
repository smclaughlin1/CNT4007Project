import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class peerProcess {
    public int numberOfPreferredNeighbors;
    public int unchokingInterval;
    public int optimisticUnchokingInterval;
    public String fileName;
    public int fileSize;
    public int pieceSize;

    // Constructor to initialize from the file
    public peerProcess(String configFilePath) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(configFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[0].equals("NumberOfPreferredNeighbors")) {
                    numberOfPreferredNeighbors = Integer.parseInt(parts[1]);
                } else if (parts[0].equals("UnchokingInterval")) {
                    unchokingInterval = Integer.parseInt(parts[1]);
                } else if (parts[0].equals("OptimisticUnchokingInterval")) {
                    optimisticUnchokingInterval = Integer.parseInt(parts[1]);
                } else if (parts[0].equals("FileName")) {
                    fileName = parts[1];
                } else if (parts[0].equals("FileSize")) {
                    fileSize = Integer.parseInt(parts[1]);
                } else if (parts[0].equals("PieceSize")) {
                    pieceSize = Integer.parseInt(parts[1]);
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            peerProcess config = new peerProcess("Common.cfg");
            System.out.println("Number Of Preferred Neighbors: " + config.numberOfPreferredNeighbors);
            // ... You can print other fields similarly
        } catch (IOException e) {
            System.out.println("Error reading the configuration file: " + e.getMessage());
        }
    }
}