package gameUi;

public class Instructions {

    //Client instructions
    public String clientGreetings =
            "-----------------------------------***-GREETINGS!-***-----------------------------------\n" +
            "Hello player! The server will not distribute your initial pool of cards once all players have connected";
    public String clientInstructions =
            "-------------------------***-Client Side Game play Tutorial-***-------------------------\n" +
            "The naming convention of the cards is first letter signifies the number (Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King) and the second letter signifies the suit (Spades, Hearts, Diamonds, Clubs)\n\n" +
            "Once all the players have joined the game, the server side will direct the client side players to pass their cards.\n" +
            "The players will be asked to enter a card. The players should only choose from their list of cards.\n" +
            "To choose a card the player should input the card's number followed by its suit.\n" +
            "After choosing a card, the player is notified of the card that it passed\n" +
            "After all the players have passed, the player is notified of its current pool of cards. The player will have its card passed to the player on the right.\n" +
            "The player wins once he gets the four matching cards.\n\n" +
            "-----------------------------------***-GAME START-***-----------------------------------\n" +
            "Waiting for other players to connect...";
    public String clientSelectionInstruction =
            "----------------------------------------Reminder----------------------------------------\n" +
            "Player should choose a card to pass from the player's current pool of cards. To choose a card enter the card's number followed by its suit:\n" +
            "----------------------------------------------------------------------------------------";
    public String clientPressEnterInstruction =
            "-----------------------------------------!HURRY!----------------------------------------\n" +
            "Your cards all match! Hurry, press Enter before anyone else does!\n" +
            "----------------------------------------------------------------------------------------\n";
    public String clientYouLost(String ID) {
        String msg = 
            "-----------------------------------------!LOSER!----------------------------------------\n" +
            "Player " + ID + " has already completed their cards :(\n" +
            "----------------------------------------------------------------------------------------\n";
        return msg;
    }
    public String serverSomeoneCompleted(int ID){
        String msg =
            "-------------------------------------!COMPLETE ALERT!-----------------------------------\n" +
            "Player " + Integer.toString(ID+1) + " matched all his/her cards!!\n" +
            "----------------------------------------------------------------------------------------";
        return msg;
    }
    public String serverSomeoneWon(int ID){
        String msg =
            "------------------------------------!WE HAVE A WINNER!----------------------------------\n" +
            "Player " + Integer.toString(ID+1) + " matched all his/her cards and has the fastest fingers\n" +
            "----------------------------------------------------------------------------------------\n";
        return msg;
    }
    public String serverPassYourCards(int ID){
        String msg =
             "-------------------------------------------!PASS!------------------------------------------\n" +
             "Server told player " + Integer.toString(ID+1) + " to pass his cards.\n" +
             "----------------------------------------------------------------------------------------\n";
        return msg;
    }
    public String congratulationsWinner =
            "------------------------------!WINNER WINNER CHICKEN DINNER!----------------------------\n" +
            "CONGRATULATIONS! YOU HAD FAST FINGERS! YOU WON!!\n" +
            "----------------------------------------------------------------------------------------\n";
    public String sorrySlowPoke(int ID){
        String msg =
            "----------------------------------------!SLOWPOKE!--------------------------------------\n" +
            "Your slow fingers cost you the game! Sorry, you lost! Player " + Integer.toString(ID+1) + " has won the game.\n" +
            "----------------------------------------------------------------------------------------\n";
        return msg;
    }
    public String inputLengthError =
            "-----------------------------------!!!!!!ALERT!!!!!!------------------------------------\n" +
            "Invalid card length. Try again.\n" +
            "----------------------------------------------------------------------------------------\n";
        
    public String wrongString(int ID, String string){
        String msg =
            "-----------------------------------!!!!!!ALERT!!!!!!------------------------------------\n" +
            "You player " + Integer.toString(ID+1) + " submitted the wrong string! You don't have "+ string + "\n" +
            "----------------------------------------------------------------------------------------\n";
        return msg;
    }
    public String allPlayersHavePassed =
            "--------------------------!!!!!!ALL PLAYERS HAVE PASSED!!!!!!---------------------------\n";
    public String gameStart =
            "\n---------------------------------!!!!!!GAME START!!!!!!---------------------------------\n";

    public Instructions(){

    }
}
