package app.com.thetechnocafe.githubcompanion.Home.Fragments.GitCommandFragment;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.BaseMVP;
import app.com.thetechnocafe.githubcompanion.Models.GitCommandModel;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class GitCommandsContract {
    public interface View extends BaseMVP.View {
        void showCommandsList(List<GitCommandModel> list);
    }

    public interface Presenter extends BaseMVP.Presenter<GitCommandsContract.View> {

    }
}
