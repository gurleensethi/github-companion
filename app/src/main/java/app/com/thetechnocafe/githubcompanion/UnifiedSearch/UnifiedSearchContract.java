package app.com.thetechnocafe.githubcompanion.UnifiedSearch;

import app.com.thetechnocafe.githubcompanion.BaseMVP;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class UnifiedSearchContract {
    public interface View extends BaseMVP.View {

    }

    public interface Presenter extends BaseMVP.Presenter<UnifiedSearchContract.View> {

    }
}
