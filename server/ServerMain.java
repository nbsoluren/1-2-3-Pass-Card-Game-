package server;

import java.net.*;
import java.util.*;

public class ServerMain{
  private static HashMap<Integer, String> users = new HashMap<Integer, String>();
  private static Integer numberOfPlayers;
  private static Integer numberOfUsers=0;
  private static List<String> playableDeck = new ArrayList<String> ();

  public static void setNumberOfPlayers(Integer nop) {
    numberOfPlayers = nop;
  }
  public static Integer getNumberOfPlayers(){
    return numberOfPlayers;
  }
  public static Integer getNumberOfUsers(){
    return numberOfUsers;
  }
  public static HashMap<Integer, String> getUsers(){
    return users;
  }
  public static void addUser(Socket Address){
    System.out.println("Set " + Address + " to number " + Integer.toString(getNumberOfUsers()+1));
    users.put(getNumberOfUsers()+1,Address+"");
    numberOfUsers++;
  }

  public static void generatePlayableCards(Integer numberOfPlayers){
    String[] cardRanks = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    String[] cardSuits = {"D", "H", "S", "C"};
    String tempCard;
    for (int i = 0; i<numberOfPlayers; i++){
      for(int j = 0; j<4; j++){
        playableDeck.add(cardRanks[i]+cardSuits[j]);
      }
    }
    Collections.shuffle(playableDeck);
    System.out.println("Shuffled Cards in play are:" + Arrays.toString(playableDeck.toArray()));
  }



  public static void main(String[] args){
    //game initialization
    int port = 8818;
    setNumberOfPlayers(Integer.parseInt(args[0]));
    generatePlayableCards(getNumberOfPlayers());
    System.out.println(playableDeck.subList(0+(4*(getNumberOfUsers())),3*(getNumberOfUsers()+1)));

    try{
      ServerSocket serverSocket = new ServerSocket(port);
      while(getNumberOfUsers()<getNumberOfPlayers()){
        System.out.println("Waiting for " + (getNumberOfPlayers()-getNumberOfUsers()) +" players on port "+ serverSocket.getLocalPort() + "...");
        System.out.println("About to accept client connection...");
        Socket clientSocket = serverSocket.accept();
        ServerWorker worker = new ServerWorker(clientSocket, playableDeck.subList(0+(4*(getNumberOfUsers())),4*(getNumberOfUsers()+1)));
        worker.start();
        addUser(clientSocket);
        //System.out.println("Accepted connection from " + clientSocket);
      }
      System.out.println("Game about to start..");



    }catch(Exception e){
      e.printStackTrace();
    }
  }


}
