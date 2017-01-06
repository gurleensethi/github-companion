package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch;

import app.com.thetechnocafe.githubcompanion.BaseMVP;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class RepositoriesSearchContract {
    public interface View extends BaseMVP.View {

    }

    public interface Presenter extends BaseMVP.Presenter<RepositoriesSearchContract.View> {
        void loadRepositories(String searchKeyword);
    }
}
