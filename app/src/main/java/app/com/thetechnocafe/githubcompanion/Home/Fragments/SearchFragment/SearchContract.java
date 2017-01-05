package app.com.thetechnocafe.githubcompanion.Home.Fragments.SearchFragment;

import app.com.thetechnocafe.githubcompanion.BaseMVP;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class SearchContract {
    public interface View extends BaseMVP.View {
        void startSearchResultActivity(String searchKeyword);

        void showError(int messageID);
    }

    public interface Presenter extends BaseMVP.Presenter<SearchContract.View> {
        void onSearch(String string);
    }
}
