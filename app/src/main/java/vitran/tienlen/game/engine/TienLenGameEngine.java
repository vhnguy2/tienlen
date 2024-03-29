package vitran.tienlen.game.engine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vitran.tienlen.game.exception.CardDoesNotExistException;
import vitran.tienlen.game.exception.PlayerAlreadyExistsException;
import vitran.tienlen.game.models.Card;
import vitran.tienlen.game.models.Deck;
import vitran.tienlen.game.models.TienLenPlayHand;
import vitran.tienlen.game.models.TienLenPlayer;
import vitran.tienlen.game.models.TienLenTable;

/**
 * TODO(viet): expand to support multiple players
 */
public class TienLenGameEngine {

  private static final int MAX_NUM_PLAYERS = 4;

  private final FirebaseDatabase database = FirebaseDatabase.getInstance();
  private final DatabaseReference dbRef;

  private final TienLenTable table;
  private boolean isGameOver;

  public TienLenGameEngine() {
    table = new TienLenTable(MAX_NUM_PLAYERS);
    dbRef = database.getReference("table1");
  }

  public void addPlayer(int position, TienLenPlayer player) throws PlayerAlreadyExistsException {
    table.addPlayer(position, player);
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
    if (lastPlayHand != null && lastPlayHand.isBetterThan(playHand)) {
      return false;
    }

    try {
      // handle trumps
      boolean isTrumpPlay = lastPlayHand != null && lastPlayHand.isTrump(playHand);
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

    callback.onCompleted();
    return true;
  }

  public boolean isSelectionPlayable(@Nullable TienLenPlayHand playHand) {
    return playHand != null && table.isPlayable(playHand);
  }

  private void shuffle() {
    table.getDeck().shuffle();
  }

  private void deal(@NonNull TienLenDealCallback callback) {
    if (!table.isReadyToPlay()) {
      callback.onDealFailed();
      return;
    }

    TienLenPlayer[] players = table.getPlayers();
    Deck deck = table.getDeck();

    int currPlayer = 0;
    for (Card card : deck.cards) {
      players[currPlayer].addCardToHand(card);
      callback.deal(players[currPlayer], card);

      currPlayer = (currPlayer + 1) % MAX_NUM_PLAYERS;
    }

    callback.onCompleted();
  }

  interface TienLenCallback {
    /**
     * true for played
     * false for pass
     */
    boolean act(@NonNull TienLenPlayer player);

    void gameOver(@NonNull TienLenPlayer winner, @NonNull TienLenTable table);
  }

  public interface TienLenDealCallback {
    void deal(@NonNull TienLenPlayer player, @NonNull Card card);
    void onCompleted();
    void onDealFailed();
  }

  public interface TienLenPlayCallback {
    boolean isTrump(
        int numConsecutiveTrumps,
        @NonNull TienLenPlayer loser,
        @NonNull TienLenPlayer winner
    );
    void onCompleted();
  }
}
