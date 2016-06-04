package vitran.tienlen;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

  private BaseFragmentHost host;

  public BaseFragment() {}

  public BaseFragment(@NonNull BaseFragmentHost host) {
    this.host = host;
  }

  @NonNull
  public BaseFragmentHost getFragmentHost() {
    if (host == null) {
      throw new IllegalStateException("BaseFragmentHost is null while calling getFragmentHost()");
    }
    return host;
  }

  public interface BaseFragmentHost {
    void swapFragment(String fragmentTag);
  }
}
