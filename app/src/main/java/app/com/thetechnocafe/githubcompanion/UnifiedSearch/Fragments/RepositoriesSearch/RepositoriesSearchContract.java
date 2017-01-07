package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.RepositoriesSearch;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.BaseMVP;
import app.com.thetechnocafe.githubcompanion.Models.RepositoriesModel;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class RepositoriesSearchContract {
    public interface View extends BaseMVP.View {
        void showRepositories(List<RepositoriesModel> list);

        void showErrorLayout();

        void showProgressLayout();
    }

    public interface Presenter extends BaseMVP.Presenter<RepositoriesSearchContract.View> {
        void loadRepositories(String searchKeyword);
    }
}
