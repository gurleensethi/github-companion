package app.com.thetechnocafe.githubcompanion.Home;

import app.com.thetechnocafe.githubcompanion.BaseMVP;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class HomeContract {
    public interface View extends BaseMVP.View {

    }

    public interface Presenter extends BaseMVP.Presenter<HomeContract.View> {

    }
}
