package app.com.thetechnocafe.githubcompanion.Networking;

import app.com.thetechnocafe.githubcompanion.Models.RepositoriesResultModel;
import app.com.thetechnocafe.githubcompanion.Models.UsersResultModel;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class NetworkManager {
    private Retrofit mRetrofit;
    private GitHubInterface mGitHubInterface;
    private static NetworkManager mInstance = null;

    //Singleton class
    private NetworkManager() {
        mRetrofit = GitHubRetrofitClient.getRetrofitClient();
        mGitHubInterface = mRetrofit.create(GitHubInterface.class);
    }

    //Return instance of network manager
    public static NetworkManager getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkManager();
        }
        return mInstance;
    }

    /**
     * Get all the repositories corresponding to a particular search keyword
     * in a observable
     *
     * @param search The search keyword passed by the user
     * @return Observable
     **/
    public Observable<RepositoriesResultModel> getSearchedRepositories(String search) {
        return mGitHubInterface.getSearchedRepositories("desc", "stars", search);
    }

    /**
     * Get all the users correspnding to a particular search keyword
     * in a observable
     *
     * @param search The search keyword passed by the user
     * @return Observable
     */
    public Observable<UsersResultModel> getSearchedUsers(String search) {
        return mGitHubInterface.getSearchedUsers(search);
    }
}
