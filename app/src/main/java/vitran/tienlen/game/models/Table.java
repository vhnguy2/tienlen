package vitran.tienlen.game.models;

import android.support.annotation.NonNull;

import vitran.tienlen.game.exception.PlayerAlreadyExistsException;

public class Table {
  public final Player[] players;
  public final Deck deck;

  public Table(int size) {
    players = new Player[size];
    deck = new Deck();
  }

  public void addPlayer(int position, @NonNull Player player) throws PlayerAlreadyExistsException {
    if (players[position] != null) {
      throw new PlayerAlreadyExistsException(String.format("Player at position %d already exists", position));
    }
    players[position] = player;
  }

  public void removePlayer(int position) {
    players[position] = null;
  }
}
