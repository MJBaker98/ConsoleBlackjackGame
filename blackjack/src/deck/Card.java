/**
 * 
 */
package deck;

/**
 * the Card class represents a single card from a standard 52 card deck
 * @author Michael Baker
 */
public final class Card {
   private String suit;
   private String value;
   private final String SPADES = "♠";
   private final String HEARTS = "♥";
   private final String CLUBS = "♣";
   private final String DIAMONDS = "♦";

   /**
    * Constructor The suit and value parameter must be defined for every card
    * object, so this will be the only constructor
    * @param suit - a string representing one of the four suits of a deck of cards
    *        (spades, hearts, diamonds, clubs)
    * @param value - a string representing the face value of the card (2, 3, 4, 5,
    *        6, 7, 8, 9, 10, J, Q, K, A)
    * @precondition - Both inputs must be strings and conform to the required
    *               inputs
    * @postcondition - Returns an object of type Card
    */
   public Card(String suit, String value) {
      this.setSuit(suit);
      this.setValue(value);
   } // end constructor

   /**
    * Copy constructor
    * @param oldCard - object of type Card
    * @precondition - expects oldCard to point to an active Card object
    * @postcondition - returns a new Card object
    */
   public Card(Card oldCard) {
      this.setSuit(oldCard.getSuit());
      this.setValue(oldCard.getValue());
   } // end copy constructor

   /***********************
    * Setters
    **********************/

   /**
    * Private method to set the suit and check the inputs
    * @param suit - a string representing one of the four suits of a deck of cards
    *        (spades, hearts, diamonds, clubs)
    * @precondition - assumes the suit is one of the four defined suits above -
    *               input checks will verify this
    * @postcondition - has no output
    * @throws RuntimeException - thrown if the suit is not one of the defined suits
    */
   private void setSuit(String suit) {
      // remove any uppercase letters
      suit = suit.toLowerCase();
      // check for a valid input
      boolean goodSuit = ((suit.equals(this.SPADES)) || suit.equals(this.HEARTS) || suit.equals(this.DIAMONDS)
            || suit.equals(this.CLUBS));

      if (goodSuit) {
         this.suit = suit;
      } else {
         throw new RuntimeException("Provided suit is not one of the following : " + this.SPADES + ", " + this.HEARTS
               + ", " + this.DIAMONDS + ", " + this.CLUBS);
      }
   } // end class setSuit

   /**
    * Private method to set the suit and check the inputs
    * @param suit - a string representing one of the four suits of a deck of cards
    *        (spades, hearts, diamonds, clubs)
    * @precondition - expects a String input. If its a letter, it must be lowercase
    *               (but uppercase will be fixed)
    * @postcondition - has no return
    * @throws RuntimeException - thrown if the value is not one of the expected
    *         inputs
    */
   private void setValue(String value) {
      // remove any uppercase letters
      value = value.toLowerCase();
      // check for a valid input
      boolean goodValue = (value.equals("2") || value.equals("3") || value.equals("4") || value.equals("5")
            || value.equals("6") || value.equals("7") || value.equals("8") || value.equals("9") || value.equals("10")
            || value.equals("j") || value.equals("q") || value.equals("k") || value.equals("a"));

      if (goodValue) {
         this.value = value;
      } else {
         throw new RuntimeException(
               "Provided value is not one of the following : 2, 3, 4, 5, 6, 7, 8, 9, 10, j, q, k, a");
      }
   } // end class setValue

   /***********************
    * Getters
    **********************/

   /**
    * public function to get the suit of a Card object
    * @return String representing the suit of a card
    */
   public String getSuit() {
      return this.suit;
   } // end getSuit

   /**
    * public function to get the value of a Card object
    * @return String representing the value of a card
    */
   public String getValue() {
      return this.value;
   } // end getValue

   /***********************
    * Misc. Functions
    **********************/
   /**
    * overwriting Java toString() function to print the cards based off of their
    * content in a uniform way
    * @return String containing the representation of the card
    */
   public String toString() {
      String cardSuit = this.getSuit();
      if (this.getValue().equals("10")) {
         return ("| " + this.getValue() + cardSuit.charAt(0) + " |");
      } else {
         return ("|  " + this.getValue() + cardSuit.charAt(0) + " |");
      }
   }

} // end class Card
