package app.com.thetechnocafe.githubcompanion.Networking;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class GitHubRetrofitClient {
    private static final String TAG = GitHubRetrofitClient.class.getSimpleName();
    private static final String BASE_URL = "https://api.github.com";
    private static Retrofit mRetrofit = null;

    //Singleton class
    private GitHubRetrofitClient() {

    }

    /**
     * @return Retrofit client with github base url
     */
    public static Retrofit getRetrofitClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
