package cs3500.freecell.hw02;

/**
 * Represents a single card, with its its suit and value.
 */
public class Card {
  private final CardSuit suit;
  private final CardValue value;

  /**
   * Constructs a Card, with a given suit and value.
   *
   * @param suit the suit of the card.
   * @param value the value of the card.
   */
  public Card(CardSuit suit, CardValue value) {
    this.suit = suit;
    this.value = value;
  }

  @Override
  public String toString() {
    return value.label + suit.label;
  }

  /**
   * Returns the suit of the card.
   * @return the suit of the card
   */
  public CardSuit getSuit() {
    return this.suit;
  }

  /**
   * Returns the Value of the card.
   * @return the value of the card
   */
  public CardValue getValue() {
    return this.value;
  }

  @Override
  public boolean equals(Object o) {
    boolean result = false;
    if (o instanceof Card) {
      result = (((Card) o).getValue() == this.value) && (((Card) o).getSuit() == this.suit);
    }
    return result;
  }

  @Override
  public int hashCode() {
    int result = 0;
    if (suit == CardSuit.SPADES) {
      result = result + 444;
    } else if (suit == CardSuit.HEARTS) {
      result = result + 555;
    } else if (suit == CardSuit.DIAMONDS) {
      result = result + 666;
    } else if (suit == CardSuit.CLUBS) {
      result = result + 777;
    }
    result = result + this.value.intVal;
    return result;
  }
}
