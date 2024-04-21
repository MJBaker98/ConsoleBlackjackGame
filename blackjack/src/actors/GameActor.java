/**
 * 
 */
package actors;

import deck.*;
import blackjack.BlackjackLogic;

/**
 * Abstract class that captures shared functionality between the Player and the
 * Computer (each considered a "GameActor")
 * @author Michael Baker
 */
public abstract class GameActor implements Visualizer {
   protected DealingMachine DealingMachineReference;
   protected Card[] hand;
   protected String actorType;

   // need to static variables to capture if the player has been assigned a hand or
   // dealing machine
   private boolean firstHandLatch = false;
   private boolean firstDealingMachineLatch = false;

   /**
    * Constructor for an agent in the blackjack game
    * @precondition - expects no input
    * @postcondition - expects no output
    */
   public GameActor() {
      // only using a default constructor assuming that the player and dealer's hands
      // will be updated once the game starts
   }

   /**
    * Private function that draws a card off of the top of the current dealing
    * machine
    * @precondition - does not expect any input, but expects that the
    *               DealingMachineReference has been assigned
    * @postcondition - does not return anything
    */
   protected void hit() {
      // draw a card
      this.addCardToHand(this.DealingMachineReference.draw());

      // every time the actor hits, check to see if they won
      if (BlackjackLogic.checkForWinningHand(this.getHand())) {
         switch (this.actorType) {
         case "Player":
            BlackjackLogic.updateGameState(BlackjackLogic.GAME_WON_BY_PLAYER);
            break;
         case "Dealer":
            BlackjackLogic.updateGameState(BlackjackLogic.GAME_WON_BY_DEALER);
            break;
         }
      } else if (BlackjackLogic.cardCounter(this.hand) > 21) {
         switch (this.actorType) {
         case "Player":
            BlackjackLogic.updateGameState(BlackjackLogic.GAME_WON_BY_DEALER);
            break;
         case "Dealer":
            BlackjackLogic.updateGameState(BlackjackLogic.GAME_WON_BY_PLAYER);
            break;
         }
      }

   }// end hit()

   /**
    * Function that will be shared by both players and dealers, but that must be
    * defined specifically for each
    */
   protected void stand() {
   } // end stand()

   /***************
    **** Getters
    ***************/
   /**
    * public getter function that returns an array of the cards the actor is
    * holding
    * @return array of Card objects that contain the cards in the GameActor's hand
    * @precondition - expects no input
    * @postcondition - will always return an array of Card objects
    */
   public Card[] getHand() {
      return this.hand;
   }

   /***************
    **** Setters
    ***************/
   /**
    * private setter function that adds a newly obtained card to the hand
    * @param newCard - object of type Card
    * @precondition - expects that the new card will only be a single card
    * @postcondition - makes no return, but adds card to hand
    */
   private void addCardToHand(Card newCard) {
      // get the number of cards currently in the actors hand
      int currentHandLength = this.hand.length;

      // create a new card array with one more card element
      Card[] tempCards = new Card[currentHandLength + 1];

      // loop through existing cards
      for (int i = 0; i < currentHandLength; i++) {
         tempCards[i] = new Card(this.hand[i]);
      }

      // add the card
      tempCards[currentHandLength] = new Card(newCard);

      // reset the pointer of this.hand to the tempCards array
      this.hand = tempCards;
   } // end addCardToHand

   /**
    * Setter function for a game actor's cards - only called once to assign the
    * first two cards to the hands of the players
    * @param cards - array of cards to make the current hand of the GameActor
    * @precondition - expects an array of Card objects
    * @postcondition - has no return - assigns the cards to the GameActor
    */
   public void setHand(Card[] cards) {
      // init hand based off of initially dealt cards
      if (this.firstHandLatch) {
         throw new RuntimeException("GameActor's hand has already been initialized");
      } else {
         // first need to set the hand to be not null
         this.hand = new Card[cards.length];

         // iterate through provided cards to assign the drawn cards
         int i = 0;
         for (var V : cards) {
            this.hand[i] = new Card(V);
            i++;
         }

         this.firstHandLatch = true;
      }
   } // end setHand

   /**
    * Setter function to assign a game actor to a dealing machine for the game
    * @param DM - DealingMachine object representing a stack with a deck of cards
    * @precondition - Expects an active (not null) DealingMachine reference
    * @postcondition - Has no return
    */
   public void setDealingMachine(DealingMachine DM) {
      // assign the local variable to the dealing machine reference
      // only assign a dealing machine if it hasn't been done yet
      if (this.firstDealingMachineLatch) {
         throw new RuntimeException("GameActor's DealingMachine reference has already been initialized");
      } else {
         this.DealingMachineReference = DM;
         this.firstDealingMachineLatch = true;
      }
   } // end setDealingMachine()

   /**
    * function that will be overwritten by inheriting classes based off of their
    * logic.
    */
   public void reason() {
   } // end reason()

}
