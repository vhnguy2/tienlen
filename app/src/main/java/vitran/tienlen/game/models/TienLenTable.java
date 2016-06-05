package vitran.tienlen.game.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import vitran.tienlen.game.exception.PlayerAlreadyExistsException;

public class TienLenTable {
  private final TienLenPlayer[] players;
  private final Deck deck;
  private int currentPlayerToAct;
  private TienLenPlayHand lastPlayHand;
  private int numOfConsecutiveTrumpPlays;
  private int prevPlayerToPlay;

  public TienLenTable(int size) {
    deck = buildDefaultDeck();
    players = new TienLenPlayer[size];
    for (int i = 0; i < size; i++) {
      players[i] = new TienLenGhostPlayer();
    }
  }

  public void addPlayer(int position, @NonNull TienLenPlayer player) throws PlayerAlreadyExistsException {
    if (!(players[position] instanceof TienLenGhostPlayer)) {
      throw new PlayerAlreadyExistsException(String.format("Player at position %d already exists", position));
    }
    players[position] = player;
  }

  public void removePlayer(int position) {
    players[position] = null;
  }

  public void setLastPlayHand(@NonNull TienLenPlayHand playHand) {
    lastPlayHand = playHand;
    prevPlayerToPlay = currentPlayerToAct;
  }

  public boolean isReadyToPlay() {
    int numOfPlayers = 0;
    for (TienLenPlayer player : players) {
      numOfPlayers = player instanceof TienLenGhostPlayer ? numOfPlayers : numOfPlayers + 1;
    }

    return numOfPlayers > 1;
  }

  @Nullable
  public TienLenPlayer nextPlayer() {
    // ask all other players if they want to play, excluding the current player
    for (int i = 0; i < players.length - 1; i++) {
      currentPlayerToAct = (currentPlayerToAct + 1) % players.length;
      if (players[currentPlayerToAct].isInLoop) {
        return players[currentPlayerToAct];
      }
    }

    return null;
  }

  public TienLenPlayer[] getPlayers() {
    return players;
  }

  public Deck getDeck() {
    return deck;
  }

  public int getCurrentPlayerToAct() {
    return currentPlayerToAct;
  }

  public void setCurrentPlayerToAct(int currentPlayerToAct) {
    this.currentPlayerToAct = currentPlayerToAct;
  }

  public TienLenPlayHand getLastPlayHand() {
    return lastPlayHand;
  }

  public void resetNumOfConsecutiveTrumpPlays() {
    numOfConsecutiveTrumpPlays = 0;
  }

  public void incrementNumOfConsecutiveTrumpPlays() {
    numOfConsecutiveTrumpPlays++;
  }

  public int getNumOfConsecutiveTrumpPlays() {
    return numOfConsecutiveTrumpPlays;
  }

  public int getPrevPlayerToPlay() {
    return prevPlayerToPlay;
  }

  public void setPrevPlayerToPlay(int prevPlayerToPlay) {
    this.prevPlayerToPlay = prevPlayerToPlay;
  }

  public boolean isPlayable(@NonNull TienLenPlayHand hand) {
    return lastPlayHand == null || hand.isBetterThan(lastPlayHand);
  }

  private Deck buildDefaultDeck() {
    Deck deck = new Deck();
    for (Card.Suit suit : Card.Suit.values()) {
      for (Card.Value value : Card.Value.values()) {
        deck.cards.add(new TienLenCard(suit, value));
      }
    }
    return deck;
  }
}
