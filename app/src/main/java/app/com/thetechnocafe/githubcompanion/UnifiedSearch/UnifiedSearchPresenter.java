package app.com.thetechnocafe.githubcompanion.UnifiedSearch;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class UnifiedSearchPresenter implements UnifiedSearchContract.Presenter {

    private UnifiedSearchContract.View mMainView;

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void attachView(UnifiedSearchContract.View view) {
        mMainView = view;
        mMainView.initViews();
    }
}
