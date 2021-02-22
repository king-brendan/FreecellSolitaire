package cs3500.freecell.hw04;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.FreecellOperations;

/**
 * Creates a specific version of the Freecell model.
 */
public class FreecellModelCreator {

  /**
   * Represents the kind of Freecell models that the game can be played with.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE;
  }

  /**
   * Returns either a FreecellModel or a FreecellMultiMoveModel, depending on the GameType entered.
   *
   * @param type the type of Freecell model to be created
   * @return a FreecellOperations<Card></Card>
   */
  public static FreecellOperations<Card> create(GameType type) {
    try {
      switch (type) {
        case SINGLEMOVE:
          return new FreecellModel();
        case MULTIMOVE:
          return new FreecellMultiMoveModel();
        default:
          throw new IllegalArgumentException("Illegal GameType.");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Illegal GameType.");
    }
  }
}
