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
 * Tests the various methods that initialize the Freecell game.
 */
public class TestGameInits {
  FreecellModel mod = new FreecellModel();
  FreecellModel mod2 = new FreecellModel();
  List<Card> deck1 = mod.getDeck();
  FreecellModel mod4 = new FreecellModel();
  List<Card> shuffleDeck = mod4.getDeck();
  Card aceOfSpades = new Card(CardSuit.SPADES, CardValue.ACE);

  @Test
  public void testGetDeck() {
    assertEquals(52, deck1.size());
  }

  @Test
  public void testStartGame() {
    mod2.startGame(deck1, 4, 4, false);

    assertEquals(true, deck1.get(0).equals(mod2.getCard(PileType.CASCADE, 0, 0)));
    assertEquals(true, deck1.get(9).equals(mod2.getCard(PileType.CASCADE, 1, 2)));
    assertEquals(true, deck1.get(51).equals(mod2.getCard(PileType.CASCADE, 3, 12)));

    mod4.startGame(shuffleDeck, 4, 3, true);
    boolean allSameCards = true;
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j < 3; j++) {
        if (mod4.getCascadePiles().get(j).get(i) != mod2.getCascadePiles().get(j).get(i)) {
          allSameCards = false;
        }
      }
    }
    assertEquals(false, allSameCards);
  }

  @Test
  public void testInitEmptyPiles() {
    List<List<Card>> list1 = mod.initEmptyPiles(4);
    assertEquals(new ArrayList<Card>(), list1.get(3));
    assertEquals(new ArrayList<Card>(), list1.get(0));

    List<List<Card>> list2 = mod.initEmptyPiles(3);
    assertEquals(new ArrayList<Card>(), list2.get(2));
    list2.get(1).add(aceOfSpades);
    assertEquals(aceOfSpades, list2.get(1).get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartGameConds1() {
    shuffleDeck.remove(50);
    mod4.startGame(shuffleDeck, 4, 3, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartGameConds2() {
    mod4.startGame(deck1, 3, 3, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartGameConds3() {
    mod4.startGame(deck1, -5, 3, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartGameConds4() {
    mod4.startGame(deck1, 5, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartGameConds5() {
    mod4.startGame(deck1, 5, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartGameDeck() {
    mod4.startGame(new ArrayList<Card>(), 4, 4, false);
  }

}

