/**
 * 
 */
package deck;

/**
 * Public class to represent the deck used for the game
 * @author Michael Baker
 */
public class Deck {
   // protected variable because dealing machine needs access to it to make a
   // shuffled deck
   protected Card[] cards = new Card[52];
   private final String SPADES = "♠";
   private final String HEARTS = "♥";
   private final String CLUBS = "♣";
   private final String DIAMONDS = "♦";

   /**
    * Constructor method - only one constructor representing opening a new pack of
    * cards
    * @postcondition - will always return an array of 52 card objects in order
    *                2-Ace diamonds, hearts, clubs, spades
    */
   public Deck() {
      String[] standardSuits = { this.SPADES, this.HEARTS, this.CLUBS, this.DIAMONDS };
      String[] standardValues = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

      // need an iterator variable
      int i = 0;

      for (var S : standardSuits) {
         for (var V : standardValues) {
            this.cards[i] = new Card(S, V);
            i++;
         }
      }
   }

   /***************
    **** Getters
    ***************/
   /**
    * public getter function for the card contents at the specified index in the
    * deck
    * @return - a new card object, not a reference to the stored card object
    */

   public Card getCardAt(int i) {
      // return a new card object for the card housed at cards[i]
      return (new Card(this.cards[i]));
   }

   /***************
    **** Misc.
    ***************/
   /**
    * overwriting Java toString() function to print the cards based off of their
    * content in a uniform way
    * @return String containing the representation of the card
    */
   public String toString() {
      String outStr = "";
      for (int i = 0; i < 52; i++) {
         outStr += this.cards[i].toString() + "\n";
      }

      return (outStr);
   }

}
