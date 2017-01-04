package app.com.thetechnocafe.githubcompanion;

import android.content.Context;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class BaseMVP {
    public interface View {
        Context getAppContext();
    }

    public interface Presenter<T> {
        void onStart();

        void onDestroy();

        void attachView(T view);
    }
}
