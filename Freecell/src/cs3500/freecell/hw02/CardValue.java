package cs3500.freecell.hw02;

/**
 * Represents the 12 values a card can have (2 - 10, A, J, Q, K).
 */
public enum CardValue {
  ACE("A", 1),
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("J", 11),
  QUEEN("Q", 12),
  KING("K", 13);

  public final String label;
  public final int intVal;

  /**
   * Constructs a CardValue.
   *
   * @param label the String value of the card value.
   */
  CardValue(String label, int intVal) {
    this.label = label;
    this.intVal = intVal;
  }

  /**
   * Returns the CardValue corresponding to the integer value, aces being 1, jacks being 11, etc.
   *
   * @param i the card 'level'
   * @return the corresponding CardValue
   */
  public static CardValue getVal(int i) {
    CardValue ans = null;
    switch (i + 1) {
      case 1:
        ans = ACE;
        break;
      case 2:
        ans = TWO;
        break;
      case 3:
        ans = THREE;
        break;
      case 4:
        ans = FOUR;
        break;
      case 5:
        ans = FIVE;
        break;
      case 6:
        ans = SIX;
        break;
      case 7:
        ans = SEVEN;
        break;
      case 8:
        ans = EIGHT;
        break;
      case 9:
        ans = NINE;
        break;
      case 10:
        ans = TEN;
        break;
      case 11:
        ans = JACK;
        break;
      case 12:
        ans = QUEEN;
        break;
      case 13:
        ans = KING;
        break;
      default:
        ans = null;
    }
    return ans;
  }
}
