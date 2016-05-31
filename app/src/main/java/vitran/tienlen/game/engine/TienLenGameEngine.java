package vitran.tienlen.game.engine;

import android.support.annotation.NonNull;

import vitran.tienlen.game.exception.CardDoesNotExistException;
import vitran.tienlen.game.models.Card;
import vitran.tienlen.game.models.Deck;
import vitran.tienlen.game.models.TienLenPlayHand;
import vitran.tienlen.game.models.TienLenPlayer;
import vitran.tienlen.game.models.TienLenTable;

public class TienLenGameEngine {

  private static final int MAX_NUM_PLAYERS = 4;

  private final TienLenTable table;
  private boolean isGameOver;

  public TienLenGameEngine() {
    table = new TienLenTable(MAX_NUM_PLAYERS);
  }

  public void reset(@NonNull TienLenDealCallback callback) {
    isGameOver = false;
    shuffle();
    deal(callback);
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

  public boolean play(
      @NonNull TienLenPlayer player,
      @NonNull TienLenPlayHand playHand,
      @NonNull TienLenPlayCallback callback) {
    TienLenPlayHand lastPlayHand = table.getLastPlayHand();
    if (lastPlayHand.compareTo(playHand) < 1) {
      return false;
    }

    try {
      // handle trumps
      boolean isTrumpPlay = lastPlayHand.isTrump(playHand);
      if (isTrumpPlay) {
        table.incrementNumOfConsecutiveTrumpPlays();
        callback.isTrump(
            table.getNumOfConsecutiveTrumpPlays(),
            // previous player to play a hand got trumped
            table.getPlayers()[table.getPrevPlayerToPlay()],
            player
        );
      } else {
        table.resetNumOfConsecutiveTrumpPlays();
      }

      // commit the playhand because it's a valid move
      player.removeCardsFromHand(playHand.cards);
      table.setLastPlayHand(playHand);
    } catch (CardDoesNotExistException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  private void shuffle() {
    table.getDeck().shuffle();
  }

  private void deal(@NonNull TienLenDealCallback callback) {
    TienLenPlayer[] players = table.getPlayers();
    Deck deck = table.getDeck();

    for (Card card : deck.cards) {
      for (int i = 0; i < MAX_NUM_PLAYERS; i++) {
        players[i].addCardToHand(card);
        callback.deal(players[i], card);
      }
    }
  }

  interface TienLenCallback {
    /**
     * true for played
     * false for pass
     */
    boolean act(@NonNull TienLenPlayer player);

    void gameOver(@NonNull TienLenPlayer winner, @NonNull TienLenTable table);
  }

  interface TienLenDealCallback {
    void deal(@NonNull TienLenPlayer player, @NonNull Card card);
  }

  interface TienLenPlayCallback {
    boolean isTrump(
        int numConsecutiveTrumps,
        @NonNull TienLenPlayer loser,
        @NonNull TienLenPlayer winner
    );
  }
}
