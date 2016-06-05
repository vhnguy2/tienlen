package vitran.tienlen.game.models;

import android.support.annotation.NonNull;

import java.util.Comparator;

public abstract class Card implements Comparator<Card>, Comparable<Card> {

  public enum Suit {
    SPADE, CLUB, DIAMOND, HEART
  }

  public enum Value {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE
  }

  @Override
  public abstract int compare(Card lhs, Card rhs);

  @Override
  public abstract int compareTo(Card rhs);

  public abstract int id();

  public final Suit suit;
  public final Value value;

  public Card(@NonNull Suit suit, @NonNull Value value) {
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
