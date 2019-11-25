package user;

import java.net.Socket;
import gameUi.Card;

public class User {
    private int ID;
    private Card hand;
    private static int numberOfUsers;

    public void setID(int ID) {
        this.ID = ID;
    }
    public static void addNumberOfUsers() {
        User.numberOfUsers++;
    }
    public void removeCard(){

    }




    public User(Socket clientSocket){
        addNumberOfUsers();


    }


}
