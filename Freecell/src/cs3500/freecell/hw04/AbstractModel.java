package cs3500.freecell.hw04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.CardSuit;
import cs3500.freecell.hw02.CardValue;
import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw02.PileType;

/**
 * An abstract class that extends the interface to facilitate several helper methods that every
 * model class uses.
 */
public abstract class AbstractModel implements FreecellOperations<Card> {
  /**
   * Moved all fields for the model into the abstract class and made them all protected so that
   * FreecellModel and FreecellMultiMoveModel can access them from the hw04 package. I made this
   * decision in order to avoid repeating the exact same code for all methods that reference the
   * fields in the model classes. If AbstractModel, FreecellModel, and FreecellMultiMove model
   * were in the same package, then the fields could be private.
   */
  protected List<List<Card>> cascadePiles;
  protected List<List<Card>> foundationPiles;
  protected List<Card> openPiles;
  protected int numCascadePiles;
  protected int numOpenPiles;
  protected boolean gameStarted;

  /**
   * Moved this method from FreecellModel because it applies to FreecellMultiModel as well.
   */
  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (!isValid(deck)) {
      throw new IllegalArgumentException("Invalid deck");
    }

    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Illegal number of open piles");
    }
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Illegal number of cascade piles");
    }

    // initialize each pile to its proper size
    this.cascadePiles = initEmptyPiles(numCascadePiles);
    this.foundationPiles = initEmptyPiles(4);
    this.openPiles = initEmptyPile(numOpenPiles);
    this.numCascadePiles = numCascadePiles;
    this.numOpenPiles = numOpenPiles;

    // create a new object in memory to represent the deck so that the game state can't
    // be changed from elsewhere in the code
    List<Card> newDeck = new ArrayList<Card>();
    for (Card c : deck) {
      newDeck.add(new Card(c.getSuit(), c.getValue()));
    }

    // shuffle the deck if shuffle is true
    if (shuffle) {
      Collections.shuffle(newDeck);
    }

    // add the cards from the deck to the appropriate cascade pile
    for (int i = 0; i < newDeck.size(); i++) {
      int cpNum = i % numCascadePiles;
      cascadePiles.get(cpNum).add(newDeck.get(i));
    }

    this.gameStarted = true;
  }

  /**
   * Moves a card onto the end a destination pile.
   *
   * @param destination the PileType of the destination pile
   * @param destPileNumber the pile number of the destination pile
   * @param card the card to be moved
   */
  protected void moveInto(PileType destination, int destPileNumber, Card card) {
    if (card == null) {
      throw new IllegalArgumentException("Cannot move a null card");
    }
    if (destPileNumber < 0) {
      throw new IllegalArgumentException("Illegal destination pile number");
    }

    if (destination == PileType.CASCADE) {
      if ((destPileNumber >= this.numCascadePiles)) {
        throw new IllegalArgumentException("Destination foundation pile does not exist");
      }
      List<Card> thisCascPile = cascadePiles.get(destPileNumber);
      int cpSize = thisCascPile.size();
      Card lastCard = thisCascPile.get(cpSize - 1);
      if (cpSize == 0) {
        thisCascPile.add(card);
      }
      else if ((lastCard.getValue().intVal == (card.getValue().intVal + 1))
              && (CardSuit.differentColors(lastCard, card))) {
        thisCascPile.add(card);
      } else {
        throw new IllegalArgumentException("Cannot add card to cascade pile");
      }
    }

    else if (destination == PileType.OPEN) {
      if ((destPileNumber >= this.numOpenPiles)) {
        throw new IllegalArgumentException("Destination open pile does not exist");
      }
      if (openPiles.get(destPileNumber) == null) {
        openPiles.set(destPileNumber, card);
      } else {
        throw new IllegalArgumentException("Destination open pile already has a card in it");
      }
    }

    else if (destination == PileType.FOUNDATION) {
      if ((destPileNumber >= 4)) {
        throw new IllegalArgumentException("Destination foundation pile does not exist");
      }
      List<Card> thisFoundPile = foundationPiles.get(destPileNumber);
      int fpSize = thisFoundPile.size();
      if (fpSize == 0) {
        if (card.getValue() == CardValue.ACE) {
          thisFoundPile.add(card);
        } else {
          throw new IllegalArgumentException("Cannot add a non-ace to an empty foundation pile");
        }
      }
      else if ((thisFoundPile.get(fpSize - 1).getSuit() == card.getSuit())
              && (card.getValue().intVal == thisFoundPile.get(fpSize - 1).getValue().intVal + 1)) {
        thisFoundPile.add(card);
      } else {
        throw new IllegalArgumentException("Card cannot be added to foundation pile");
      }
    }
  }

  /**
   * Moved this method from FreecellModel because it applies to FreecellMultiModel as well.
   */
  public boolean isValid(List<Card> deck) {
    int numSpades = 0;
    int numHearts = 0;
    int numDiamonds = 0;
    int numClubs = 0;
    int numAces = 0;
    int numTwos = 0;
    int numThrees = 0;
    int numFours = 0;
    int numFives = 0;
    int numSixes = 0;
    int numSevens = 0;
    int numEights = 0;
    int numNines = 0;
    int numTens = 0;
    int numJacks = 0;
    int numQueens = 0;
    int numKings = 0;
    boolean isValid = true;

    if (deck.size() != 52) {
      isValid = false;
    }

    for (Card c : deck) {
      switch (c.getValue().intVal) {
        case 1:
          numAces++;
          break;
        case 2:
          numTwos++;
          break;
        case 3:
          numThrees++;
          break;
        case 4:
          numFours++;
          break;
        case 5:
          numFives++;
          break;
        case 6:
          numSixes++;
          break;
        case 7:
          numSevens++;
          break;
        case 8:
          numEights++;
          break;
        case 9:
          numNines++;
          break;
        case 10:
          numTens++;
          break;
        case 11:
          numJacks++;
          break;
        case 12:
          numQueens++;
          break;
        case 13:
          numKings++;
          break;
        default:
          throw new IllegalArgumentException("Illegal card value in deck");
      }
      switch (c.getSuit().label) {
        case "♣":
          numClubs++;
          break;
        case "♦":
          numDiamonds++;
          break;
        case "♥":
          numHearts++;
          break;
        case "♠":
          numSpades++;
          break;
        default:
          throw new IllegalArgumentException("Illegal card suit in deck");
      }
    }
    isValid = ((numAces == numTwos) && (numTwos == numThrees) && (numThrees == numFours)
            && (numFours == numFives) && (numFives == numSixes)
            && (numSixes == numSevens) && (numSevens == numEights) && (numEights == numNines)
            && (numNines == numTens) && (numTens == numJacks)
            && (numJacks == numQueens) && (numQueens == numKings) && (numKings == 4)
            && (numSpades == numClubs) && (numClubs == numDiamonds)
            && (numDiamonds == numHearts) && (numHearts == 13));
    return isValid;
  }

  @Override
  public boolean isGameOver() {
    if (!gameStarted) {
      return false;
    }

    for (int i = 0; i < 4; i++) {
      if (this.foundationPiles.get(i).size() != 13) {
        return false;
      }
    }

    ArrayList<CardSuit> pileSuits = new ArrayList<CardSuit>();
    for (int i = 0; i < 4; i++) {
      List<Card> thisPile = this.foundationPiles.get(i);
      CardSuit thisPileSuit = thisPile.get(i).getSuit();
      for (int j = 1; j < thisPile.size(); j++) {
        if (thisPile.get(j).getValue().intVal != thisPile.get(j - 1).getValue().intVal + 1) {
          return false;
        }
        if (thisPile.get(j).getSuit() != thisPileSuit) {
          return false;
        }
      }
      if (!pileSuits.contains(thisPileSuit)) {
        pileSuits.add(thisPileSuit);
      }
    }

    return (pileSuits.size() == 4);
  }

  @Override
  public Card getCard(PileType pile, int pileNumber, int cardIndex) {
    Card c = null;
    if (!gameStarted) {
      throw new IllegalStateException("Cannot get card when game hasn't started");
    }

    if (pile == PileType.CASCADE) {
      try {
        c = cascadePiles.get(pileNumber).get(cardIndex);
      } catch (NullPointerException | IndexOutOfBoundsException ex) {
        throw new IllegalArgumentException("Card is nonexistant");
      }
    } else if (pile == PileType.FOUNDATION) {
      try {
        c = foundationPiles.get(pileNumber).get(cardIndex);
      } catch (NullPointerException | IndexOutOfBoundsException ex) {
        throw new IllegalArgumentException("Card is nonexistant");
      }
    } else if ((pile == PileType.OPEN) && (cardIndex == 0)) {
      try {
        c = openPiles.get(pileNumber);
      } catch (NullPointerException | IndexOutOfBoundsException ex) {
        throw new IllegalArgumentException("Card is nonexistant");
      }
    } else {
      throw new IllegalArgumentException("Card is nonexistant");
    }

    return c;
  }

  @Override
  public String getGameState() {

    if (this.getCascadePiles() == null) {
      return "";
    }
    String result = "";

    for (int i = 0; i < 4; i++) {
      result = result + "F" + (i + 1) + ":";
      if (foundationPiles.get(i).size() == 0) {
        result = result + "\n";
      } else {
        for (int j = 0; j < foundationPiles.get(i).size(); j++) {
          if (j == (foundationPiles.get(i).size() - 1)) {
            result = result + " " + foundationPiles.get(i).get(j).getValue().label
                    + foundationPiles.get(i).get(j).getSuit().label;
          } else {
            result = result + " " + foundationPiles.get(i).get(j).getValue().label
                    + foundationPiles.get(i).get(j).getSuit().label + ",";
          }
        }
        result = result + "\n";
      }
    }

    for (int i = 0; i < openPiles.size(); i++) {
      result = result + "O" + (i + 1) + ":";
      if (openPiles.get(i) != null) {
        result = result + " " + openPiles.get(i).getValue().label
                + openPiles.get(i).getSuit().label;
      }
      result = result + "\n";
    }

    for (int i = 0; i < cascadePiles.size(); i++) {
      result = result + "C" + (i + 1) + ":";
      if (cascadePiles.get(i).size() == 0) {
        if (i != (cascadePiles.size() - 1)) {
          result = result + "\n";
        }
      } else {
        for (int j = 0; j < cascadePiles.get(i).size(); j++) {
          if (j == (cascadePiles.get(i).size() - 1)) {
            result = result + " " + cascadePiles.get(i).get(j).getValue().label
                    + cascadePiles.get(i).get(j).getSuit().label;
          } else {
            result = result + " " + cascadePiles.get(i).get(j).getValue().label
                    + cascadePiles.get(i).get(j).getSuit().label + ",";
          }
        }
        if (i != (cascadePiles.size() - 1)) {
          result = result + "\n";
        }
      }
    }

    return result;
  }

  /**
   * Initializes an ArrayList of a certain size with empty Card ArrayLists.
   * Moved this method from FreecellModel into the abstract class
   *
   * @param num the size of the List to be created
   * @return a List of type ArrayList of size num.
   */
  public static List<List<Card>> initEmptyPiles(int num) {
    List<List<Card>> l = new ArrayList<List<Card>>();
    for (int i = 0; i < num; i++) {
      l.add(new ArrayList<Card>());
    }
    return l;
  }

  /**
   * Initializes a list of null values of a certain size.
   * Moved this method from FreecellModel into the abstract class
   *
   * @param num the size of the null list to be created
   * @return a list of null values of a certain size.
   */
  public static List<Card> initEmptyPile(int num) {
    ArrayList<Card> result = new ArrayList<Card>();
    for (int i = 0; i < num; i++) {
      result.add(null);
    }
    return result;
  }

  /**
   * Moved this method from FreecellModel into the abstract class.
   */
  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>(52);
    for (int i = 0; i < 52; i++) {
      if ((i / 13) == 0) {
        deck.add(new Card(CardSuit.CLUBS, CardValue.getVal(i)));
      } else if ((i / 13) == 1) {
        deck.add(new Card(CardSuit.SPADES, CardValue.getVal(i % 13)));
      } else if ((i / 13) == 2) {
        deck.add(new Card(CardSuit.HEARTS, CardValue.getVal(i % 13)));
      } else if ((i / 13) == 3) {
        deck.add(new Card(CardSuit.DIAMONDS, CardValue.getVal(i % 13)));
      }
    }
    return deck;
  }

  /**
   * Returns the open piles.
   * @return the open piles
   */
  public List<Card> getOpenPiles() {
    return this.openPiles;
  }

  /**
   * returns the foundation piles.
   * @return the foundation piles
   */
  public List<List<Card>> getFoundationPiles() {
    return this.foundationPiles;
  }

  /**
   * returns the cascade piles.
   * @return the cascade piles
   */
  public List<List<Card>> getCascadePiles() {
    return this.cascadePiles;
  }


}
