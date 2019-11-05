package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerWorker extends Thread{

  private final Socket clientSocket;
  private final List<String> initialHand;

  public ServerWorker(Socket clientSocket, List<String> initialHand1){
    this.clientSocket = clientSocket;
    this.initialHand = initialHand1;
  }

  @Override
  public void run(){
    try{
      handleClientSocket();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private void handleClientSocket() throws IOException, InterruptedException{
    InputStream inputStream = clientSocket.getInputStream();
    OutputStream outputStream = clientSocket.getOutputStream();

    //initial cards
    StringBuilder sb = new StringBuilder();
    for (String s : initialHand)
    {
      sb.append(s);
    }
    String cards = sb.toString();
    outputStream.write(cards.getBytes());

    //chatstuff
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String line;
    while((line = reader.readLine()) != null){
      if("quit".equalsIgnoreCase(line)){
        String msg = "Sad to see you go! :( Bye!\n";
        outputStream.write(msg.getBytes());
        break;
      }
      String msg = "You " + line + "\n";
      outputStream.write(msg.getBytes());

    }
    // for(int i=0; i<10; i++){
    //   outputStream.write(("Time now is" + new Date() + "\n").getBytes());
    //   Thread.sleep(1000);
    // }
    clientSocket.close();
  }

}
