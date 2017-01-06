package app.com.thetechnocafe.githubcompanion.Networking;

import app.com.thetechnocafe.githubcompanion.Models.RepositoriesResultModel;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gurleensethi on 06/01/17.
 */

public interface GitHubInterface {
    @GET("search/repositories")
    Observable<RepositoriesResultModel> getSearchedRepositories(
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("q") String searchKey
    );
}
