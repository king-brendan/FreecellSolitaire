package cs3500.freecell.hw02;

/**
 * Represents a card's possible suits (spades, clubs, hearts, diamonds).
 */
public enum CardSuit {
  CLUBS("♣"),
  DIAMONDS("♦"),
  HEARTS("♥"),
  SPADES("♠");

  public final String label;

  /**
   * Constructs a CardSuit.
   *
   * @param label the String value of the suit.
   */
  CardSuit(String label) {
    this.label = label;
  }

  /**
   * Returns whether two Cards are oppositely colored.
   *
   * @param a the first Card
   * @param b the second Card
   * @return whether or not they are differently colored
   */
  public static boolean differentColors(Card a, Card b) {
    boolean result = false;
    if (((a.getSuit() == CLUBS) || (a.getSuit() == SPADES)) && ((b.getSuit() == HEARTS)
            || (b.getSuit() == DIAMONDS))) {
      result = true;
    }
    if (((b.getSuit() == CLUBS) || (b.getSuit() == SPADES)) && ((a.getSuit() == HEARTS)
            || (a.getSuit() == DIAMONDS))) {
      result = true;
    }
    return result;
  }
}
