
package gameUi;
import java.util.*;


public class Card{
  private static String[] cardRanks = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
  private static HashMap<String, String> cardSuits = new HashMap<String, String>();

  public void setRanks(){
    this.cardSuits.put("Spades","♠");
    this.cardSuits.put("Diamond","♦");
    this.cardSuits.put("Hearts","♥");
    this.cardSuits.put("Clover","♣");
  }
  public HashMap<String,String> getCardSuits(){
    return this.cardSuits;
  }
  public Card(String suit, String rank){
    setRanks();
    HashMap<String,String> cardSuits = this.getCardSuits();

    String extraSpace = rank.length()==1? " ":"";
    String card =
    "┌─────────┐\n"+
    "│"+ rank + extraSpace + "       │\n"+
    "│         │\n"+
    "│         │\n"+
    "│    "+ cardSuits.get(suit)+"    │\n"+
    "│         │\n"+
    "│         │\n"+
    "│       "+ rank + extraSpace +"│\n"+
    "└─────────┘";
    System.out.println(card);
  }

  public static void main (String[] arg){
    Card testCard = new Card("Hearts","10");
    Card testCard2 = new Card("Spades","10");
  }
}
