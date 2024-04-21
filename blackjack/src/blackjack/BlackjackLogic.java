/**
 * blackjack package
 */
package blackjack;

import deck.Card;

/**
 * abstract class to house logic functions for the game of blackjack. Abstract
 * class implementation with static methods - methods can be called by any other
 * object
 * @author Michael Baker
 */
public abstract class BlackjackLogic {
   // create game state variables for all operations
   public static final int PASS_GAME_STATE_TO_MAIN_LOOP = 3;
   public static final int PASS_GAME_STATE_TO_PLAYER = 1;
   public static final int PASS_GAME_STATE_TO_DEALER = 2;
   public static final int GAME_WON_BY_PLAYER = 4;
   public static final int GAME_WON_BY_DEALER = 5;
   public static final int GAME_IS_A_TIE = 6;
   protected static int gameState = 0;

   /**
    * Static function that checks if the hand provided is a winner
    * @param hand - An array of card objects representing an actors hand
    * @return - Returns true of the card total is exactly equal to 21
    * @precondition - Expects an n-element array of card objects
    * @postcondition - Will return a booolean type - true if the hand contains
    *                exactly 21 and false otherwise
    */
   public static boolean checkForWinningHand(Card[] hand) {
      int handValue = cardCounter(hand);
      return (handValue == 21);
   } // end of checkForWinningHand()

   /**
    * static function that counts the sum of the points held in the hand
    * @param hand - array of Card objects
    * @return an integer representing the value of the cards
    * @precondition - Expects an n-element array of card objects
    * @postcondition - Will return an integer type equal to the value of the hand.
    *                If there are aces in the hand, the ace will always be assumed
    *                to be 1 unless it can be added to the sum of the current
    *                "static" cards (2-K) without exceeding 21.
    */
   public static int cardCounter(Card[] hand) {
      // init variables to store the card sum and number of aces in the hand
      int cardSum = 0;
      int AceCount = 0;

      for (Card C : hand) {
         switch (C.getValue()) {
         case "10":
         case "j":
         case "q":
         case "k":
            cardSum += 10;
            break;
         case "9":
            cardSum += 9;
            break;
         case "8":
            cardSum += 8;
            break;
         case "7":
            cardSum += 7;
            break;
         case "6":
            cardSum += 6;
            break;
         case "5":
            cardSum += 5;
            break;
         case "4":
            cardSum += 4;
            break;
         case "3":
            cardSum += 3;
            break;
         case "2":
            cardSum += 2;
            break;
         case "a":
            // When you add aces to the hand matters - they should only be added once all
            // other cards are added
            AceCount++;
            break;

         }
      }

      // logic to account for the number of aces in the hand
      if (AceCount == 1) {
         // with one ace, the math can be done directly
         cardSum += (cardSum + 11 > 21) ? 1 : 11;
      } else if (AceCount > 1) {
         // can any one of my aces be 11, or do they all have to be 1?
         byte aceCountMax = 0;
         byte aceCountMin = 0;
         for (int i = 0; i < AceCount; i++) {
            // increases aceCountMax assuming one ace can be an 11
            // note that there will never be two two aces with 11 - the hand total would
            // then be 22 (bust)
            switch (i) {
            case 0:
               aceCountMax += 11;
               break;
            default:
               aceCountMax += 1;
               break;
            }
            // increase aceCountMin assuming all aces are calculated as 1
            aceCountMin++;
         }
         if ((aceCountMax + cardSum) > 21) {
            cardSum += aceCountMin;
         } else {
            cardSum += aceCountMax;
         }
      }

      return (cardSum);
   }// end of cardCounter()

   /**
    * public static function to be used by the player and dealer classes to
    * announce if/when they have won
    * @param newGameState - the value to change the game state to
    * @precondition - The function expects that the input is one of the predefined
    *               game states in the GameStateVariables interface
    * @postcondition - Updates the gameState static variable shared with the
    *                BlackjackGameSimulator
    */
   public static void updateGameState(int newGameState) {
      if (newGameState > 6) {
         throw new RuntimeException("Unrecognized game state " + newGameState + "\n");
      } else {
         gameState = newGameState;
      }
   } // end of updateGameState()

   /**
    * Static function to determine who has the better hand in the case that neither
    * player reaches 21
    * @param dealerHand - Array of Card objects that contains the Dealer's cards
    * @param playerHand - Array of Card objects that contains the Player's cards
    * @return - integer containing one of the predefined game state variables
    * @precondition - this function assumes that the dealer's hand is first and the
    *               player's hand is second, there is no check to ensure this is
    *               true
    * @postcondition - the output of this function will only ever be the integers
    *                stored in one of the three game completion static final
    *                variables defined above
    */
   public static int solveUnknownGameState(Card[] dealerHand, Card[] playerHand) {
      // whoever has the higher number in their hand after both players stand wins
      // three possible states: dealer wins, player wins, or tie
      int dealersCount = cardCounter(dealerHand);
      int playersCount = cardCounter(playerHand);

      if (dealersCount == playersCount) {
         return GAME_IS_A_TIE;
      } else if (dealersCount > playersCount) {
         return GAME_WON_BY_DEALER;
      } else {
         return GAME_WON_BY_PLAYER;
      }
   } // end of solveUnknownGameState()

} // end of BlackjackLogic class
