import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;

import static org.junit.Assert.assertEquals;

/**
 * Test the various move methods for Freecell.
 */
public class TestMove {
  FreecellModel mod3 = new FreecellModel();
  List<Card> deck2 = mod3.getDeck();

  private void initExamples() {
    mod3.startGame(deck2, 5, 4, false);
  }

  @Test
  public void testMoveFromCascToOpen() {
    initExamples();
    assertEquals(null, mod3.getOpenPiles().get(0));
    assertEquals(11, mod3.getCascadePiles().get(0).size());
    mod3.move(PileType.CASCADE, 0, 10, PileType.OPEN, 0);
    assertEquals(false, (mod3.getOpenPiles().get(0) == null));
    assertEquals(10, mod3.getCascadePiles().get(0).size());
  }

  @Test
  public void testMoveFromCascToFound() {
    initExamples();
    assertEquals(new ArrayList<Card>(), mod3.getFoundationPiles().get(3));
    mod3.move(PileType.CASCADE, 4, 9, PileType.OPEN, 0);
    mod3.move(PileType.CASCADE, 4, 8, PileType.OPEN, 1);
    mod3.move(PileType.CASCADE, 4, 7, PileType.FOUNDATION, 1);
    assertEquals(7, mod3.getCascadePiles().get(4).size());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromCascToCascExc() {
    initExamples();
    mod3.move(PileType.CASCADE, -1, 4, PileType.CASCADE, 2);
  }

  @Test
  public void testMoveFromOpenToFound() {
    initExamples();
    mod3.move(PileType.CASCADE, 4, 9, PileType.OPEN, 0);
    mod3.move(PileType.CASCADE, 4, 8, PileType.OPEN, 1);
    mod3.move(PileType.CASCADE, 4, 7, PileType.OPEN, 2);
    mod3.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 0);
    assertEquals(1, mod3.getFoundationPiles().get(0).size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromCascToFoundExc() {
    initExamples();
    mod3.move(PileType.CASCADE, 1, 2, PileType.FOUNDATION, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFromCascToNonEmptyOpen() {
    initExamples();
    mod3.move(PileType.CASCADE, 4, 9, PileType.OPEN, 0);
    mod3.move(PileType.CASCADE, 4, 8, PileType.OPEN, 0);
  }

  @Test
  public void myTestMoveToFoundationPilesFromOpenPiles4Open6CascadesNoShuffle() {
    FreecellModel modMY = new FreecellModel();
    modMY.startGame(deck2, 6, 4, false);
    modMY.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    modMY.move(PileType.CASCADE, 3, 7, PileType.OPEN, 1);
    modMY.move(PileType.CASCADE, 3, 6, PileType.OPEN, 2);
    modMY.move(PileType.CASCADE, 3, 5, PileType.OPEN, 3);
    modMY.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 0);
    assertEquals(null, modMY.getCard(PileType.OPEN, 2, 0));
  }
}
