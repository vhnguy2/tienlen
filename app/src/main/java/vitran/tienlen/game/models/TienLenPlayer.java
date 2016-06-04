package vitran.tienlen.game.models;

import android.support.annotation.NonNull;

public class TienLenPlayer extends Player {

  public boolean isInLoop;

  public TienLenPlayer(long id, @NonNull String name) {
    super(id, name);
  }

  @Override
  public void reset() {
    super.reset();
    resetLoop();
  }

  public void resetLoop() {
    isInLoop = true;
  }

  public void pass() {
    isInLoop = false;
  }
}
