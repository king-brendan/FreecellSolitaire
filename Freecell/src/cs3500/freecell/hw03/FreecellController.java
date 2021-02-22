package cs3500.freecell.hw03;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw02.PileType;

/**
 * Represents a controller for the game of Freecell, that takes in input from the console and
 * displays the game state in the console. Input is entered as the source pile, the card index,
 * and the destination pile. If one input is entered incorrectly, the user will be prompted to
 * re-enter that input as well as the following ones.
 */
public class FreecellController implements IFreecellController<Card> {
  private final Readable rb;
  private final Appendable ap;
  private boolean legit;

  /**
   * Constructs a FreecellController with a given Readable and an Appendable.
   *
   * @param rb the Readable to be used
   * @param ap the Appendable to be used
   */
  public FreecellController(Readable rb, Appendable ap) {
    if ((rb == null) || (ap == null)) {
      throw new IllegalArgumentException("Input and output transmission can not be null");
    }
    this.rb = rb;
    this.ap = ap;
    this.legit = true;
  }

  /**
   * Constructs a FreecellController with a given Readable and an Appendable, and a boolean
   * for the purposes of testing. If legit is false, then the game can be started with any
   * model configuration the user wants and the given deck will not be shuffled amongst the
   * piles.
   *
   * @param rb the Readable to be used
   * @param ap the Appendable to be used
   * @param legit whether or not the game is to be initialized properly with startGame
   */
  public FreecellController(Readable rb, Appendable ap, boolean legit) {
    if ((rb == null) || (ap == null)) {
      throw new IllegalArgumentException("Input and output transmission can not be null");
    }
    this.rb = rb;
    this.ap = ap;
    this.legit = legit;
  }

  @Override
  public void playGame(List<Card> deck, FreecellOperations<Card> model,
                       int numCascades, int numOpens, boolean shuffle) {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    Scanner scan = new Scanner(rb);
    if (legit) {
      try {
        model.startGame(deck, numCascades, numOpens, shuffle);
      } catch (IllegalArgumentException | NullPointerException exc) {
        append("Could not start game.");
        return;
      }
    }

    while (!model.isGameOver()) {
      append(model.getGameState() + "\n");

      if (!scan.hasNext()) {
        break;
      }
      String input1;
      try {
        input1 = scan.next();
      } catch (NoSuchElementException exc) {
        throw new IllegalStateException("No input entered");
      }
      if ((input1.contains("q")) || (input1.contains("Q")))  {
        append("Game quit prematurely.");
        return;
      }

      if (!scan.hasNext()) {
        break;
      }
      String input2;
      try {
        input2 = scan.next();
      } catch (NoSuchElementException exc) {
        throw new IllegalStateException("No input entered");
      }
      if ((input2.contains("q")) || (input2.contains("Q")))  {
        append("Game quit prematurely.");
        return;
      }

      if (!scan.hasNext()) {
        break;
      }
      String input3;
      try {
        input3 = scan.next();
      } catch (NoSuchElementException exc) {
        throw new IllegalStateException("No input entered");
      }
      if ((input3.contains("q")) || (input3.contains("Q"))) {
        append("Game quit prematurely.");
        return;
      }

      PileType fromPileType = null;
      int fromPileNumber = -1;
      int cardIndex = -1;
      PileType toPileType = null;
      int toPileNumber = -1;

      if (!isValid(input1, 0)) {
        while (!isValid(input1, 0)) {
          append("Re-enter source pile:\n");
          try {
            input1 = scan.next();
          } catch (NoSuchElementException exc) {
            throw new IllegalStateException("No input entered");
          }
          if ((input1.contains("q")) || (input1.contains("Q"))) {
            append("Game quit prematurely.");
            return;
          }
        }
      }
      fromPileNumber = Character.getNumericValue(input1.charAt(1));
      fromPileType = PileType.getPileType(input1.charAt(0));

      if (!isValid(input2, 1)) {
        while (!isValid(input2, 1)) {
          append("Re-enter card index:\n");
          try {
            input2 = scan.next();
          } catch (NoSuchElementException exc) {
            throw new IllegalStateException("No input entered");
          }
          if ((input2.contains("q")) || (input2.contains("Q"))) {
            append("Game quit prematurely.");
            return;
          }
        }
      }
      cardIndex = Integer.parseInt(input2);

      if (!isValid(input3, 0)) {
        while (!isValid(input3, 0)) {
          append("Re-enter destination pile:\n");
          try {
            input3 = scan.next();
          } catch (NoSuchElementException exc) {
            throw new IllegalStateException("No input entered");
          }
          if ((input3.contains("q")) || (input3.contains("Q"))) {
            append("Game quit prematurely.");
            return;
          }
        }
      }
      toPileNumber = Character.getNumericValue(input3.charAt(1));
      toPileType = PileType.getPileType(input3.charAt(0));

      try {
        model.move(fromPileType, (fromPileNumber - 1), (cardIndex - 1),
                toPileType, (toPileNumber - 1));
      } catch (IllegalArgumentException | IllegalStateException exc) {
        append("Invalid move. Try again. " + exc.getMessage() + "\n");
      }
    }

    append(model.getGameState() + "\n" + "Game over.");
  }

  /**
   * Appends a given String to the Appendable.
   *
   * @param message the String to be appended to the Appendable
   */
  private void append(String message) {
    try {
      ap.append(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Determines whether a String is a valid input.
   *
   * @param s the input string
   * @param i 0 if the input is a card pile, 1 if it is a card index
   * @return whether the String is valid input or not
   */
  private static boolean isValid(String s, int i) {
    if (i == 0) {
      return ((s.length() == 2) && ((s.charAt(0) == 'C') || (s.charAt(0) == 'F')
              || (s.charAt(0) == 'O')) && (Character.isDigit(s.charAt(1))));
    } else if (i == 1) {
      try {
        Integer.parseInt(s);
        return true;
      } catch (NumberFormatException exc) {
        return false;
      }
    }
    return false;
  }



}
