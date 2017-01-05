package app.com.thetechnocafe.githubcompanion.Home;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mMainView;

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void attachView(HomeContract.View view) {
        mMainView = view;
        mMainView.initViews();
    }
}
