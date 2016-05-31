package vitran.tienlen.game.engine;

import android.support.annotation.NonNull;

import vitran.tienlen.game.models.Card;
import vitran.tienlen.game.models.Deck;
import vitran.tienlen.game.models.Player;
import vitran.tienlen.game.models.TienLenPlayer;
import vitran.tienlen.game.models.TienLenTable;

public class TienLenGameEngine {

  private static final int MAX_NUM_PLAYERS = 4;

  private final TienLenTable table;
  private boolean isGameOver;

  public TienLenGameEngine() {
    table = new TienLenTable(MAX_NUM_PLAYERS);
  }

  public void reset() {
    isGameOver = false;
    shuffle();
    deal();
  }

  public void nextPlay(@NonNull TienLenCallback callback) {
    TienLenPlayer playerToAct = table.nextPlayer();
    if (playerToAct == null) {
      return;
    }

    callback.act(playerToAct);

    // figure out if this player won
    if (playerToAct.getHand().isEmpty()) {
      callback.gameOver(playerToAct, table);
      isGameOver = true;
    }
  }

  private void shuffle() {
    table.deck.shuffle();
  }

  private void deal() {
    Player[] players = table.players;
    Deck deck = table.deck;

    for (Card card : deck.cards) {
      for (int i = 0; i < MAX_NUM_PLAYERS; i++) {
        players[i].addCardToHand(card);
      }
    }
  }

  public interface TienLenCallback {
    /**
     * true for played
     * false for pass
     */
    boolean act(@NonNull TienLenPlayer player);

    void gameOver(@NonNull TienLenPlayer winner, @NonNull TienLenTable table);
  }
}
