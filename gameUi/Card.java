
package gameUi;
import java.util.*;


public class Card{
  private static String[] cardRanks = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
  private static HashMap<String, String> cardSuits = new HashMap<String, String>();

  public void setRanks(){
    this.cardSuits.put("S","♠");
    this.cardSuits.put("D","♦");
    this.cardSuits.put("H","♥");
    this.cardSuits.put("C","♣");
  }
  public HashMap<String,String> getCardSuits(){
    return this.cardSuits;
  }
  public Card(String cardString){
    setRanks();
    HashMap<String,String> cardSuits = this.getCardSuits();
    //rank = rank.equals("T")? "10":rank;
    //String "" = rank.length()==1? " ":"";
    String card =
            "┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐\n"+
                    "│"+ cardString.charAt(0) + " " + "       │ │"+ cardString.charAt(2) + " " + "       │ │"+ cardString.charAt(4) + " " + "       │ │"+ cardString.charAt(6) + " " + "       │\n"+
                    "│         │ │         │ │         │ │         │\n"+
                    "│         │ │         │ │         │ │         │\n"+
                    "│    "+ cardSuits.get(Character.toString(cardString.charAt(1)))+"    │ │    "+ cardSuits.get(Character.toString(cardString.charAt(3)))+"    │ │    "+cardSuits.get(Character.toString(cardString.charAt(5)))+"    │ │    "+cardSuits.get(Character.toString(cardString.charAt(7)))+"    │\n"+
                    "│         │ │         │ │         │ │         │\n"+
                    "│         │ │         │ │         │ │         │\n"+
                    "│       "+ cardString.charAt(0) + " " +"│ │       "+ cardString.charAt(2) + " " +"│ │       "+ cardString.charAt(4) + " " +"│ │       "+ cardString.charAt(6) + " " +"│ \n"+
                    "└─────────┘ └─────────┘ └─────────┘ └─────────┘";
    System.out.println(card);
  }


  public static void main (String[] arg){
    Card testCard = new Card("9H9D2S2C");
  }
}
