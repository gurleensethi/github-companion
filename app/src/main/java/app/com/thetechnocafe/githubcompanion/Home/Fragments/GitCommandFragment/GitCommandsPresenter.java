package app.com.thetechnocafe.githubcompanion.Home.Fragments.GitCommandFragment;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class GitCommandsPresenter implements GitCommandsContract.Presenter {

    private GitCommandsContract.View mMainView;

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void attachView(GitCommandsContract.View view) {
        mMainView = view;
        mMainView.initViews();
    }
}
