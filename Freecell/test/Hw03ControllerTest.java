import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.CardSuit;
import cs3500.freecell.hw02.CardValue;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw03.FreecellController;

import static org.junit.Assert.assertEquals;


/**
 * Tests the various FreecellController methods.
 */
public class Hw03ControllerTest {
  FreecellModel mod1;
  List<Card> deck1;
  StringBuilder gameLog;
  FreecellController c;
  List<Card> illegalDeck;
  FreecellModel mod5;
  List<Card> heartPile;
  List<Card> spadesPile;
  List<Card> clubsPile;
  List<Card> diamondsPile;
  List<List<Card>> foundPiles;


  /**
   * Initializes examples for testing.
   */
  private void initExamples() {
    mod1 = new FreecellModel();
    deck1 = mod1.getDeck();
    gameLog = new StringBuilder();
    illegalDeck = new ArrayList<Card>();
    illegalDeck.add(new Card(CardSuit.SPADES, CardValue.ACE));
    heartPile = TestGameState.buildFinishedList(CardSuit.HEARTS);
    spadesPile = TestGameState.buildFinishedList(CardSuit.SPADES);
    clubsPile = TestGameState. buildFinishedList(CardSuit.CLUBS);
    diamondsPile = TestGameState.buildFinishedList(CardSuit.DIAMONDS);
    foundPiles = new ArrayList<List<Card>>();
    foundPiles.add(heartPile);
    foundPiles.add(spadesPile);
    foundPiles.add(clubsPile);
    foundPiles.add(diamondsPile);
    mod5 = new FreecellModel(FreecellModel.initEmptyPiles(4), foundPiles,
            FreecellModel.initEmptyPile(4), 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullDeck() {
    initExamples();
    c = new FreecellController(new StringReader(""), gameLog);
    c.playGame(null, mod1, 5, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullModel() {
    initExamples();
    c = new FreecellController(new StringReader(""), gameLog);
    c.playGame(deck1, null, 5, 4, false);
  }

  @Test
  public void testPlayGameIllegalDeck() {
    initExamples();
    c = new FreecellController(new StringReader(""), gameLog);
    c.playGame(illegalDeck, mod1, 5, 4, false);
    assertEquals("Could not start game.", gameLog.toString());
  }

  @Test
  public void testPlayGameIllegalCascades() {
    initExamples();
    c = new FreecellController(new StringReader(""), gameLog);
    c.playGame(deck1, mod1, 0, 4, false);
    assertEquals("Could not start game.", gameLog.toString());
  }

  @Test
  public void testPlayGameIllegalOpens() {
    initExamples();
    c = new FreecellController(new StringReader(""), gameLog);
    c.playGame(deck1, mod1, 5, -2, false);
    assertEquals("Could not start game.", gameLog.toString());
  }

  @Test
  public void testInvalidPileInput1() {
    initExamples();
    c = new FreecellController(new StringReader("F234 10 O1 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Re-enter source pile:",
            lines[lines.length - 2]);
  }

  @Test
  public void testInvalidPileInput2() {
    initExamples();
    c = new FreecellController(new StringReader("C234 10 O1 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Re-enter source pile:",
            lines[lines.length - 2]);
  }

  @Test
  public void testInvalidPileInput3() {
    initExamples();
    c = new FreecellController(new StringReader("0234 10 O1 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Re-enter source pile:",
            lines[lines.length - 2]);
  }

  @Test
  public void testInvalidPileInput4() {
    initExamples();
    c = new FreecellController(new StringReader("C1 10 O234678 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Re-enter destination pile:",
            lines[lines.length - 2]);
  }

  @Test
  public void testInvalidPileInput5() {
    initExamples();
    c = new FreecellController(new StringReader("C1 10 C23487 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Re-enter destination pile:",
            lines[lines.length - 2]);
  }

  @Test
  public void testInvalidPileInput6() {
    initExamples();
    c = new FreecellController(new StringReader("C1 10 F2345978 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Re-enter destination pile:",
            lines[lines.length - 2]);
  }

  @Test
  public void testLegalMove() {
    initExamples();
    c = new FreecellController(new StringReader("C5 10 O1 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    assertEquals(new Card(CardSuit.DIAMONDS, CardValue.JACK), mod1.getCard(PileType.OPEN, 0, 0));
  }

  @Test
  public void testIllegalCascMove() {
    initExamples();
    c = new FreecellController(new StringReader("C6 10 O1 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again. Pile number too high",
            lines[lines.length - 15]);
  }

  @Test
  public void testIllegalIndexMove() {
    initExamples();
    c = new FreecellController(new StringReader("C5 44 O1 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again. Card index not the last card in the pile",
            lines[lines.length - 15]);
  }

  @Test
  public void testIllegalOpenMove() {
    initExamples();
    c = new FreecellController(new StringReader("C5 10 O6 q"), gameLog);
    c.playGame(deck1, mod1, 5, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again. Destination open pile does not exist",
            lines[lines.length - 15]);
  }

  @Test
  public void testGameOver() {
    initExamples();
    c = new FreecellController(new StringReader(""), gameLog, false);
    c.playGame(deck1, mod5, 4, 4, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Game over.", lines[lines.length - 1]);
  }
}
