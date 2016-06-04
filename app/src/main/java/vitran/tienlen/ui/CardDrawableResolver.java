package vitran.tienlen.ui;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import vitran.tienlen.R;
import vitran.tienlen.game.models.Card;

public class CardDrawableResolver {

  public static @DrawableRes int resolve(@NonNull Card card) {
    switch (card.suit) {
      case SPADE:
        return resolveSpade(card);
      case CLUB:
        return resolveClub(card);
      case DIAMOND:
        return resolveDiamond(card);
      case HEART:
        return resolveHeart(card);
    }

    return 0;
  }

  private static @DrawableRes int resolveSpade(@NonNull Card card) {
    switch (card.value) {
      case TWO:
        return R.drawable.two_of_spades;
      case THREE:
        return R.drawable.three_of_spades;
      case FOUR:
        return R.drawable.four_of_spades;
      case FIVE:
        return R.drawable.five_of_spades;
      case SIX:
        return R.drawable.six_of_spades;
      case SEVEN:
        return R.drawable.seven_of_spades;
      case EIGHT:
        return R.drawable.eight_of_spades;
      case NINE:
        return R.drawable.nine_of_spades;
      case TEN:
        return R.drawable.ten_of_spades;
      case JACK:
        return R.drawable.jack_of_spades;
      case QUEEN:
        return R.drawable.queen_of_spades;
      case KING:
        return R.drawable.king_of_spades;
      case ACE:
        return R.drawable.ace_of_spades;
    }

    return 0;
  }

  private static @DrawableRes int resolveClub(@NonNull Card card) {
    switch (card.value) {
      case TWO:
        return R.drawable.two_of_clubs;
      case THREE:
        return R.drawable.three_of_clubs;
      case FOUR:
        return R.drawable.four_of_clubs;
      case FIVE:
        return R.drawable.five_of_clubs;
      case SIX:
        return R.drawable.six_of_clubs;
      case SEVEN:
        return R.drawable.seven_of_clubs;
      case EIGHT:
        return R.drawable.eight_of_clubs;
      case NINE:
        return R.drawable.nine_of_clubs;
      case TEN:
        return R.drawable.ten_of_clubs;
      case JACK:
        return R.drawable.jack_of_clubs;
      case QUEEN:
        return R.drawable.queen_of_clubs;
      case KING:
        return R.drawable.king_of_clubs;
      case ACE:
        return R.drawable.ace_of_clubs;
    }

    return 0;
  }

  private static @DrawableRes int resolveDiamond(@NonNull Card card) {
    switch (card.value) {
      case TWO:
        return R.drawable.two_of_diamonds;
      case THREE:
        return R.drawable.three_of_diamonds;
      case FOUR:
        return R.drawable.four_of_diamonds;
      case FIVE:
        return R.drawable.five_of_diamonds;
      case SIX:
        return R.drawable.six_of_diamonds;
      case SEVEN:
        return R.drawable.seven_of_diamonds;
      case EIGHT:
        return R.drawable.eight_of_diamonds;
      case NINE:
        return R.drawable.nine_of_diamonds;
      case TEN:
        return R.drawable.ten_of_diamonds;
      case JACK:
        return R.drawable.jack_of_diamonds;
      case QUEEN:
        return R.drawable.queen_of_diamonds;
      case KING:
        return R.drawable.king_of_diamonds;
      case ACE:
        return R.drawable.ace_of_diamonds;
    }

    return 0;
  }

  private static @DrawableRes int resolveHeart(@NonNull Card card) {
    switch (card.value) {
      case TWO:
        return R.drawable.two_of_hearts;
      case THREE:
        return R.drawable.three_of_hearts;
      case FOUR:
        return R.drawable.four_of_hearts;
      case FIVE:
        return R.drawable.five_of_hearts;
      case SIX:
        return R.drawable.six_of_hearts;
      case SEVEN:
        return R.drawable.seven_of_hearts;
      case EIGHT:
        return R.drawable.eight_of_hearts;
      case NINE:
        return R.drawable.nine_of_hearts;
      case TEN:
        return R.drawable.ten_of_hearts;
      case JACK:
        return R.drawable.jack_of_hearts;
      case QUEEN:
        return R.drawable.queen_of_hearts;
      case KING:
        return R.drawable.king_of_hearts;
      case ACE:
        return R.drawable.ace_of_hearts;
    }

    return 0;
  }
}
