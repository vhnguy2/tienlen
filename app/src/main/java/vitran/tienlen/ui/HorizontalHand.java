package vitran.tienlen.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vitran.tienlen.R;

public class HorizontalHand extends LinearLayout {
  private Context mContext;

  public HorizontalHand(Context context) {
    super(context);
    init(context);
  }

  public HorizontalHand(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public HorizontalHand(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(@NonNull Context context) {
    mContext = context;
    setOrientation(HORIZONTAL);
  }

  public void setNumberOfCards(int numCards) {
    removeAllViews();

    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(25, 100);
    for (int i = 0; i < numCards - 1; i++) {
      ImageView card = new ImageView(mContext);
      card.setImageResource(R.drawable.black_joker);
      card.setLayoutParams(lp);
      card.setScaleType(ImageView.ScaleType.CENTER_CROP);
      addView(card);
    }

    if (numCards > 0) {
      // add the final card, which will be the full card
      ImageView card = new ImageView(mContext);
      card.setImageResource(R.drawable.black_joker);
      card.setScaleType(ImageView.ScaleType.CENTER_CROP);
      addView(card, 100, 100);
    }
  }
}
