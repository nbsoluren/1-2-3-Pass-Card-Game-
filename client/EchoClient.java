package client;

import java.net.*;
import java.io.*;
import java.util.*;

import gameUi.Card;

public class EchoClient {
    public static void main(String[] args) {

        try {
            String serverName = args[0];
            int port = 8818;
            Socket server = new Socket(serverName, port);
            InputStream inputStream = server.getInputStream();
            OutputStream outputStream = server.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Client started");
//    Socket soc = new Socket("localhost", 8818);
            System.out.println("Connecting to " + serverName + " on port " + port);

            /* Send data to the ServerSocket */
            System.out.println("Just connected to " + server.getRemoteSocketAddress());

            PrintWriter out = new PrintWriter(outputStream, true); //true autoflush data, immediately send import junit.framework.TestCase;
//    Thread.sleep(10000);

            String cardString = reader.readLine();
            System.out.println("Hello player! The server will not distribute your initial pool of cards");
            System.out.println("Initial Pool of Cards from server:" + cardString + "\n");
            //new Card(cardString+"");

            System.out.println("The naming convention of the cards is first letter signifies the number (Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King) and the second letter signifies the suit (Spades, Hearts, Diamonds, Clubs)");

            System.out.println("-----Client Side Gameplay Tutorial-----");
            System.out.println("Once all the players have joined the game, the server side will direct the client side players to pass their cards.");
            System.out.println("The players will be asked to enter a card. The players should only choose from their list of cards.");
            System.out.println("To choose a card the player should input the card's number followed by its suit.");
            System.out.println("After choosing a card, the player is notified of the card that it passed");
            System.out.println("After all the players have passed, the player is notified of its current pool of cards. The player will have its card passed to the player on the right.");
            System.out.println("The player wins once he gets the four matching cards.");
            System.out.println();
            System.out.println("-----GAMEPLAY-----");
            System.out.println();

            //for reading from server
            String line;
            //for reading from terminal
//            outputStream.write("what\n".getBytes());
            while ((line = reader.readLine()) != null) {
                if (line.equals("03")) {
                    String newCards = new String(reader.readLine());
                    System.out.println(newCards);
                    new Card(newCards + "");
                    System.out.println("Player should choose a card to pass from the player's current pool of cards. To choose a card enter the card's number followed by its suit.");
                    System.out.println("Choose a card. Enter the card's number followed by its suit: \n");
                    String str = userInput.readLine() + "\n";
                    str = "04" + str;
                    outputStream.write(str.getBytes());
                    System.out.println(reader.readLine());

                } else if (line.equals("07")){
                  String newCards2 = new String(reader.readLine());
                  System.out.println(newCards2);
                  new Card(newCards2 + "");
                  System.out.println("You won! Hurry, press Enter before anyone else does!");
                  String str2 = userInput.readLine() + "\n";
                  String signal2 = "07\n";
                  outputStream.write(signal2.getBytes());
                  System.out.println(reader.readLine());
                  //server.close();
                } else if(line.equals("08")){
                  String newCards3 = new String(reader.readLine());
                  System.out.println(newCards3);
                  new Card(newCards3 + "");
                  System.out.println("YoUr A lOzEEEEErrrrr!oUr");
                }else {
                    out.println("START\n");
                    System.out.println(line);
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
