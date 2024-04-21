/**
 * 
 */
package actors;

/**
 * Interface the ensure that all game objects use the same function handle to
 * update the visuals of the game
 * 
 * @author Michael Baker
 */
public interface Visualizer {
   // final static Strings that represent shared resourecs for displaying the game
   final String BOARDER = "=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+";
   final String TABLE = "\n\t B L A C K J A C K \t\n";

   /**
    * Interface function to be implemented by all implementing classes
    */
   public void drawTick();
}// end of Visualizer
