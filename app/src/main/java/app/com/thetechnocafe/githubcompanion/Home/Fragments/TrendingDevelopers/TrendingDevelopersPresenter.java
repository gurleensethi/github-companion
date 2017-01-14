package app.com.thetechnocafe.githubcompanion.Home.Fragments.TrendingDevelopers;

import app.com.thetechnocafe.githubcompanion.Networking.NetworkManager;
import rx.Subscription;

/**
 * Created by gurleensethi on 10/01/17.
 */

public class TrendingDevelopersPresenter implements TrendingDevelopersContract.Presenter {

    private TrendingDevelopersContract.View mMainView;
    private Subscription mUsersSubscription;

    @Override
    public void loadUsers(String searchKeyword) {
        //Show the loading layout
        mMainView.showProgressLayout();

        mUsersSubscription = NetworkManager.getInstance()
                .getTrendingDevelopers(searchKeyword)
                .subscribe(
                        usersResult -> {
                            mMainView.showDevelopers(usersResult);
                        },
                        error -> {
                            mMainView.showErrorLayout();
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mMainView = null;

        if (mUsersSubscription != null) {
            mUsersSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(TrendingDevelopersContract.View view) {
        mMainView = view;
        mMainView.initViews();
    }
}
