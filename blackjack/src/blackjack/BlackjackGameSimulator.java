/**
 * @author Michael Baker
 *         Group of classes that allow the user to play blackjack against their
 *         computer
 */
package blackjack;

import deck.*;
import actors.*;
import java.util.Scanner;

/**
 * class that contains the main function - drives the interaction between the
 * user and the blackjack game
 * @author Michael Baker
 */
public abstract class BlackjackGameSimulator extends BlackjackLogic implements Visualizer {
   private static Scanner inputScanner;
   private static int numRoundsPlayed = 0;
   private static int playersMoney = 0;
   private static int playersBet = 0;
   private static boolean PlayGameAgain = false;

   /**
    * Main function that implements the UI and calls game functionality
    * @param args - this function does not use any command line arguments
    * @precondition - does not expect any inputs
    * @postcondition - does not return any outputs
    */
   public static void main(String[] args) {
      // main function progression
      do {
         // create the Scanner object that will be used for input
         inputScanner = new Scanner(System.in);

         // create player and dealer objects
         Player blackjackPlayer = new Player(inputScanner);
         Dealer blackjackDealer = new Dealer();
         GameActor[] gameActors = { blackjackDealer, blackjackPlayer };

         // ***** First message to the player
         if (numRoundsPlayed < 1) {
            // set the player's initial funds
            System.out.print("How much money do you have as the player? ");
            playersMoney = inputScanner.nextInt();
         } else {
            // if the game has already been played before, then use the old player's money
            System.out.println("Players money: " + playersMoney);
         }

         // if player has no money - kick them out of the game
         if (playersMoney == 0) {
            System.out.println("Player has no money! Better luck next time!");
            return;
         }

         // this gets called every loop because we make new player and dealer objects
         // each loop
         blackjackPlayer.addMoneyToPlayer(playersMoney);

         // player first must bet on their hand before anything else happens
         playersBet = blackjackPlayer.bet();
         playersMoney = blackjackPlayer.getMoney();

         // create the stack deck that we will be using for the entire game
         DealingMachine testDM = new DealingMachine();

         // first step is to deal the first two cards - what will be the players cards
         // and see if they sum up to blackjack
         Card[] playersInitialCards = { new Card(testDM.draw()), new Card(testDM.draw()) };
         blackjackPlayer.setHand(playersInitialCards);

         // if the game is not immeidately won by the player, deal two cards to the
         // dealer
         Card[] dealersInitialCards = { new Card(testDM.draw()), new Card(testDM.draw()) };
         blackjackDealer.setHand(dealersInitialCards);

         // assign both players to the same dealing machine
         for (var a : gameActors) {
            a.setDealingMachine(testDM);
         }

         // check to see if the player has won immediately
         if (BlackjackLogic.checkForWinningHand(blackjackPlayer.getHand())) {
            BlackjackLogic.updateGameState(GAME_WON_BY_PLAYER);
         } else {
            // if you reach the start of the game, switch the game state to the player
            BlackjackLogic.updateGameState(PASS_GAME_STATE_TO_PLAYER);
         }

         // draw the first view of the game
         drawTick(gameActors);

         // to implement the main functionality, a do while loop will be executed
         do {

            // use switch to move between game states
            switch (gameState) {
            case PASS_GAME_STATE_TO_PLAYER:
               blackjackPlayer.reason();
               // update visuals only when the player or dealer play
               drawTick(gameActors);
               break;
            case PASS_GAME_STATE_TO_DEALER:
               blackjackDealer.reason();
               // update visuals only when the player or dealer play
               drawTick(gameActors);
               break;
            case PASS_GAME_STATE_TO_MAIN_LOOP:
               BlackjackLogic.updateGameState(solveUnknownGameState(gameActors[0].getHand(), gameActors[1].getHand()));
               break;
            }

         } while ((gameState != GAME_WON_BY_PLAYER) && (gameState != GAME_WON_BY_DEALER)
               && (gameState != GAME_IS_A_TIE));

         // based on game state, either return the player's money or do nothing
         switch (gameState) {
         case GAME_WON_BY_PLAYER:
            System.out.println("\nPlayer wins!");
            blackjackPlayer.addMoneyToPlayer(playersBet * 2);
            break;
         case GAME_WON_BY_DEALER:
            System.out.println("\nDealer wins!");
            break;
         case GAME_IS_A_TIE:
            System.out.println("\nIt's A Tie!");
            break;

         }

         // table's reference update to the player's money
         playersMoney = blackjackPlayer.getMoney();

         // iterate on the number of rounds played
         numRoundsPlayed++;

         // ask the player if they want to play again
         boolean badPlayAgainInput = false;
         do {
            System.out.print("Play again? \n[Y]es/[N]o: ");
            String playAgain = inputScanner.next();
            if (!playAgain.equals("Y") && !playAgain.equals("N")) {
               System.out.println("Enter Y to play again or N to stop playing!");
               badPlayAgainInput = true;
            } else {
               switch (playAgain) {
               case "Y":
                  PlayGameAgain = true;
                  break;
               case "N":
                  PlayGameAgain = false;
                  break;
               }
               badPlayAgainInput = false;
            }
         } while (badPlayAgainInput);

      } while (PlayGameAgain);
   } // end of main

   /**
    * Public class to draw the table based off of the current game state - from
    * Visualizer interface
    * @param actors - array of GameActor objects, in the order Dealer, then Player
    * @precondition expects a 2 element array of GameActor objects
    * @postcondition Has no return, but draws the game state,
    *                including the player and dealer's hands in the console
    */
   public static void drawTick(GameActor[] actors) {
      // What does the actual game need to draw?
      // every time the player or dealer hits and their cards update the console needs
      // to be cleared and the new game state needs to be displayed

      // print a buffer line
      System.out.print("\n\n\n");

      // print the boarder of the game
      System.out.println(BOARDER);

      int i = 0;
      for (var a : actors) {
         a.drawTick();
         if (i == 0) {
            System.out.println(TABLE);
            i++;
         }
      }
      System.out.println(BOARDER);
      System.out.println("Players Moeny: " + playersMoney + "\tCurrent Bet: " + playersBet);

   }// end of drawTick()

}// end of BlackjackGameSimulator class
