package app.com.thetechnocafe.githubcompanion.Home.Fragments.TrendingDevelopers;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.BaseMVP;
import app.com.thetechnocafe.githubcompanion.Models.UsersSearchModel;



public class TrendingDevelopersContract {
    public interface View extends BaseMVP.View {
        void showDevelopers(List<UsersSearchModel> list);

        void showErrorLayout();

        void showProgressLayout();
    }

    public interface Presenter extends BaseMVP.Presenter<TrendingDevelopersContract.View> {
        void loadUsers(String searchKeyword);
    }
}
