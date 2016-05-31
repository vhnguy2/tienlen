package vitran.tienlen.game.engine;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

import vitran.tienlen.game.exception.IllegalPlayTypeException;
import vitran.tienlen.game.models.Card;

public class TienLenEvaluator {

  public enum PlayType {
    SINGLE, DOUBLE, TRIPLE, SERIES, PAIR_SERIES, QUAD
  }

  public static PlayType computePlayType(@NonNull List<Card> cards) throws IllegalPlayTypeException {
    if (isSingle(cards)) {
      return PlayType.SINGLE;
    }

    if (isDouble(cards)) {
      return PlayType.DOUBLE;
    }

    if (isTriple(cards)) {
      return PlayType.TRIPLE;
    }

    if (isQuad(cards)) {
      return PlayType.QUAD;
    }

    if (isSeries(cards)) {
      return PlayType.SERIES;
    }

    if (isPairSeries(cards)) {
      return PlayType.PAIR_SERIES;
    }

    throw new IllegalPlayTypeException("Unable to determine the PlayType from cards");
  }

  private static boolean isSingle(@NonNull List<Card> cards) {
    return cards.size() == 1;
  }

  private static boolean isDouble(@NonNull List<Card> cards) {
    return cards.size() == 2 && cards.get(0).value == cards.get(1).value;
  }

  private static boolean isTriple(@NonNull List<Card> cards) {
    return
        cards.size() == 3 &&
        cards.get(0).value == cards.get(1).value &&
        cards.get(1).value == cards.get(2).value;
  }

  private static boolean isQuad(@NonNull List<Card> cards) {
    return
        cards.size() == 4 &&
        cards.get(0).value == cards.get(1).value &&
        cards.get(1).value == cards.get(2).value &&
        cards.get(2).value == cards.get(3).value;
  }

  private static boolean isSeries(@NonNull List<Card> cards) {
    if (cards.size() < 3) {
      return false;
    }

    Collections.sort(cards);
    Card prevCard = cards.get(0);

    // cannot contain a 2
    if (prevCard.value == Card.Value.TWO) {
      return false;
    }

    for (int i = 1; i < cards.size(); i++) {
      Card currCard = cards.get(i);
      // must be consecutively descending
      if (prevCard.value.ordinal() - 1 != currCard.value.ordinal()) {
        return false;
      }
    }

    return true;
  }

  private static boolean isPairSeries(@NonNull List<Card> cards) {
    if (cards.size() < 6 || cards.size() % 2 == 0) {
      return false;
    }

    Collections.sort(cards);

    Card.Value prevValue = null;

    for (int i = 0; i < cards.size(); i += 2) {
      if (cards.get(i).value != cards.get(i+1).value) {
        return false;
      }
      Card.Value currValue = cards.get(0).value;

      // cannot contain a 2
      if (currValue == Card.Value.TWO) {
        return false;
      }

      // must be consecutively descending
      if (prevValue != null && prevValue.ordinal() - 1 == currValue.ordinal()) {
        return false;
      }

      prevValue = currValue;
    }

    return true;
  }
}
