package server;

import gameUi.Card;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerWorker extends Thread {

    private final Socket clientSocket;
    private final List<String> initialHand;
    private final int ID;
    private static Integer numberOfPlayers;
    private static Integer numberOfUsers;
    private static Integer state;
    private static List<Card> hands = new ArrayList<Card>();
    private static List<String> passedList = new ArrayList<String>(List.of("Hello", "world", "JAVA EVIL"));
    private static Integer submitted = 0;

    public ServerWorker(Socket clientSocket, List<String> initialHand1, int ID, Integer numberOfPlayers1, Integer numberOfUsers1, Integer state1, List<Card> hands1) {
        numberOfPlayers = numberOfPlayers1;
        this.clientSocket = clientSocket;
        this.initialHand = initialHand1;
        this.numberOfUsers = numberOfUsers1;
        this.hands = hands1;
        this.ID = ID;
        this.state = state1;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean cardChecker(String card, int id) {
        boolean suit = false;
        boolean rank = false;

        String cardString = new String(hands.get(id).getCardString());
        card = card.toUpperCase();

        for(int i=0; i<8; i+=2) {
            if(card.charAt(0) == cardString.charAt(i)) rank = true;
            if(card.charAt(1) == cardString.charAt(i+1)) suit = true;
            if(rank && suit) {
                System.out.println("Match found!");
                return true;
            }
        }
        System.out.println("No match found.");
        return false;
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
//            line = reader.readLine();
//            if ("quit".equalsIgnoreCase(line)) {
//                String msg = "Sad to see you go! :( Bye!\n";
//                outputStream.write(msg.getBytes());
//                break;
//            }

            while ((numberOfPlayers - 1) != numberOfUsers && state == 2) {
                Thread.sleep(100);
            }

            while(true){
            String signal = "03\n";
            outputStream.write(signal.getBytes());
            String playerHand = new String(hands.get(ID).cardString);
            playerHand = playerHand + "\n";
            outputStream.write(playerHand.getBytes());
            System.out.println("Pass your cards!" + Integer.toString(ID+1));

            String passed = reader.readLine().substring(2, 4).toUpperCase();
            System.out.println("Player " + (ID + 1) + " just passed " + passed);
            boolean checker = cardChecker(passed,ID);
            while(checker != true) {
                String warning = "You, player #" + (ID + 1) + " don't have " + passed + "\n";
                outputStream.write(warning.getBytes());

                outputStream.write(signal.getBytes());
                outputStream.write(playerHand.getBytes());
                System.out.println("Pass your cards!" + Integer.toString(ID+1));

                passed = reader.readLine().substring(2, 4).toUpperCase();
                System.out.println("Player " + (ID + 1) + " just passed " + passed);
                checker = cardChecker(passed,ID);
            }

            passedList.set(ID, passed);

            String msg = "You, player #" + (ID + 1) + " sent me " + passed + "\n";
            outputStream.write(msg.getBytes());

            submitted++;
            if(submitted == numberOfPlayers){
                System.out.println("ALL PLAYERS PASSED");
                for (int i=0; i<numberOfPlayers; i++){
                    Card hand = hands.get(i);
                    String passedValue = passedList.get(i).toUpperCase();

                    char[] cardString = hand.cardString;
                    String handString = new String(cardString);
                    System.out.println(handString);
                    int index = handString.indexOf(passedValue);

                    System.out.println(cardString[index] + "" + cardString[index+1]);
                    int nextPlayerIndex = (i+1) % numberOfPlayers;
                    String nextpassedValue = passedList.get(nextPlayerIndex);
                    cardString[index] = nextpassedValue.charAt(0);
                    cardString[index+1] = nextpassedValue.charAt(1);
                    System.out.println(nextpassedValue);
                    System.out.println(cardString);
                    hand.displayCards();
                }
              submitted = 0;
            }
            while(submitted!=0){
                Thread.sleep(100);
            }
          }



        }
        // for(int i=0; i<10; i++){
        //   outputStream.write(("Time now is" + new Date() + "\n").getBytes());
        //   Thread.sleep(1000);
        // }
//        clientSocket.close();
    }

}
