package app.com.thetechnocafe.githubcompanion.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class GitHubRetrofitClient {
    private static final String BASE_URL = "https://api.github.com";
    private static Retrofit mRetrofit = null;

    //Singleton class
    private GitHubRetrofitClient() {

    }

    /**
     * @return Retrofit client with github base url
     */
    public static Retrofit getRetrofitClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
