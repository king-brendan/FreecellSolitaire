package cs3500.freecell.hw03;

import java.io.InputStreamReader;
import java.util.List;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw04.AbstractModel;
import cs3500.freecell.hw04.FreecellMultiMoveModel;


/**
 * Runs a Freecell game interactively on the console.
 */
public class Main {

  /**
   * Runs a Freecell game interactively on the console.
   * @param args the arguments for main
   */
  public static void main(String[] args) {
    AbstractModel model = new FreecellMultiMoveModel();
    List<Card> deck = model.getDeck();
    int numCascades = 4;
    int numOpens = 9;
    boolean shuffle = false;
    new FreecellController(new InputStreamReader(System.in),
            System.out).playGame(deck, model, numCascades, numOpens, shuffle);
  }
}
