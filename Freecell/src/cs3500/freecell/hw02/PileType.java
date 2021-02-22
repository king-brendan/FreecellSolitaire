package cs3500.freecell.hw02;

/**
 * Type for the three types of piles in a game of Freecell. <br>
 * Open: This pile can hold only one card. It is like a buffer to temporarily
 * hold cards <br>
 * Cascade: This is a pile of face-up cards. A build within a cascade
 * pile is a subset of cards that has monotonically decreasing values and suits
 * of alternating colors<br>
 * Foundation: Initially empty, there are 4 foundation
 * piles, one for each suit. Each foundation pile collects cards in increasing
 * order of value for one suit (Ace being the lowest). <br> The goal of the
 * game is to fill up all the foundation piles
 */
public enum PileType {
  OPEN, CASCADE, FOUNDATION;

  /**
   * Returns the PileType corresponding to a given character.
   *
   * @param c the character corresponding to the PileType
   * @return the corresponding PileType
   */
  public static PileType getPileType(char c) {
    if (c == 'C') {
      return CASCADE;
    } else if (c == 'F') {
      return FOUNDATION;
    } else if (c == 'O') {
      return OPEN;
    } else {
      throw new IllegalArgumentException("Invalid pile type character");
    }
  }
}
