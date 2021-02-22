import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.CardSuit;
import cs3500.freecell.hw02.CardValue;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;

import static org.junit.Assert.assertEquals;

/**
 * Tests the various helper methods in Freecell.
 */
public class TestHelpers {
  FreecellModel mod3 = new FreecellModel();
  FreecellModel modGO;
  FreecellModel modGNS = new FreecellModel();
  List<Card> deck2 = mod3.getDeck();
  Card a;
  Card b;
  Card c;
  Card d;

  private void initExamples() {
    mod3.startGame(deck2, 5, 4, false);
    a = new Card(CardSuit.SPADES, CardValue.FOUR);
    b = new Card(CardSuit.DIAMONDS, CardValue.FIVE);
    c = new Card(CardSuit.CLUBS, CardValue.KING);
    d = new Card(CardSuit.HEARTS, CardValue.ACE);
    List<List<Card>> fPilesDone = FreecellModel.initEmptyPiles(4);
    for (int i = 0; i < 4; i++) {
      CardSuit suit = null;
      if (i == 0) {
        suit = CardSuit.CLUBS;
      } else if (i == 1) {
        suit = CardSuit.SPADES;
      } else if (i == 2) {
        suit = CardSuit.DIAMONDS;
      } else if (i == 3) {
        suit = CardSuit.HEARTS;
      }
      for (int j = 0; j < 13; j++) {
        fPilesDone.get(i).add(new Card(suit, CardValue.getVal(j)));
      }
    }
    modGO = new FreecellModel(FreecellModel.initEmptyPiles(5), fPilesDone,
            FreecellModel.initEmptyPile(4), 5, 4);
  }

  @Test
  public void testGetVal() {
    assertEquals(CardValue.ACE, CardValue.getVal(0));
    assertEquals(CardValue.KING, CardValue.getVal(12));
    assertEquals(CardValue.SEVEN, CardValue.getVal(6));
    assertEquals(null, CardValue.getVal(-12));
  }

  @Test
  public void testDifferentColors() {
    initExamples();
    assertEquals(true, CardSuit.differentColors(a, b));
    assertEquals(true, CardSuit.differentColors(a, d));
    assertEquals(false, CardSuit.differentColors(b, b));
    assertEquals(false, CardSuit.differentColors(d, b));
  }

  @Test
  public void testIsGameOver() {
    initExamples();
    assertEquals(false, mod3.isGameOver());
    assertEquals(true, modGO.isGameOver());
  }

  @Test
  public void testIsValid() {
    initExamples();
    assertEquals(true, mod3.isValid(deck2));
    List<Card> badDeck = new ArrayList<Card>();
    badDeck.add(a);
    badDeck.add(b);
    badDeck.add(c);
    badDeck.add(d);
    assertEquals(false, mod3.isValid(badDeck));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardGameNotStarted() {
    modGNS.getCard(PileType.CASCADE, 2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardNonexistant1() {
    initExamples();
    modGO.getCard(PileType.CASCADE, 2, 2);
  }
}
