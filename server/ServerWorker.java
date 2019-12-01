package server;

import gameUi.Card;
import gameUi.Instructions;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerWorker extends Thread {

    //---shared by threads---
    private static Integer numberOfPlayers; //how many should be playing
    private static Integer numberOfUsers; //how many are currently connected

    private static List<Card> hands = new ArrayList<Card>(); //all player cards
    private static List<String> passedList = new ArrayList<String>(List.of("Hello", "world", "JAVA EVIL")); //cards passed by player (in order)
    private static Integer submitted = 0; //how many players submitted cards in that round
    private static Integer state;
    private static List<Integer> winners = new ArrayList<Integer>(); //players who pressed enter when they had all the same rank of cards

    //---client specific---
    private final Socket clientSocket;
    private final int ID;
    private final List<String> initialHand; //initial hand distributed by the server



    ServerWorker(Socket clientSocket, List<String> initialHand1, int ID, Integer numberOfPlayers1, Integer numberOfUsers1, Integer state1, List<Card> hands1) {
        numberOfPlayers = numberOfPlayers1;
        numberOfUsers = numberOfUsers1;
        hands = hands1;
        state = state1;

        this.clientSocket = clientSocket;
        this.ID = ID;
        this.initialHand = initialHand1;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //checks if all ranks of the card is the same
    private boolean winChecker(String card) {
        for (int i = 0; i < 8; i += 2) {
            if (!(card.charAt(0) == card.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    //checks if the card exists within the players hand
    private boolean cardChecker(String card, int id) {
        boolean suit = false;
        boolean rank = false;

        String cardString = new String(hands.get(id).getCardString());
        card = card.toUpperCase();

        for (int i = 0; i < 8; i += 2) {
            if (card.charAt(0) == cardString.charAt(i)) rank = true;
            if (card.charAt(1) == cardString.charAt(i + 1)) suit = true;
            if (rank && suit) {
                return true;
            }
        }
        return false;
    }

    //main bits
    private void handleClientSocket() throws IOException, InterruptedException {
        Instructions instruction = new Instructions();

        //setting up input output streams to client
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


        String signal; //Signals sent to client
        String playerHand; //clients hand

        //list of string make into single string initial hand
        StringBuilder sb = new StringBuilder();
        for (String s : initialHand) {
            sb.append(s);
        }
        String cards = sb.toString() + "\n";

        //SEND initial cards to client
        outputStream.write(cards.getBytes());

        //wait until all players have connected
        while ((numberOfPlayers - 1) != numberOfUsers && state == 2) {
            Thread.sleep(100);
        }

        while (true) {
            playerHand = new String(hands.get(ID).cardString);
            playerHand = playerHand + "\n"; //add new line since the function at client is .readline()

            // CASE 1: PLAYER HAS ALREADY COMPLETED HIS CARDS
            if (winChecker(playerHand)) { //check if all cards are the same
                System.out.println(instruction.serverSomeoneCompleted(ID));
                signal = "07\n";
                outputStream.write(signal.getBytes()); //send to client alert that his cards all match
                outputStream.write(playerHand.getBytes()); //send to client his cards as proof
                if (reader.readLine().equals("07")) { //if client replies "07" add to winners
                    winners.add(ID);
                }
                if (winners.get(0) == ID) { //if client is the first one in the list he/she WON!
                    System.out.println(instruction.serverSomeoneWon(ID));
                    outputStream.write(instruction.congratulationsWinner.getBytes()); // TELL CLIENT !! CONGRATULATIONS FOR WINNING!!!!
                } else {
                    //CASE 2: PLAYER IS NOT THE FIRST TO COMPLETE HIS CARDS
                    outputStream.write(instruction.sorrySlowPoke.getBytes()); // TELL CLIENT He's a SLOW POKE :(
                }
                break;
            } else {
                //CASE 3: ALL PLAYERS HAVE NOT COMPLETED THEIR CARDS
                signal = "03\n";
                outputStream.write(signal.getBytes()); //send 03 meaning to pass cards
                outputStream.write(playerHand.getBytes()); // send cards

                //System.out.println(instruction.serverPassYourCards(ID)); //**Tell server that he requested to pass cards**

                String passed = reader.readLine().substring(2, 4).toUpperCase(); //receive passed card from client
                System.out.println("Player " + (ID + 1) + " just passed " + passed);

                while (!cardChecker(passed, ID)) { //if input card is wrong
                    String warning = instruction.wrongString(ID, passed);
                    outputStream.write(warning.getBytes()); //send warning to client

                    outputStream.write(signal.getBytes()); //send 03 again to tell client to pass
                    outputStream.write(playerHand.getBytes()); //send player hands again
                    System.out.println(instruction.serverPassYourCards(ID)); //**Tell server that he requested to pass cards**

                    passed = reader.readLine(); //read passed cards by client
                    passed = passed.substring(2, 4).toUpperCase(); //get substring
                    System.out.println("Player " + (ID + 1) + " just passed " + passed);
                }

                passedList.set(ID, passed); //put passed card into passedList according to ID number

                if (!(winners.isEmpty())) { //if someone has ALREADY WON tell client that he's a loser (eww)
                    signal = "09\n";
                    outputStream.write(signal.getBytes());
                    submitted ++;
                    break;
                }else{
                    String msg = "You, player #" + (ID + 1) + " sent me " + passed + "\n"; // if not remind client what they sent you
                    outputStream.write(msg.getBytes());
                }

                //ONLY ONE SERVER THEAD WILL DO THIS (THE ONE THAT LAST SUBMITTED
                submitted++;
                if (submitted == numberOfPlayers) {
                    System.out.println(instruction.allPlayersHavePassed); //**Tell server that all players have passed**
                    for (int i = 0; i < numberOfPlayers; i++) {
                        Card hand = hands.get(i); //get the cards of player i
                        String passedValue = passedList.get(i).toUpperCase(); //get the card they passed

                        char[] cardString = hand.cardString; //get string of cards of client
                        String handString = new String(cardString); //make it into a string kasi list of chars siya
                        int index = handString.indexOf(passedValue); //get index of passed card

                        int nextPlayerIndex = (i + 1) % numberOfPlayers; //to who it will be passed (so if 3 players si 3rd player will pass to 1)
                        String nextpassedValue = passedList.get(nextPlayerIndex); //passed card of next player
                        cardString[index] = nextpassedValue.charAt(0); //replace card passed with the card passed by next player //rank
                        cardString[index + 1] = nextpassedValue.charAt(1); //suit
                        System.out.println("Player " + Integer.toString(i+1) + " cards:\n");
                        hand.displayCards(); //display new cards
                    }
                    submitted = 0; //reset submission to 0
                }
                while (submitted != 0 && winners.isEmpty()) {
                    Thread.sleep(100); //wait for others
                }
            }


        }



    }

}
