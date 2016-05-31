package vitran.tienlen.game.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

  public final List<Card> cards;

  public Deck() {
    cards = new ArrayList<>();
  }

  public void shuffle() {
    Collections.shuffle(cards);
  }
}
