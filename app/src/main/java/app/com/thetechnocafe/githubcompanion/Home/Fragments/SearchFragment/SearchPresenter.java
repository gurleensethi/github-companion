package app.com.thetechnocafe.githubcompanion.Home.Fragments.SearchFragment;

import app.com.thetechnocafe.githubcompanion.R;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mMainView;

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void attachView(SearchContract.View view) {
        mMainView = view;
        mMainView.initViews();
    }

    @Override
    public void onSearch(String string) {
        //Check if search string is empty or not
        if (string.trim().equals("")) {
            mMainView.showError(R.string.keyword_empty);
        } else {
            mMainView.startSearchResultActivity(string.trim());
        }
    }
}
