package vitran.tienlen.game.models;

import android.support.annotation.NonNull;

import java.util.Comparator;

public abstract class Card implements Comparator<Card> {

  public enum Suit {
    HEART, DIAMOND, CLUB, SPADE
  }

  @Override
  public abstract int compare(Card lhs, Card rhs);

  public abstract int id();

  public final Suit suit;
  public final int value;

  public Card(@NonNull Suit suit, int value) {
    this.suit = suit;
    this.value = value;
  }

  @Override
  public int hashCode() {
    return id();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (!(o instanceof Card)) {
      return false;
    }

    Card compareCard = (Card) o;
    return compareCard.value == value && compareCard.suit == suit;
  }
}
