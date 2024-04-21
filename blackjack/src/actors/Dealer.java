/**
 * 
 */
package actors;

import blackjack.BlackjackLogic;

/**
 * 
 */
public final class Dealer extends GameActor {
   private boolean hiddenHandLatch = true; // card should be hidden at first

   /**
    * Constructor - only need to implement a basic constructor for this design
    */
   public Dealer() {
      this.actorType = "Dealer";
   }

   /**
    * Function to pass game logic back to the loop
    */
   @Override
   protected void stand() {
      BlackjackLogic.updateGameState(BlackjackLogic.PASS_GAME_STATE_TO_MAIN_LOOP);
      System.out.println("Dealer Stands...");
   }

   @Override
   /**
    * Dealer's logic is rather simple. Hit until greater than 17, then stand
    */
   public void reason() {
      // dealer's reason is simple - if their hand is less than 17 they hit, otherwise
      // they stand

      // set the hidden hand latch to be false so the card will now show when
      // reasoning
      this.hiddenHandLatch = false;

      // first check to see if the dealer has won immediately
      int handCardCount = BlackjackLogic.cardCounter(this.hand);

      if (BlackjackLogic.checkForWinningHand(this.hand)) {
         BlackjackLogic.updateGameState(BlackjackLogic.GAME_WON_BY_DEALER);
         return;
      } else if (handCardCount < 17) {
         // if the value is less than 17 hit
         this.hit();
         System.out.println("Dealer Hits...");
      } else if (handCardCount >= 17) {
         if (handCardCount > 21) {
            // if the value is greater than 21, the dealer busts and the player wins
            BlackjackLogic.updateGameState(BlackjackLogic.GAME_WON_BY_PLAYER);
         } else {
            this.stand();
         }
      }
   }

   /**
    * Visualization interface
    */
   public void drawTick() {
      System.out.print("Dealers hand: ");
      for (int i = 0; i < this.hand.length; i++) {
         // at first the second card the dealer draws is hidden
         // un-hide it when the dealer starts to draw cards
         if ((i == 1) && this.hiddenHandLatch) {
            System.out.print("|-----|");
         } else {
            System.out.print(this.hand[i]);
         }
         System.out.print("\t");
      }
      System.out.println();
   }

}
