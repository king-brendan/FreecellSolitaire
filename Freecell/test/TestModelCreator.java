import org.junit.Test;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw04.FreecellModelCreator;
import cs3500.freecell.hw04.FreecellMultiMoveModel;

import static org.junit.Assert.assertEquals;

/**
 * Tests the FreecellModelCreator class.
 */
public class TestModelCreator {
  @Test
  public void testCreateSingleMove() {
    FreecellOperations<Card> mod1 =
            FreecellModelCreator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    assertEquals(true, (mod1 instanceof FreecellModel));
    assertEquals(null, ((FreecellModel) mod1).getFoundationPiles());
    assertEquals(null, ((FreecellModel) mod1).getOpenPiles());
  }

  @Test
  public void testCreateMultiMove() {
    FreecellOperations<Card> mod2 =
            FreecellModelCreator.create(FreecellModelCreator.GameType.MULTIMOVE);
    assertEquals(true, (mod2 instanceof FreecellMultiMoveModel));
    assertEquals(null, ((FreecellMultiMoveModel) mod2).getFoundationPiles());
    assertEquals(null, ((FreecellMultiMoveModel) mod2).getOpenPiles());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateInvalidGameType() {
    FreecellOperations<Card> mod3 = FreecellModelCreator.create(null);
  }
}
