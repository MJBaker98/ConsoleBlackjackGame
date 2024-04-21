/**
 * 
 */
package deck;

import java.util.HashSet;
import java.util.Set;

/**
 * Public class to represent a shuffled version of a deck of cards that works
 * like a stack
 * @author Michael Baker
 */
public class DealingMachine extends Deck {
   private byte endOfStack = 51;

   /**
    * Constructor to create a deck object
    * @return DealingMachine object
    */
   public DealingMachine() {
      // Create the deck object
      super();
      this.shuffle();
   } // end Constructor

   /**
    * Public function to "pull" a card from the deck of cards
    * @return random card at the top of the deck
    * @precondition There are no inputs
    * @postcondition Unlike a real stack, the top card is not removed, the pointer
    *                just moves up the deck as cards are pulled
    */
   public Card draw() {
      // create return variable
      Card returnCard;

      // Drawing will take the top card off of the deck and remove it from the array
      if (endOfStack == -1) {
         throw new RuntimeException("Ran out of cards!");
      } else {
         returnCard = new Card(this.cards[endOfStack]);
         endOfStack--;
      }
      return (returnCard);
   } // end draw()

   /**
    * Private function for use during construction of the DealingMachine class to
    * create a shuffled version of the deck
    * @precondition - There are no inputs to this function. The function operates
    *               on the list of cards stored in the object
    * @postcondition - has no return, but after the shuffle is done the internal
    *                deck of cards are shuffled
    */
   private void shuffle() {
      // to achieve the shuffled form, use a set and a random number generator to get
      // a unique order of values
      Set<Integer> shuffledIndicies = new HashSet<Integer>();
      int[] newOrder = new int[52];
      int i = 0;

      while (shuffledIndicies.size() < 52) {
         // generate a random integer within the range 0-51
         Integer currentRandomInt = (int) (52 * Math.random());

         // if the value is in the set, add it, if not, continue
         if (shuffledIndicies.contains(currentRandomInt)) {
            continue;
         } else {
            shuffledIndicies.add(currentRandomInt);
            newOrder[i] = currentRandomInt;
            i++;
         }
      }

      // create a new deck from scratch
      Deck tempDeck = new Deck();

      // iterate through new indicies and replace cards at those indicies
      for (int j = 0; j < 52; j++) {
         this.cards[j] = new Card(tempDeck.cards[newOrder[j]]);
      }

   }// end shuffle()
}// end of DealingMachine class
