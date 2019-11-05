package client;

import java.net.*;
import java.io.*;
import java.util.*;

import gameUi.Card;

public class EchoClient {
    public static void main(String[] args) {

        try {
            String serverName = "localhost";
            int port = 8818;

            System.out.println("Client started");
//    Socket soc = new Socket("localhost", 8818);
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            /* Send data to the ServerSocket */
            System.out.println("Just connected to " + client.getRemoteSocketAddress());

            PrintWriter out = new PrintWriter(client.getOutputStream(), true); //true autoflush data, immediately send import junit.framework.TestCase;
            out.println("received");
//    Thread.sleep(10000);

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String cardString = in.readLine();
            System.out.println("Cards from server:" + cardString + ". Enter card to pass.");
            new Card(cardString);


            while (true) {

                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter a string");
                String str = userInput.readLine();

                //sending to the server
                out.println("received");

                System.out.println(in.readLine());


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
