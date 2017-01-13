package app.com.thetechnocafe.githubcompanion.Home.Fragments.TrendingRepositories;

import app.com.thetechnocafe.githubcompanion.Networking.NetworkManager;
import rx.Subscription;

/**
 * Created by gurleensethi on 08/01/17.
 */

public class TrendingRepositoriesPresenter implements TrendingRepositoriesContract.Presenter {

    private static final String TAG = TrendingRepositoriesPresenter.class.getSimpleName();
    private TrendingRepositoriesContract.View mMainView;
    private Subscription mRepositoriesSubscription;

    @Override
    public void loadRepositories(String searchKeyword) {
        //Show the loading layout
        mMainView.showProgressLayout();

        mRepositoriesSubscription = NetworkManager.getInstance()
                .getTrendingRepositories(searchKeyword)
                .subscribe(
                        repositoriesResult -> {
                            mMainView.showRepositories(repositoriesResult);
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

        //UnSubscribe from subscription to prevent crash
        if (mRepositoriesSubscription != null) {
            mRepositoriesSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(TrendingRepositoriesContract.View view) {
        mMainView = view;
        mMainView.initViews();
    }
}
