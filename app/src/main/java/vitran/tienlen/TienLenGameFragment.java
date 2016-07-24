package vitran.tienlen;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vitran.tienlen.game.engine.TienLenGameEngine;
import vitran.tienlen.game.exception.IllegalPlayTypeException;
import vitran.tienlen.game.exception.PlayerAlreadyExistsException;
import vitran.tienlen.game.models.Card;
import vitran.tienlen.game.models.TienLenPlayHand;
import vitran.tienlen.game.models.TienLenPlayer;
import vitran.tienlen.ui.CardDrawableResolver;
import vitran.tienlen.ui.HorizontalHand;
import vitran.tienlen.ui.VerticalHand;

public class TienLenGameFragment extends BaseFragment {

  private static final String TAG = TienLenGameFragment.class.getSimpleName();

  private float toggleTranslationInPx;
  private int cardWidth;

  // subviews
  private final List<ImageView> cards = new ArrayList<>();
  private final List<ImageView> tableCardViews = new ArrayList<>();
  private VerticalHand player2HandView;
  private HorizontalHand player3HandView;
  private VerticalHand player4HandView;

  private final TienLenGameEngine gameEngine = new TienLenGameEngine();
  private final Handler handler = new Handler();

  // TODO(viet): dynamically populate this
  private final TienLenPlayer mePlayer = new TienLenPlayer(0, "Viet");

  private Button playButton;
  private Button passButton;

  public TienLenGameFragment() {}

  public TienLenGameFragment(@NonNull BaseFragmentHost host) {
    super(host);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO(viet): handle the save of fragment being re-created

    toggleTranslationInPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        15,
        getResources().getDisplayMetrics()
    );

    Point screenDim = new Point();
    getActivity().getWindowManager().getDefaultDisplay().getSize(screenDim);
    cardWidth = (int) (screenDim.x - toggleTranslationInPx - toggleTranslationInPx) / 13;

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

    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_1));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_2));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_3));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_4));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_5));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_6));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_7));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_8));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_9));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_10));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_11));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_12));
    tableCardViews.add((ImageView) v.findViewById(R.id.tien_len_table_card_13));

    playButton = (Button) v.findViewById(R.id.tien_len_play);
    passButton = (Button) v.findViewById(R.id.tien_len_pass);

    player2HandView = (VerticalHand) v.findViewById(R.id.player2_hand);
    player3HandView = (HorizontalHand) v.findViewById(R.id.player3_hand);
    player4HandView = (VerticalHand) v.findViewById(R.id.player4_hand);

    return v;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (savedInstanceState == null) {
      // TODO(viet): wait for the player to explicitly triggers the deal functionality
      // start the game
      gameEngine.reset(dealCallback());

      setupTableListeners();
    }
  }

  @NonNull
  private TienLenGameEngine.TienLenDealCallback dealCallback() {
    return new TienLenGameEngine.TienLenDealCallback() {
        @Override
        public void deal(@NonNull TienLenPlayer player, @NonNull Card card) {
          if (player.getId() == mePlayer.getId()) {
            ImageView viewToSwap = cards.get(player.getHand().size() - 1);
            updateCardImage(viewToSwap, card);
          }

          if (player.getId() == 1) {
            player2HandView.setNumberOfCards(player.getHand().size());
          }

          if (player.getId() == 2) {
            player3HandView.setNumberOfCards(player.getHand().size());
          }

          if (player.getId() == 3) {
            player4HandView.setNumberOfCards(player.getHand().size());
          }
        }

      @Override
      public void onCompleted() {
        sortHand();
      }

      @Override
      public void onDealFailed() {
        Toast.makeText(getContext(), "deal failed", Toast.LENGTH_SHORT).show();
      }
    };
  }

  private void updateCardImage(@NonNull final ImageView viewToSwap, @NonNull Card card) {
    // resize height of card
    if (viewToSwap.getTag() == null) {
      ViewGroup.LayoutParams lp = viewToSwap.getLayoutParams();
      lp.width = cardWidth;
      lp.height = (int) (1.452 * cardWidth);
      viewToSwap.setLayoutParams(lp);
    }

    viewToSwap.setVisibility(View.VISIBLE);
    viewToSwap.setTranslationY(0f);
    viewToSwap.setImageResource(CardDrawableResolver.resolve(card));
    viewToSwap.setTag(card);
  }

  private void sortHand() {
    mePlayer.sortHand();
    int currLocation = 0;
    for (ImageView viewToSwap : cards) {
      if (mePlayer.getHand().size() <= currLocation) {
        viewToSwap.setImageResource(0);
        viewToSwap.setVisibility(View.GONE);
      } else {
        updateCardImage(viewToSwap, mePlayer.getHand().get(currLocation++));
      }
    }
  }

  private void setupTableListeners() {
    for (ImageView cardView : cards) {
      cardView.setOnClickListener(buildCardClickListener(cardView));
    }

    playButton.setOnClickListener(buildPlayClickListener());
  }

  @NonNull
  private View.OnClickListener buildCardClickListener(@NonNull final ImageView imageView) {
    return new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleCard(imageView);
      }
    };
  }

  @NonNull
  private View.OnClickListener buildPlayClickListener() {
    return new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final TienLenPlayHand cardsToPlay = getCardSelection();
        gameEngine.play(mePlayer, cardsToPlay, new TienLenGameEngine.TienLenPlayCallback() {
          @Override
          public boolean isTrump(
              int numConsecutiveTrumps,
              @NonNull TienLenPlayer loser,
              @NonNull TienLenPlayer winner) {
            return false;
          }

          @Override
          public void onCompleted() {
            sortHand();
            playButton.setEnabled(false);
            updateTableCards(cardsToPlay.cards);
          }
        });
      }
    };
  }

  private void updateTableCards(@NonNull List<Card> playedCards) {
    int currLocation = 0;
    for (ImageView viewToSwap : tableCardViews) {
      if (playedCards.size() <= currLocation) {
        viewToSwap.setImageResource(0);
        viewToSwap.setVisibility(View.GONE);
      } else {
        updateCardImage(viewToSwap, playedCards.get(currLocation++));
      }
    }
  }

  private void toggleCard(@NonNull ImageView imageView) {
    // TODO(viet): refactor this
    boolean isSelected = imageView.getTranslationY() != 0;
    imageView.setTranslationY(isSelected ? 0 : -toggleTranslationInPx);

    boolean playable = gameEngine.isSelectionPlayable(getCardSelection());
    playButton.setEnabled(playable);
  }

  @Nullable
  private TienLenPlayHand getCardSelection() {
    List<Card> selectedCards = new ArrayList<>();
    for (ImageView cardView : cards) {
      boolean isSelected = cardView.getTranslationY() != 0;
      if (isSelected) {
        selectedCards.add((Card) cardView.getTag());
      }
    }

    try {
      return new TienLenPlayHand(selectedCards);
    } catch (IllegalPlayTypeException e) {
      // the user has selected an illegal hand
    }
    return null;
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
