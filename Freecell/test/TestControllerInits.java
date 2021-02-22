import org.junit.Test;

import java.io.InputStreamReader;

import cs3500.freecell.hw03.FreecellController;


/**
 * Tests the various controller intialization methods.
 */
public class TestControllerInits {
  @Test(expected = IllegalArgumentException.class)
  public void testNoRBConstruct() {
    FreecellController fc1 = new FreecellController(null, System.out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoAPConstruct() {
    FreecellController fc1 = new FreecellController(new InputStreamReader(System.in), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNoConstruct() {
    FreecellController fc1 = new FreecellController(null, null);
  }
}
