package app.com.thetechnocafe.githubcompanion.Networking.Crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.UsersSearchModel;
import app.com.thetechnocafe.githubcompanion.Utilities.Constants;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GitHubTrendingDevelopersCrawler {
    private static GitHubTrendingDevelopersCrawler mInstance;

    //Singleton class
    private GitHubTrendingDevelopersCrawler() {

    }

    public static GitHubTrendingDevelopersCrawler getInstance() {
        if (mInstance == null) {
            mInstance = new GitHubTrendingDevelopersCrawler();
        }
        return mInstance;
    }

    /**
     * Return an observable that provides a list of trending users
     *
     * @param time Time to get the repos by, today/week/month/year
     * @return Observable
     */
    public Observable<List<UsersSearchModel>> getTrendingUsersObservable(String time) {
        //Create a observable
        Observable<List<UsersSearchModel>> observable = Observable.create(subscriber -> {
            try {
                //Get list of all repositories
                List<UsersSearchModel> trendingUsers = crawlAndGetUserData(Constants.GITHUB_TRENDING_DEVELOPERS_URL + time);

                subscriber.onNext(trendingUsers);
                subscriber.onCompleted();
            } catch (Exception e) {
                //Notify subscriber for error
                subscriber.onError(e);
            }
        });

        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    /**
     * Crawl the GitHub trending page, get all the repos and create Repository Model,
     * return the list of repositories model
     *
     * @param trendingURL URL of the trending page
     * @return List list of repositories model crawled
     */
    public List<UsersSearchModel> crawlAndGetUserData(String trendingURL) {
        List<UsersSearchModel> usersList = new ArrayList<>();

        try {
            //Get the html webpage using Jsoup
            Document document = Jsoup.connect(trendingURL).get();

            //Get the <ol> of trending users having class="user-leaderboard-list"
            Element users = document.getElementsByClass("user-leaderboard-list").get(0);

            //Iterate over the list and fetch required data
            for (Element user : users.children()) {
                String avatar = getUserAvatar(user);
                String username = getUserName(user);

                //Create new user search model and add to list
                UsersSearchModel model = new UsersSearchModel();
                model.setAvatarUrl(avatar);
                model.setLogin(username);
                model.setUrl(Constants.GITHUB_USER_INCOMPLETE + username.split(" ")[0]);

                usersList.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersList;
    }

    //Get the user avatar with class="leaderboard-gravatar"
    private String getUserAvatar(Element element) {
        return element.getElementsByClass("leaderboard-gravatar").attr("src");
    }

    //Get the name of the user with class="user-leaderboard-list-name"
    private String getUserName(Element element) {
        return element.getElementsByClass("user-leaderboard-list-name").text();
    }
}
