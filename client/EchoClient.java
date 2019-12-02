package client;

import java.net.*;
import java.io.*;

import gameUi.Card;
import gameUi.Instructions;

public class EchoClient {

    private static boolean strChecker(String input) {
        if(input.length()!=5) return false;
        else return true;
    }

    public static void main(String[] args) {

        Instructions instruction = new Instructions(); //strings of instruction
        try {

            String serverName = args[0]; //getting ip address of server
            int port = 8818; //default port

            //setting up input output streams from server
            Socket server = new Socket(serverName, port);
            InputStream inputStream = server.getInputStream();
            OutputStream outputStream = server.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            //buffered reader for input of user
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            //printing successful connection
            System.out.println("Connecting to " + serverName + " on port " + port);
            System.out.println("Just connected to " + server.getRemoteSocketAddress());

            PrintWriter out = new PrintWriter(outputStream, true); //true auto flush data, immediately send import junit.framework.TestCase; I read that this was a good practice

            //RECEIVING INITIAL CARDS FROM SERVER
            String cardString = reader.readLine();
            System.out.println(instruction.clientGreetings); //***Greet Client***
            System.out.println(instruction.clientInstructions); //**Tell Client Instructions**

            //for reading from server
            String line; //from iostream
            String userInputCard; //from user buffer
            String newCards; //cards sent by server
            String signal; //signals sent TO server
            //while there is something being sent to client
            while ((line = reader.readLine()) != null) {

                if (line.equals("03")) { //O3 Signifies to *PASS* a card
                    newCards = new String(reader.readLine()); //get cards from server
                    new Card(newCards + ""); //display these cards
                    System.out.println(instruction.clientSelectionInstruction); //**Tell Client how to select a card**

                    //get card from user
                    System.out.print("Card: ");
                    userInputCard = userInput.readLine() + "\n";
                    userInputCard = "04" + userInputCard;

                    //check if user input length is valid
                    while(!strChecker(userInputCard)){
                        System.out.println(instruction.inputLengthError);
                        System.out.print("Card: ");
                        userInputCard = userInput.readLine() + "\n";
                        userInputCard = "04" + userInputCard;
                    }

                    //send card to server
                    outputStream.write(userInputCard.getBytes());

                    //read reply of server
                    String reply = reader.readLine();
                    if (reply.equals("09")) { //O9 means the game is over :(
                        System.out.println(instruction.clientYouLost);
                        break;
                    } else {
                        System.out.println(reply); // server tells you what card you sent
                    }


                } else if (line.equals("07")) { //O7 Signifies all of your cards are the same!
                    newCards = new String(reader.readLine()); //Server also sends your cards to prove they're all the same
                    System.out.println(newCards);
                    new Card(newCards + ""); //display your cards

                    System.out.println(instruction.clientPressEnterInstruction); //**Tell client to press enter fast to win**
                    String str2 = userInput.readLine() + "\n"; //buffer waits for user to enter
                    signal = "07\n";
                    outputStream.write(signal.getBytes()); //send to server you pressed enter
                    System.out.println(reader.readLine()); // verdict from SERVER whether you won or not
                    System.out.println(reader.readLine()); //3 lines kasi yung verdict
                    System.out.println(reader.readLine()); //
                    break;
                } else {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
