package app.com.thetechnocafe.githubcompanion.UnifiedSearch.Fragments.UsersSearch;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.BaseMVP;
import app.com.thetechnocafe.githubcompanion.Models.UsersSearchModel;

/**
 * Created by gurleensethi on 08/01/17.
 */

public class UsersSearchContract {
    public interface View extends BaseMVP.View {
        void showUsers(List<UsersSearchModel> list);

        void showErrorLayout();

        void showProgressLayout();
    }

    public interface Presenter extends BaseMVP.Presenter<UsersSearchContract.View> {
        void loadUsers(String searchKeyword);
    }
}
