package app.com.thetechnocafe.githubcompanion.Networking;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.RepositoriesModel;
import app.com.thetechnocafe.githubcompanion.Models.RepositoriesResultModel;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class NetworkManager {

    private static NetworkManager mInstance = null;

    //Singleton class
    private NetworkManager() {

    }

    //Return instance of network manager
    public static NetworkManager getInstance() {
        if(mInstance == null) {
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
        //Get the retorfit client
        Retrofit mRetrofitClient = GitHubRetrofitClient.getRetrofitClient();
        //Create the gitHub interface from retorfit
        GitHubInterface gitHubInterface = mRetrofitClient.create(GitHubInterface.class);
        //Return the observable
        return gitHubInterface.getSearchedRepositories("desc", "stars", search);
    }
}
