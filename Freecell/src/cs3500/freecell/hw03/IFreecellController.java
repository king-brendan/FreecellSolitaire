package cs3500.freecell.hw03;

import java.util.List;

import cs3500.freecell.hw02.FreecellOperations;

/**
 * Represents a controller for the game Freecell. The controller can take input in any form,
 * as long as it properly implements the playGame method.
 *
 * @param <K> the card representation for the Freecell game
 */
public interface IFreecellController<K> {
  /**
   * Begins to play an interactive game of Freecell.
   *
   * @param deck the deck to initialize the game with
   * @param model the game model to be used
   * @param numCascades the number of cascade piles
   * @param numOpens the number of open piles
   * @param shuffle whether or not the deck should be shuffled at game start
   * @throws IllegalStateException if no inputs are entered whatsoever
   * @throws IllegalArgumentException if any inputs are invalid
   */
  void playGame(List<K> deck, FreecellOperations<K> model, int numCascades,
                int numOpens, boolean shuffle);
}