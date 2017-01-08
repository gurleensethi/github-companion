package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.UsersSearch;

import app.com.thetechnocafe.githubcompanion.Networking.NetworkManager;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gurleensethi on 08/01/17.
 */

public class UsersSearchPresenter implements UsersSearchContract.Presenter {

    private static final String TAG = UsersSearchPresenter.class.getSimpleName();
    private UsersSearchContract.View mMainView;
    private Subscription mUsersSubscription;

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mMainView = null;

        //UnSubscribe from subscription to prevent crash
        if (mUsersSubscription != null) {
            mUsersSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(UsersSearchContract.View view) {
        mMainView = view;
        mMainView.initViews();
    }

    @Override
    public void loadUsers(String searchKeyword) {
        //Show the loading layout
        mMainView.showProgressLayout();

        mUsersSubscription = NetworkManager.getInstance()
                .getSearchedUsers(searchKeyword)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        usersSearchResult -> {
                            mMainView.showUsers(usersSearchResult.getItems());
                        },
                        error -> {
                            mMainView.showErrorLayout();
                            error.printStackTrace();
                        }
                );
    }
}
