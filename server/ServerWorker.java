package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final List<String> initialHand;
    private final int ID;
    private static Integer numberOfPlayers;
    private static Integer numberOfUsers;

    public ServerWorker(Socket clientSocket, List<String> initialHand1, int ID, Integer numberOfPlayers1, Integer numberOfUsers1) {
        numberOfPlayers = numberOfPlayers1;
        this.clientSocket = clientSocket;
        this.initialHand = initialHand1;
        this.numberOfUsers = numberOfUsers1;
        this.ID = ID;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        //initial cards
        StringBuilder sb = new StringBuilder();
        for (String s : initialHand) {
            sb.append(s);
        }
        String cards = sb.toString() + "\n";
        outputStream.write(cards.getBytes());


        //chatstuff
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        // while((line = reader.readLine()) != null){
        while (true) {
            line = reader.readLine();
            if ("quit".equalsIgnoreCase(line)) {
                String msg = "Sad to see you go! :( Bye!\n";
                outputStream.write(msg.getBytes());
                break;
            }
            System.out.println("number of players" + numberOfPlayers + "number of users" + numberOfUsers);
            while ((numberOfPlayers - 1) != numberOfUsers) {
                Thread.sleep(100);
            }

            String signal = "03\n";
            outputStream.write(signal.getBytes());
            System.out.println("Pass your cards!");
            String passed = reader.readLine();
            System.out.println("Player " + (ID + 1) + " just passed " + passed);
            String msg = "You, player #" + (ID + 1) + " sent me " + passed + "\n";
            outputStream.write(msg.getBytes());

        }
        // for(int i=0; i<10; i++){
        //   outputStream.write(("Time now is" + new Date() + "\n").getBytes());
        //   Thread.sleep(1000);
        // }
        clientSocket.close();
    }

}
