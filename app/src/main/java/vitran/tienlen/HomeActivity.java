package vitran.tienlen;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class HomeActivity extends FragmentActivity implements BaseFragment.BaseFragmentHost {

  public static final String HOME_FRAGMENT_TAG = "homeFragment";
  public static final String TIEN_LEN_GAME_FRAGMENT_TAG = "tienLenGameFragment";

  private BaseFragment baseFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home_activity);
    swapFragment(HOME_FRAGMENT_TAG);
  }

  @Override
  public void swapFragment(String fragmentTag) {
    baseFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(fragmentTag);
    if (baseFragment == null) {
      switch (fragmentTag) {
        case HOME_FRAGMENT_TAG:
          baseFragment = HomeFragment.createInstance(this);
          break;
        case TIEN_LEN_GAME_FRAGMENT_TAG:
          baseFragment = TienLenGameFragment.createInstance(this);

      }
    }

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.home_container, baseFragment)
        .commit();
  }
}
