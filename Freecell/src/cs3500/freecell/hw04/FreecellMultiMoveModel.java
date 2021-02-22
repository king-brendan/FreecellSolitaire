package cs3500.freecell.hw04;

import java.util.ArrayList;
import java.util.List;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.CardSuit;
import cs3500.freecell.hw02.PileType;

/**
 * Represents a model for the Freecell game that allows for multi-card moves.
 */
public class FreecellMultiMoveModel extends AbstractModel {

  /**
   * Creates a FreecellMultiMoveModel with all null values.
   */
  public FreecellMultiMoveModel() {
    this.cascadePiles = null;
    this.foundationPiles = null;
    this.openPiles = null;
    this.numCascadePiles = 0;
    this.numOpenPiles = 0;
    this.gameStarted = false;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex,
                   PileType destination, int destPileNumber) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet");
    }

    if ((pileNumber < 0) || (destPileNumber < 0)) {
      throw new IllegalArgumentException("Pile number cannot be negative");
    }

    if (source == PileType.CASCADE) {
      if (pileNumber >= numCascadePiles) {
        throw new IllegalArgumentException("Pile number too high");
      }
      List<Card> thisCPile = this.cascadePiles.get(pileNumber);

      if (cardIndex == (thisCPile.size() - 1)) {
        Card thisCard = thisCPile.get(cardIndex);
        moveInto(destination, destPileNumber, thisCard);
        thisCPile.remove(thisCard);
      } else if ((cardIndex >= 0) && (cardIndex <= (thisCPile.size() - 1))) {
        if (destination != PileType.CASCADE) {
          throw new IllegalArgumentException(
                  "Cannot move a multi-card build to a non-cascade pile");
        }
        List<Card> build = getBuild(pileNumber, cardIndex);
        moveBuild(build, destPileNumber);
        removeCards(pileNumber, build);
      } else {
        throw new IllegalArgumentException("Source card index is illegal");
      }

    }

    else if (source == PileType.FOUNDATION) {
      throw new IllegalArgumentException("Cannot move a card from a foundation pile");
    }

    else if (source == PileType.OPEN) {
      if (cardIndex != 0) {
        throw new IllegalArgumentException("Can only move first card from open pile");
      }
      if (pileNumber >= this.numOpenPiles) {
        throw new IllegalArgumentException("Illegal open source pile number");
      }
      Card thisOCard = this.openPiles.get(pileNumber);
      moveInto(destination, destPileNumber, thisOCard);
      this.openPiles.set(pileNumber, null);
    }
  }

  /**
   * Retrieves every card from a certain index onward from a cascade pile.
   *
   * @param pileNumber the pile number of the source pile
   * @param cardIndex the index of the starting card in the build
   * @return the build, from the starting card onwards.
   */
  private List<Card> getBuild(int pileNumber, int cardIndex) {
    List<Card> result = new ArrayList<Card>();
    List<Card> pile = cascadePiles.get(pileNumber);
    if (pile.size() == 0) {
      return result;
    }
    for (int i = cardIndex; i < pile.size(); i++) {
      Card c = pile.get(i);
      result.add(new Card(c.getSuit(), c.getValue()));
    }
    return result;
  }

  /**
   * Checks if a proposed build move is valid and moves it to the proper pile.
   *
   * @param build the build
   * @param destPileNumber the destination pile
   */
  private void moveBuild(List<Card> build, int destPileNumber) {
    if (!isValidBuild(build)) {
      throw new IllegalArgumentException("Build is invalid");
    }
    int emptyOpens = 0;
    for (Card c : openPiles) {
      if (c == null) {
        emptyOpens++;
      }
    }
    int emptyCascades = 0;
    for (List<Card> l : cascadePiles) {
      if (l.size() == 0) {
        emptyCascades++;
      }
    }
    if (build.size() > ((emptyOpens + 1) * Math.pow(2, emptyCascades))) {
      throw new IllegalArgumentException(
              "Not enough empty open or cascade piles to allow a build move");
    }
    List<Card> pile = cascadePiles.get(destPileNumber);
    if (pile.size() == 0) {
      for (Card c : build) {
        pile.add(new Card(c.getSuit(), c.getValue()));
      }
    } else if (CardSuit.differentColors(build.get(0), pile.get(pile.size() - 1))
            && (build.get(0).getValue().intVal
            == (pile.get(pile.size() - 1).getValue().intVal - 1))) {
      for (Card c : build) {
        pile.add(new Card(c.getSuit(), c.getValue()));
      }
    } else {
      throw new IllegalArgumentException("Build cannot be added to end of Cascade pile");
    }
  }

  /**
   * Determines if a build of cards is in proper order and alternating suits.
   *
   * @param build the build of cards
   * @return whether the build is valid or not.
   */
  private boolean isValidBuild(List<Card> build) {
    if ((build.size() == 0) || (build.size() == 1)) {
      return true;
    }
    for (int i = 1; i < build.size(); i++) {
      boolean result = (CardSuit.differentColors(build.get(i), build.get(i - 1))
              && (build.get(i).getValue().intVal == (build.get(i - 1).getValue().intVal - 1)));
      if (!result) {
        return false;
      }
    }
    return true;
  }

  /**
   * Removes a list of cards from the end of a Cascade pile.
   *
   * @param pileNumber the pile number
   * @param build the list of cards to be removed
   */
  private void removeCards(int pileNumber, List<Card> build) {
    List<Card> pile = cascadePiles.get(pileNumber);
    for (Card c : build) {
      pile.remove(c);
    }
  }

}
