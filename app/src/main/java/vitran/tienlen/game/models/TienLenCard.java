package vitran.tienlen.game.models;

import android.support.annotation.NonNull;

public class TienLenCard extends Card {

  public TienLenCard(@NonNull Suit suit, int value) {
    super(suit, value);
  }

  @Override
  public int compare(Card lhs, Card rhs) {
    if (lhs.value == rhs.value) {
      return lhs.suit.ordinal() - rhs.suit.ordinal();
    }
    return lhs.value - rhs.value;
  }

  @Override
  public int id() {
    return suit.ordinal() * Suit.values().length + value;
  }
}
