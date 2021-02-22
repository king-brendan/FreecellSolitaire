import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.CardSuit;
import cs3500.freecell.hw02.CardValue;
import cs3500.freecell.hw02.FreecellModel;

import static org.junit.Assert.assertEquals;

/**
 * Test methods related to the game state.
 */
public class TestGameState {
  List<Card> heartPile;
  List<Card> spadesPile;
  List<Card> clubsPile;
  List<Card> diamondsPile;
  List<List<Card>> foundPiles;
  FreecellModel mod5;
  FreecellModel mod7;
  List<Card> gsDeck;

  private void gameInit() {
    heartPile = buildFinishedList(CardSuit.HEARTS);
    spadesPile = buildFinishedList(CardSuit.SPADES);
    clubsPile = buildFinishedList(CardSuit.CLUBS);
    diamondsPile = buildFinishedList(CardSuit.DIAMONDS);
    foundPiles = new ArrayList<List<Card>>();
    foundPiles.add(heartPile);
    foundPiles.add(spadesPile);
    foundPiles.add(clubsPile);
    foundPiles.add(diamondsPile);
    mod5 = new FreecellModel(FreecellModel.initEmptyPiles(4), foundPiles,
            FreecellModel.initEmptyPile(4), 4, 4);
    mod7 = new FreecellModel();
    gsDeck = mod7.getDeck();
    mod7.startGame(gsDeck, 4, 4, false);
  }

  @Test
  public void testGetGameState() {
    gameInit();
    assertEquals("F1:\n"
            + "F2:\n"
            + "F3:\n"
            + "F4:\n"
            + "O1:\n"
            + "O2:\n"
            + "O3:\n"
            + "O4:\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♠, 8♠, Q♠, 3♥, 7♥, J♥, 2♦, 6♦, 10♦\n"
            + "C2: 2♣, 6♣, 10♣, A♠, 5♠, 9♠, K♠, 4♥, 8♥, Q♥, 3♦, 7♦, J♦\n"
            + "C3: 3♣, 7♣, J♣, 2♠, 6♠, 10♠, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦\n"
            + "C4: 4♣, 8♣, Q♣, 3♠, 7♠, J♠, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦", mod7.getGameState());


  }

  @Test
  public void testIsGameOver() {
    gameInit();
    assertEquals(true, mod5.isGameOver());

    FreecellModel mod6 = new FreecellModel();
    List<Card> deck3 = mod6.getDeck();
    mod6.startGame(deck3, 4, 4, false);
    assertEquals(false, mod6.isGameOver());
  }

  /**
   * Builds a list of Cards in ascending order of a certain suit.
   * @param suit the suit of the cards
   * @return a list of cards in ascending order of the same suit
   */
  public static List<Card> buildFinishedList(CardSuit suit) {
    ArrayList<Card> list = new ArrayList<Card>();
    for (int i = 0; i < 13; i++) {
      list.add(new Card(suit, CardValue.getVal(i)));
    }
    return list;
  }
}
