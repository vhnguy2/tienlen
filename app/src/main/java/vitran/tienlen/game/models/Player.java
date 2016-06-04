package vitran.tienlen.game.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vitran.tienlen.game.exception.CardDoesNotExistException;

public class Player {

  private final long id;
  private final String name;
  private List<Card> hand;

  public Player(long id, @NonNull String name) {
    this.id = id;
    this.name = name;
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

  public void sortHand() {
    Collections.sort(hand);
  }

  public String getName() {
    return name;
  }

  public long getId() {
    return id;
  }
}
