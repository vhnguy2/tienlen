package vitran.tienlen.game.models;

import android.support.annotation.NonNull;

public class TienLenCard extends Card {

  public TienLenCard(@NonNull Suit suit, @NonNull Value value) {
    super(suit, value);
  }

  @Override
  public int compare(Card lhs, Card rhs) {
    return rhs.id() - lhs.id();
  }

  @Override
  public int compareTo(Card rhs) {
    return compare(this, rhs);
  }

  @Override
  public int id() {
    return suit.ordinal() + getOrderValue() * Value.values().length;
  }

  private int getOrderValue() {
    switch (value) {
      case THREE:
        return 3;
      case FOUR:
        return 4;
      case FIVE:
        return 5;
      case SIX:
        return 6;
      case SEVEN:
        return 7;
      case EIGHT:
        return 8;
      case NINE:
        return 9;
      case TEN:
        return 10;
      case JACK:
        return 11;
      case QUEEN:
        return 12;
      case KING:
        return 13;
      case ACE:
        return 14;
      case TWO:
        return 15;
    }

    return -1;
  }
}
