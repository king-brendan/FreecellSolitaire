import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw03.FreecellController;
import cs3500.freecell.hw04.FreecellMultiMoveModel;

import static org.junit.Assert.assertEquals;


/**
 * Tests the various methods for the FreecellMultiMoveModel class.
 */
public class Hw04MultiMoveTest {
  FreecellMultiMoveModel mod1 = new FreecellMultiMoveModel();
  List<Card> deck1 = mod1.getDeck();
  FreecellMultiMoveModel mod2 = new FreecellMultiMoveModel();
  List<Card> deck2 = mod2.getDeck();
  FreecellMultiMoveModel mod3 = new FreecellMultiMoveModel();
  List<Card> deck3 = mod3.getDeck();
  FreecellController c;
  StringBuilder gameLog;

  /**
   * Initializes examples for the Multi Move tests.
   */
  private void initExamples() {
    mod1.startGame(deck1, 4, 4, false);
    mod2.startGame(deck2, 4, 20, false);
    mod3.startGame(deck3, 4, 10, false);
    gameLog = new StringBuilder();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCardIndexSrcCascadeDestCascade() {
    initExamples();
    mod1.move(PileType.CASCADE, 2, 20, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCardIndexSrcCascadeDestCascade2() {
    initExamples();
    mod1.move(PileType.CASCADE, 2, -5, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveDestFound() {
    initExamples();
    mod1.move(PileType.CASCADE, 2, -5, PileType.FOUNDATION, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveDestOpen() {
    initExamples();
    mod1.move(PileType.CASCADE, 2, -5, PileType.OPEN, 3);
  }

  @Test
  public void testValidMultiMove() {
    initExamples();
    c = new FreecellController(new StringReader("C4 13 O1 C2 13 O2 C1 13 O3 C1 12 " +
            "O4 C1 11 O5 C1 10 O6 C1 9 O7 C1 8 O8 C1 7 O9 C2 12 C1 C1 6 C4 q"), gameLog);
    c.playGame(deck2, mod2, 4, 20, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("C4: 4♣, 8♣, Q♣, 3♠, 7♠, J♠, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, 8♠, 7♦",
            lines[lines.length - 2]);
  }

  @Test
  public void testInvalidMultiMoveNotEnoughOpenPiles() {
    initExamples();
    c = new FreecellController(new StringReader("C4 13 O1 C2 13 O2 C1 13 O3 C1 12 " +
            "O4 C1 11 O5 C1 10 O6 C1 9 O7 C1 8 O8 C1 7 O9 C2 12 C1 C1 6 C4 q"), gameLog);
    c.playGame(deck3, mod3, 4, 9, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again." +
                    " Not enough empty open or cascade piles to allow a build move",
            lines[lines.length - 19]);
  }

  @Test
  public void testInvalidMultiMoveinvalidBuild() {
    initExamples();
    c = new FreecellController(new StringReader("C4 13 O1 C2 13 O2 C1 13 O3 C1 12 " +
            "O4 C1 11 O5 C1 10 O6 C1 9 O7 C1 8 O8 C1 7 O9 C2 12 C1 C2 3 C4 q"), gameLog);
    c.playGame(deck3, mod3, 4, 9, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again." +
                    " Build is invalid",
            lines[lines.length - 19]);
  }

  @Test
  public void testInvalidMultiMoveToNonCascade() {
    initExamples();
    c = new FreecellController(new StringReader("C4 13 O1 C2 13 O2 C1 13 O3 C1 12 " +
            "O4 C1 11 O5 C1 10 O6 C1 9 O7 C1 8 O8 C1 7 O9 C2 12 C1 C2 3 F4 q"), gameLog);
    c.playGame(deck3, mod3, 4, 9, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again." +
                    " Cannot move a multi-card build to a non-cascade pile",
            lines[lines.length - 19]);
  }

  @Test
  public void testInvalidMultiMovePileNumTooHigh() {
    initExamples();
    c = new FreecellController(new StringReader("C4 13 O1 C2 13 O2 C1 13 O3 C1 12 " +
            "O4 C1 11 O5 C1 10 O6 C1 9 O7 C1 8 O8 C1 7 O9 C2 12 C1 C8 3 F4 q"), gameLog);
    c.playGame(deck3, mod3, 4, 9, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again." +
                    " Pile number too high",
            lines[lines.length - 19]);
  }

  @Test
  public void testInvalidMultiMoveIllegalSourceIndex() {
    initExamples();
    c = new FreecellController(new StringReader("C4 13 O1 C2 13 O2 C1 13 O3 C1 12 " +
            "O4 C1 11 O5 C1 10 O6 C1 9 O7 C1 8 O8 C1 7 O9 C2 12 C1 C2 30 F4 q"), gameLog);
    c.playGame(deck3, mod3, 4, 9, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again." +
                    " Source card index is illegal",
            lines[lines.length - 19]);
  }

  @Test
  public void testInvalidMultiMoveBuildDoesntMatch() {
    initExamples();
    c = new FreecellController(new StringReader("C4 13 O1 C2 13 O2 C1 13 O3 C1 12 " +
            "O4 C1 11 O5 C1 10 O6 C1 9 O7 C1 8 O8 C1 7 O9 C2 12 C1 C1 6 C3 q"), gameLog);
    c.playGame(deck2, mod2, 4, 20, false);
    String[] lines = gameLog.toString().split("\n");
    assertEquals("Invalid move. Try again." +
                    " Build cannot be added to end of Cascade pile",
            lines[lines.length - 30]);
  }

}
