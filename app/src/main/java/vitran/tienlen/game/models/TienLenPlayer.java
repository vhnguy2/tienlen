package vitran.tienlen.game.models;

public class TienLenPlayer extends Player {

  public boolean isInLoop;

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
