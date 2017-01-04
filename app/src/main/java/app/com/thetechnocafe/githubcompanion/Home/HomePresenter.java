package app.com.thetechnocafe.githubcompanion.Home;

import app.com.thetechnocafe.githubcompanion.R;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mMainView;

    @Override
    public void onSearch(String string) {
        //Check if search string is empty or not
        if (string.trim().equals("")) {
            mMainView.showError(R.string.keyword_empty);
        } else {
            mMainView.startSearchResultActivity(string.trim());
        }
    }

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
    }
}
