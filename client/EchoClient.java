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
          Socket server = new Socket(serverName, port);
            InputStream inputStream = server.getInputStream();
            OutputStream outputStream = server.getOutputStream();

            System.out.println("Client started");
//    Socket soc = new Socket("localhost", 8818);
            System.out.println("Connecting to " + serverName + " on port " + port);

            /* Send data to the ServerSocket */
            System.out.println("Just connected to " + server.getRemoteSocketAddress());

            PrintWriter out = new PrintWriter(outputStream, true); //true autoflush data, immediately send import junit.framework.TestCase;
//    Thread.sleep(10000);

            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            String cardString = in.readLine();
            System.out.println("Cards from server:" + cardString + "\n");
            new Card(cardString+"");

            //for reading from server
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            //for reading from terminal
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
          outputStream.write("what\n".getBytes());
            while ((line = reader.readLine())!= null) {

                if(line.equals("03")){
                  System.out.println("Enter a card: \n");
                  String str = userInput.readLine() + "\n";
                  str = "04"+str;
                  outputStream.write(str.getBytes());
                  System.out.println(in.readLine());
                }else{
                  out.println("START\n");
                  System.out.println(line);
                }




            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
