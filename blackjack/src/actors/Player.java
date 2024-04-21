/**
 * 
 */
package actors;

import blackjack.BlackjackLogic;
import java.util.Scanner;

/**
 * Class to represent the player that extends the abstract class GameActor
 * @see GameActor
 * @author Michael Baker
 */
public final class Player extends GameActor {
   private int money;
   private Scanner scanner;
   private boolean scannerSetLatch = false;

   /**
    * constructor for the player object
    * @param inputScannerReference - reference to scanner object
    * @precondition Expects a reference to a scanner
    *               object
    * @postcondition Creates an object of type Player
    *                with a Scanner object
    */
   public Player(Scanner inputScannerReference) {
      this.setScannerReference(inputScannerReference);
      this.actorType = "Player";
   }// end of Player()

   /**
    * Public function that represents the player's ability to bet at the beginning
    * of the game
    * @precondition Expects no input. Will interface with the player via the
    *               scanner
    * @postcondition Will return a positive integer less than the players current
    *                money total
    * @return - integer representing the provided bet
    */
   public int bet() {
      boolean badBet = false;
      int playersBet;
      do {
         System.out.print("Place your bet: ");
         // send the call for the player to bet
         playersBet = scanner.nextInt();
         if (playersBet <= this.money) {
            this.money -= playersBet;
            badBet = false;
         } else if (playersBet >= this.money) {
            System.out.print("You cannot bet more than you have!\n");
            badBet = true;
         } else if (playersBet <= 0) {
            System.out.println("The entered bet must be greater than zero!");
            badBet = true;
         }
      } while (badBet);

      return (playersBet);
   }// end of bet()

   /**
    * Stand function for players.
    * @precondition This function takes no input
    * @postcondition This function passes input to the Dealer by way of the
    *                BlackjackLogic class and the gameState static variable
    */
   protected void stand() {
      BlackjackLogic.updateGameState(BlackjackLogic.PASS_GAME_STATE_TO_DEALER);
   } // end of stand()

   /**
    * Reason function for player. The player interacts with the function via the
    * user interface (the shared scanner object)
    * @precondition Does not expect any input
    * @postcondition Will call either the hit or stand function based off of the
    *                player
    */
   @Override
   public void reason() {
      // create a boolean to capture bad moves
      boolean badReason = false;

      do {
         // create I/O for player
         System.out.print("[H]it or [S]tand: ");
         String move = this.scanner.next();

         // check the player's input
         if (!move.equals("H") && !move.equals("S")) {
            System.out.println("Player can either hit by entering H, or stand by entering S");
            badReason = true;
         } else {
            switch (move) {
            case "H":
               this.hit();
               return;
            case "S":
               this.stand();
               return;
            }
         }
      } while (badReason);
   }

   /***********************
    * Getters
    **********************/

   /**
    * public getter function to return the player's money total
    * @return - integer representing the player's current money total
    */
   public int getMoney() {
      return (this.money);
   } // end getMoney()

   /***********************
    * Setters
    **********************/
   /**
    * function to set the reference used by the player object to the scanner being
    * used for the game
    * @param scannerReference - reference to the scanner the player object will use
    *        to interact with the console
    * @precondition expects a reference to a Java Scanner
    *               object with System.in
    *               as the parameter
    * @postcondition sets the private scanner variable of
    *                the player class
    */
   public void setScannerReference(Scanner scannerReference) {
      // only set the scanner if it has not yet been set
      if (this.scannerSetLatch) {
         throw new RuntimeException("Player Scanner reference has already been set");

      } else {
         // set the scanner reference to the reference to the input scanner
         this.scanner = scannerReference;
         this.scannerSetLatch = true;
      }
   } // end of setScannerReference()

   /**
    * function that adds any winnings to the players pool
    * @param money - money earned from a hand
    * @precondition assumes the value is a positive integer value
    * @postcondition Adds the provided integer input to the players
    *                money
    *                holdings
    */
   public void addMoneyToPlayer(int money) {
      this.money += money;
   }// end of addMoneyToPlayer

   /**
    * Implementation of the Visualizer interface for the Player object
    * @precondition Expects no input
    * @postcondition Returns no output
    */
   public void drawTick() {
      System.out.print("Players hand: ");
      for (var c : this.hand) {
         System.out.print(c);
         System.out.print("\t");
      }
      System.out.println();
   }// end of drawTick()

}
