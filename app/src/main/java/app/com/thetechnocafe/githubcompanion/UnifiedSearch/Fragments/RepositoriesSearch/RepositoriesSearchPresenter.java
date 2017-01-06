package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch;

import app.com.thetechnocafe.githubcompanion.Networking.NetworkManager;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class RepositoriesSearchPresenter implements RepositoriesSearchContract.Presenter {

    private static final String TAG = RepositoriesSearchPresenter.class.getSimpleName();
    private RepositoriesSearchContract.View mMainView;
    private Subscription mRepositoriesSubscription;

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
    public void attachView(RepositoriesSearchContract.View view) {
        mMainView = view;
        mMainView.initViews();
    }

    @Override
    public void loadRepositories(String searchKeyword) {
        mRepositoriesSubscription = NetworkManager.getInstance()
                .getSearchedRepositories(searchKeyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        repositoriesResult -> {
                            mMainView.showRepositories(repositoriesResult.getItems());
                        },
                        error -> {
                            error.printStackTrace();
                        }
                );
    }
}
