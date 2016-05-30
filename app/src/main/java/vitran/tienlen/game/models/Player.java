package vitran.tienlen.game.models;

import java.util.ArrayList;
import java.util.List;

import vitran.tienlen.game.exception.CardDoesNotExistException;

public class Player {

  public List<Card> hand;

  public Player() {
    hand = new ArrayList<>();
  }

  public void reset() {
    hand.clear();
  }

  public void addCardToHand(Card card) {
    hand.add(card);
  }

  public void removeCardsFromHand(List<Card> cards) throws CardDoesNotExistException {
    for (Card card : cards) {
      if (!hand.contains(card)) {
        throw new CardDoesNotExistException("Missing card: " + card);
      }
    }

    hand.removeAll(cards);
  }

  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }
}
