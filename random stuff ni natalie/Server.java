// File Name Server.java

import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread
{
   private ServerSocket serverSocket;
   private HashMap<Integer, String> users = new HashMap<Integer, String>();
   private Integer numberOfUsers=1;
   private Integer numberOfPlayers;

   public Server(Integer port, Integer numberOfPlayers) throws IOException
   {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(20000); //20sec
      this.numberOfPlayers = numberOfPlayers;
   }

   public void addUser(String Address){
     System.out.println("Set " + Address + " to number " + Integer.toString(this.numberOfUsers));
     this.users.put(this.numberOfUsers,Address+"");
     this.numberOfUsers++;
   }
   public Integer getNumberOfPlayers(){
     return this.numberOfPlayers;
   }
   public Integer getNumberOfUsers(){
     return this.numberOfUsers;
   }
   public HashMap<Integer, String> getUsers(){
     return this.users;
   }


   public void run()
   {
      while(true)
      {
         try
         {
           //Getting N number of Players
            System.out.println("Waiting for " + (getNumberOfPlayers()-getNumberOfUsers()+1) +" players on port "+ serverSocket.getLocalPort() + "...");
            if((getNumberOfUsers()-1) == getNumberOfPlayers()){
              System.out.println("okay na");
              for (Map.Entry<Integer, String> entry : getUsers().entrySet()) {
                  System.out.println(entry.getKey() + ":" + entry.getValue().toString());
              }
              //same but different method getUsers().forEach((key, value) -> System.out.println(key + ":" + value));
            }

            /* Start accepting data from the ServerSocket */
            Socket server = serverSocket.accept();
            String wow = "" + server.getRemoteSocketAddress();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            addUser(wow);
            System.out.println("check");

            System.out.println(new DataInputStream(server.getInputStream()).readUTF());
            /* Read data from the ClientSocket */
            System.out.println(new DataInputStream(server.getInputStream()).readUTF());
            DataInputStream in2 = new DataInputStream(server.getInputStream());
            System.out.println(in2.readUTF());

            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            System.out.println("he sending to: " + server.getOutputStream());

            /* Send data to the ClientSocket */
            out.writeUTF("Thank you"+ wow + "for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();

         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   public static void main(String [] args)
   {
      int port = Integer.parseInt(args[0]);
      int numberOfPlayers = Integer.parseInt(args[1]);
      try
      {
         Thread t = new Server(port, numberOfPlayers);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
