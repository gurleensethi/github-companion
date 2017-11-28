package app.com.thetechnocafe.githubcompanion.Home.Fragments.TrendingRepositories;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.BaseMVP;
import app.com.thetechnocafe.githubcompanion.Models.RepositoriesModel;


public class TrendingRepositoriesContract {
    public interface View extends BaseMVP.View {
        void showRepositories(List<RepositoriesModel> list);

        void showErrorLayout();

        void showProgressLayout();
    }

    public interface Presenter extends BaseMVP.Presenter<TrendingRepositoriesContract.View> {
        void loadRepositories(String searchKeyword);
    }
}
