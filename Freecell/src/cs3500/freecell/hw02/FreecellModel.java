package cs3500.freecell.hw02;

import java.util.List;

import cs3500.freecell.hw04.AbstractModel;

/**
 * Represents the game state of a game of Freecell.
 */
public class FreecellModel extends AbstractModel {

  /**
   * Creates a FreecellModel with all null values.
   */
  public FreecellModel() {
    this.cascadePiles = null;
    this.foundationPiles = null;
    this.openPiles = null;
    this.numCascadePiles = 0;
    this.numOpenPiles = 0;
    this.gameStarted = false;
  }

  /**
   * Constructs a Freecell model with access over all fields for the purpose of testing.
   *
   * @param cascadePiles the cascade piles
   * @param foundationPiles the foundation piles
   * @param openPiles the open piles
   * @param numCascadePiles the number of cascade piles
   * @param numOpenPiles the number of open piles
   */
  public FreecellModel(List<List<Card>> cascadePiles, List<List<Card>> foundationPiles,
                       List<Card> openPiles, int numCascadePiles, int numOpenPiles) {
    this.cascadePiles = cascadePiles;
    this.foundationPiles = foundationPiles;
    this.openPiles = openPiles;
    this.numCascadePiles = numCascadePiles;
    this.numOpenPiles = numOpenPiles;
    this.gameStarted = true;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) {
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

      if (cardIndex != (thisCPile.size() - 1)) {
        throw new IllegalArgumentException("Card index not the last card in the pile");
      }
      Card thisCard = thisCPile.get(cardIndex);
      moveInto(destination, destPileNumber, thisCard);
      thisCPile.remove(thisCard);
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
  
}
