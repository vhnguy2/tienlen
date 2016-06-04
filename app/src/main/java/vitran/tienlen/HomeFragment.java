package vitran.tienlen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends BaseFragment {

  private Button dealButton;

  public HomeFragment() {}

  public HomeFragment(@NonNull BaseFragmentHost host) {
    super(host);
  }

  @Nullable
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.home_fragment, container, false);
    dealButton = (Button) v.findViewById(R.id.home_deal_button);

    setupListeners();

    return v;
  }

  private void setupListeners() {
    dealButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getFragmentHost().swapFragment(HomeActivity.TIEN_LEN_GAME_FRAGMENT_TAG);
      }
    });
  }

  @NonNull
  public static HomeFragment createInstance(BaseFragmentHost host) {
    return new HomeFragment(host);
  }
}
