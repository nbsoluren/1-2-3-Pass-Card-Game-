package server;

import gameUi.Card;
import gameUi.Instructions;

import java.net.*;
import java.util.*;

public class ServerMain {
    private static HashMap<Integer, String> users = new HashMap<Integer, String>();
    private static List<Card> hands = new ArrayList<Card>();
    private static Integer numberOfPlayers;
    private static Integer numberOfUsers = 0;
    private static List<String> playableDeck = new ArrayList<String>();
    private static Integer state = 2;

    public static void setNumberOfPlayers(Integer nop) {
        numberOfPlayers = nop;
    }

    public static Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public static HashMap<Integer, String> getUsers() {
        return users;
    }

    public static void addUser(String address, List<String> initialCards) {
        System.out.println("User from" + address + " is now player#" + Integer.toString(getNumberOfUsers() + 1));
        users.put(getNumberOfUsers() + 1, address + "");
        hands.add(new Card(initialCards));
        numberOfUsers++;
    }

    public static void generatePlayableCards(Integer numberOfPlayers) {
        String[] cardRanks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};
        String[] cardSuits = {"D", "H", "S", "C"};
        String tempCard;
        for (int i = 0; i < numberOfPlayers; i++) {
            for (int j = 0; j < 4; j++) {
                playableDeck.add(cardRanks[i] + cardSuits[j]);
            }
        }
        Collections.shuffle(playableDeck);
       



        System.out.println("Shuffled Cards in play are:" + Arrays.toString(playableDeck.toArray()));
        System.out.println();
        System.out.println("The specified cards above are the cards available for playing.");
        System.out.println();
        System.out.println("The naming convention of the cards is first letter signifies the number (Ace, 2, 3, 4, 5, 6, 7, 8, 9, T, Jack, Queen, King) and the second letter signifies the suit (Spades, Hearts, Diamonds, Clubs).");
        System.out.println();
        System.out.println("-----Server Side Gameplay Tutorial-----");
        System.out.println("Once all the players have joined the game, server side will print 'Game about to start' and will direct the players to pass their cards.");
        System.out.println("Server side will print the initial cards of all the players and will wait until the players have passed their cards.");
        System.out.println("Each time a player has passed a card, the server side is notified of the card that the player passed. It will output the player's positon and the card that the player passed.");
        System.out.println("Once all the players have passed, the server side will show the current pool of cards that each player has.");
        System.out.println("Server side will instruct all the players to pass their cards until a winner has been found.");
        System.out.println();        
           
    }


    public static void main(String[] args) {
        //game initialization
        int port = 8818;
        setNumberOfPlayers(Integer.parseInt(args[0]));
        generatePlayableCards(getNumberOfPlayers());
        List<String> distributedCards;
        Instructions instructions = new Instructions();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (getNumberOfUsers() < getNumberOfPlayers()) {
                System.out.println("Waiting for " + (getNumberOfPlayers() - getNumberOfUsers()) + " more player/s on port " + serverSocket.getLocalPort() + "...");
                Socket clientSocket = serverSocket.accept();
                distributedCards = playableDeck.subList(0 + (4 * (getNumberOfUsers())), 4 * (getNumberOfUsers() + 1)); //SHUFFLING OF CARDS EY
                ServerWorker worker = new ServerWorker(clientSocket, distributedCards, getNumberOfUsers(),getNumberOfPlayers(),numberOfUsers, state, hands);
                worker.start();
                addUser(clientSocket.getRemoteSocketAddress() + "", distributedCards);

            }

            //Game about to START!
            System.out.println(instructions.gameStart); //**PRINT on server GAME START**
//            for (Map.Entry<Integer, String> entry : getUsers().entrySet()) {
//                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
//            }

            int playerNo = 1;
            for (Card card : hands) {
                System.out.println("Initial Cards of Player number: " + playerNo++);
                card.displayCards();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
