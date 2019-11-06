
package gameUi;
import java.util.*;


public class Card{
  private static String[] cardRanks = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
  private static HashMap<String, String> cardSuits = new HashMap<String, String>();
  private static List<String> hand = new ArrayList<String> ();
  private String cardString;

  public HashMap<String,String> getCardSuits(){
    return this.cardSuits;
  }

  public void setCardString(String cardString) {
    this.cardString = cardString;
  }

  public void setHand(List<String> hand) {
    Card.hand = hand;
    //initial cards
    StringBuilder sb = new StringBuilder();
    for (String s : hand)
    {
      sb.append(s);
    }
    setCardString(sb.toString());
  }

  public void setRanks(){
    this.cardSuits.put("S","♠");
    this.cardSuits.put("D","♦");
    this.cardSuits.put("H","♥");
    this.cardSuits.put("C","♣");
  }
  public String makeCard(String cardString){
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
    return card;
  }

  //for server
  public void displayCards(){
    cardString = this.cardString;
    String card = makeCard(cardString);
    System.out.println(card);
  }
  //for client
  public void displayCards(String cardString){
    cardString = cardString+"";
    String card = makeCard(cardString);
    System.out.println(card);
  }
  //for server
  public Card(List<String> cards){
    setRanks();
    HashMap<String,String> cardSuits = this.getCardSuits();
    setHand(cards);
    //rank = rank.equals("T")? "10":rank;
    //String "" = rank.length()==1? " ":"";

  }
  //this one is for client. he just knows what's in his hands eh.
  public Card(String card){
    setRanks();
    HashMap<String,String> cardSuits = this.getCardSuits();
    displayCards(card);

  }


  public static void main (String[] arg){
    //server
//    List<String> cards =  new ArrayList<String> ();
//    cards.add("TH");
//    cards.add("TH");
//    cards.add("TH");
//    cards.add("TH");
//      Card testCard = new Card(cards);
//      testCard.displayCards();
    //client
    Card testCard = new Card("9H8HTHTH");
  }
}
