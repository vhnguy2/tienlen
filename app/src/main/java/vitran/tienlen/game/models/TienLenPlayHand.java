package vitran.tienlen.game.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vitran.tienlen.game.engine.TienLenEvaluator;
import vitran.tienlen.game.exception.IllegalPlayTypeException;

public class TienLenPlayHand implements Comparable<TienLenPlayHand> {
  public List<Card> cards;
  public TienLenEvaluator.PlayType playType;

  public TienLenPlayHand(@NonNull List<Card> cards) throws IllegalPlayTypeException {
    this.cards = new ArrayList<>(cards);
    this.playType = TienLenEvaluator.computePlayType(cards);

    Collections.sort(this.cards);
  }

  @Override
  public int compareTo(@NonNull TienLenPlayHand rhs) {
    if (playType == rhs.playType) {
      return cards.get(0).compareTo(rhs.cards.get(0));
    }
    if (isTrump(rhs)) {
      return 1;
    }

    return -1;
  }

  public boolean isTrump(@NonNull TienLenPlayHand rhs) {
    // trumps 2
    if (playType == TienLenEvaluator.PlayType.SINGLE && hasTwo()) {
      if (rhs.playType == TienLenEvaluator.PlayType.PAIR_SERIES) {
        return true;
      }
      if (rhs.playType == TienLenEvaluator.PlayType.QUAD) {
        return true;
      }
    }

    // trumps a pair of 2s
    if (playType == TienLenEvaluator.PlayType.DOUBLE && hasTwo()) {
      return rhs.playType == TienLenEvaluator.PlayType.PAIR_SERIES && rhs.cards.size() == 8;
    }

    // trumps a trump
    if (playType == TienLenEvaluator.PlayType.PAIR_SERIES) {
      if (rhs.playType == TienLenEvaluator.PlayType.PAIR_SERIES) {
        if (cards.size() == rhs.cards.size()) {
          return cards.get(0).compareTo(rhs.cards.get(0)) < 0;
        }
        return cards.size() == 6 && rhs.cards.size() == 8;
      }
      return rhs.playType == TienLenEvaluator.PlayType.QUAD;
    }

    if (playType == TienLenEvaluator.PlayType.QUAD) {
      if (rhs.playType == TienLenEvaluator.PlayType.QUAD) {
        return cards.get(0).compareTo(rhs.cards.get(0)) < 0;
      }
      if (rhs.playType == TienLenEvaluator.PlayType.PAIR_SERIES) {
        return rhs.cards.size() == 8;
      }
    }

    return false;
  }

  public boolean hasTwo() {
    for (Card card : cards) {
      if (card.value == Card.Value.TWO) {
        return true;
      }
    }
    return false;
  }
}
