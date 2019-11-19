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
    private static Boolean winner = false; //true if winner has been declared

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

    private boolean handChecker () {
        String currentHand = this.initialHand.get(ID);
        System.out.println(currentHand);
        if(currentHand.charAt(0) == currentHand.charAt(2) && currentHand.charAt(0) == currentHand.charAt(4) && currentHand.charAt(0) == currentHand.charAt(6)){
          return true;             
        }
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
            line = reader.readLine();
            if ("quit".equalsIgnoreCase(line)) {
                String msg = "Sad to see you go! :( Bye!\n";
                outputStream.write(msg.getBytes());
                break;
            }

            while ((numberOfPlayers - 1) != numberOfUsers && state == 2) {
                Thread.sleep(100);
            }

            //MAKE LOOP HERE - flag to see winner
            while(winner == false){
                



                String signal = "03\n";
                outputStream.write(signal.getBytes());
                System.out.println("Pass your cards!");
                String passed = reader.readLine().substring(2, 4);
                System.out.println("Player " + (ID + 1) + " just passed " + passed);
                passedList.set(ID, passed);
                String msg = "You, player #" + (ID + 1) + " sent me " + passed + "\n";
                outputStream.write(msg.getBytes());


                submitted++;
                if(submitted == numberOfPlayers){
                    System.out.println("ALL PLAYERS PASSED");
                    for (int i=0; i<numberOfPlayers; i++){
                        Card hand = hands.get(i); //current hand of player(i)
                        String passedValue = passedList.get(i); //passed card is saved in passedValue

                        char[] cardString = hand.cardString;
                        String handString = new String(cardString);
                        int index = handString.indexOf(passedValue);

                        System.out.println(cardString[index] + "" + cardString[index+1]);
                        int nextPlayerIndex = (i+1) % numberOfPlayers;
                        String nextpassedValue = passedList.get(nextPlayerIndex);
                        cardString[index] = nextpassedValue.charAt(0);
                        cardString[index+1] = nextpassedValue.charAt(1);

                        handString = new String(cardString);
                        System.out.println(handString);
                        
                        System.out.println("Player "+ i + ": " + nextpassedValue);
                        System.out.println("Player "+ i + ": " + handString);

                        // String cardStr = String.valueOf(cardString);
                        Card newHand = new Card(handString);
                        hands.set(i,newHand);

                        // hand.displayCards();


                    }

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
        clientSocket.close();
    }

}
