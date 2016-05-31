package vitran.tienlen.game.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import vitran.tienlen.game.exception.PlayerAlreadyExistsException;

public class TienLenTable {
  public final TienLenPlayer[] players;
  public final Deck deck;
  public int currentPlayerToAct;

  public TienLenTable(int size) {
    players = new TienLenPlayer[size];
    deck = new Deck();
  }

  public void addPlayer(int position, @NonNull TienLenPlayer player) throws PlayerAlreadyExistsException {
    if (players[position] != null) {
      throw new PlayerAlreadyExistsException(String.format("Player at position %d already exists", position));
    }
    players[position] = player;
  }

  public void removePlayer(int position) {
    players[position] = null;
  }

  @Nullable
  public TienLenPlayer nextPlayer() {
    for (int i = 0; i < players.length; i++) {
      currentPlayerToAct = (currentPlayerToAct + 1) % players.length;
      if (players[currentPlayerToAct].isInLoop) {
        return players[currentPlayerToAct];
      }
    }

    return null;
  }
}
