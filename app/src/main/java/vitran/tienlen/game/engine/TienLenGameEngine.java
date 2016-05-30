package vitran.tienlen.game.engine;

import vitran.tienlen.game.models.Card;
import vitran.tienlen.game.models.Deck;
import vitran.tienlen.game.models.Player;
import vitran.tienlen.game.models.Table;

public class TienLenGameEngine {

  private static final int MAX_NUM_PLAYERS = 4;

  private final Table table;

  public TienLenGameEngine() {
    table = new Table(MAX_NUM_PLAYERS);
  }

  public void shuffle() {
    table.deck.shuffle();
  }

  public void deal() {
    Player[] players = table.players;
    Deck deck = table.deck;

    for (Card card : deck.cards) {
      for (int i = 0; i < MAX_NUM_PLAYERS; i++) {
        players[i].addCardToHand(card);
      }
    }
  }
}
