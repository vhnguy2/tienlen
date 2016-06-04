package vitran.tienlen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vitran.tienlen.game.engine.TienLenGameEngine;
import vitran.tienlen.game.exception.PlayerAlreadyExistsException;
import vitran.tienlen.game.models.Card;
import vitran.tienlen.game.models.TienLenPlayer;
import vitran.tienlen.ui.CardDrawableResolver;

public class TienLenGameFragment extends BaseFragment {

  private static final String TAG = TienLenGameFragment.class.getSimpleName();

  private final List<ImageView> cards = new ArrayList<>();
  private final TienLenGameEngine gameEngine = new TienLenGameEngine();

  // TODO(viet): dynamically populate this
  private final TienLenPlayer mePlayer = new TienLenPlayer(0, "Viet");

  public TienLenGameFragment() {}

  public TienLenGameFragment(@NonNull BaseFragmentHost host) {
    super(host);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO(viet): handle the save of fragment being re-created

    if (savedInstanceState == null) {
      setupGameEngine();
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.tien_len_game_fragment, container, false);

    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_1));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_2));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_3));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_4));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_5));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_6));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_7));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_8));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_9));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_10));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_11));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_12));
    cards.add((ImageView) v.findViewById(R.id.tien_len_player_card_13));

    return v;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (savedInstanceState == null) {
      // TODO(viet): wait for the player to explicitly triggers the deal functionality
      // start the game
      gameEngine.reset(dealCallback());
    }
  }

  @NonNull
  private TienLenGameEngine.TienLenDealCallback dealCallback() {
    return new TienLenGameEngine.TienLenDealCallback() {
        @Override
        public void deal(@NonNull TienLenPlayer player, @NonNull Card card) {
          if (player.getId() == mePlayer.getId()) {
            ImageView viewToSwap = cards.get(player.getHand().size() - 1);
            viewToSwap.setImageResource(CardDrawableResolver.resolve(card));
          }
        }

      @Override
      public void onCompleted() {
        sortHand();
      }
    };
  }

  private void sortHand() {
    mePlayer.sortHand();
    int currLocation = 0;
    for (ImageView viewToSwap : cards) {
      viewToSwap
          .setImageResource(CardDrawableResolver.resolve(mePlayer.getHand().get(currLocation++)));
    }
  }

  private void setupGameEngine() {
    try {
      // add the current player to the game
      gameEngine.addPlayer(0, mePlayer);

      // TODO(viet): remove mock data
      gameEngine.addPlayer(1, new TienLenPlayer(1, "Computer 1"));
      gameEngine.addPlayer(2, new TienLenPlayer(2, "Computer 2"));
      gameEngine.addPlayer(3, new TienLenPlayer(3, "Computer 3"));
    } catch (PlayerAlreadyExistsException e) {
      Log.e(TAG, e.getMessage(), e);
    }
  }

  @NonNull
  public static TienLenGameFragment createInstance(BaseFragment.BaseFragmentHost host) {
    return new TienLenGameFragment(host);
  }
}
