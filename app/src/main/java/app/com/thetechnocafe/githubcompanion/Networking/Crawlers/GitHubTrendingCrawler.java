package app.com.thetechnocafe.githubcompanion.Networking.Crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.RepositoriesModel;
import app.com.thetechnocafe.githubcompanion.Utilities.Constants;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GitHubTrendingCrawler {
    private static GitHubTrendingCrawler mInstance;
    private static final String BASE_URL = "https://github.com/trending?since=";

    //Singleton class
    private GitHubTrendingCrawler() {

    }

    //Instance function for singleton class
    public static GitHubTrendingCrawler getInstance() {
        if (mInstance == null) {
            mInstance = new GitHubTrendingCrawler();
        }
        return mInstance;
    }

    /**
     * Return an observable that provides a list of trending repos
     *
     * @param time Time to get the repos by, today/week/month/year
     * @return Observable
     */
    public Observable<List<RepositoriesModel>> getTrendingRepositoriesObservable(String time) {
        //Create a observable
        Observable<List<RepositoriesModel>> observable = Observable.create(subscriber -> {
            try {
                //Get list of all repositories
                List<RepositoriesModel> trendingRepos = crawlAndGetTrendingData(BASE_URL + time);

                subscriber.onNext(trendingRepos);
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
    private List<RepositoriesModel> crawlAndGetTrendingData(String trendingURL) {
        //Create a list of Repositories Model
        List<RepositoriesModel> repoList = new ArrayList<>();

        try {
            //Get the trending page
            Document document = Jsoup.connect(trendingURL).get();

            //Get the <ol> tag with class="repo-list", containing all the repositories
            Element element = document.getElementsByClass("repo-list").get(0);

            //Get list of all the repositories
            Elements repositories = element.children();

            //Loop over the repositories and extract the data
            for (Element repo : repositories) {
                //Get all the properties
                String fullName = getRepoCompleteTitle(repo);
                String name = getRepoTitle(repo);
                String description = getRepoDescription(repo);
                String language = getRepoLanguage(repo);
                int stars = getRepoStars(repo);
                int forks = getRepoForks(repo);

                RepositoriesModel repository = new RepositoriesModel();
                repository.setName(name);
                repository.setFullName(fullName);
                repository.setDescription(description);
                repository.setForks(forks);
                repository.setForksCount(forks);
                repository.setStargazersCount(stars);
                repository.setLanguage(language);
                repository.setUrl(Constants.GITHUB_REPO_INCOMPLETE + fullName);

                repoList.add(repository);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repoList;
    }

    //Return the complete repo title in the <div> tag with class="d-inline-block"
    private String getRepoCompleteTitle(Element element) {
        Element div = element.getElementsByClass("d-inline-block").get(0);
        //Get the text in the <a> tag
        String completeTitle = div.select("a").text();

        //Remove the spaces around the '/'
        //Example: google / grumpy ==> google/grumpy
        completeTitle = completeTitle.split("/")[0].trim() + "/" + completeTitle.split("/")[1].trim();
        return completeTitle.trim();
    }

    //Return onyl the repo title in the <div> tag with class="d-inline-block"
    private String getRepoTitle(Element element) {
        return getRepoCompleteTitle(element).split("/")[1];
    }

    //Return the repo description in the <div> tag with class="py-1"
    private String getRepoDescription(Element element) {
        Element div = element.getElementsByClass("py-1").get(0);
        //Get the text in <p> tag
        String description = div.select("p").text();
        return description.trim();
    }

    //Return the repo language in the <div> tag with class="f6"
    private String getRepoLanguage(Element element) {
        Element div = element.getElementsByClass("f6").get(0);
        //Get the text in <span> that has class="mr-3'
        String language = div.getElementsByAttributeValue("itemprop", "programmingLanguage").text();
        return language.trim();
    }

    //Return the repo stars in the <a> tag with aria-lable="Stargazers"
    private int getRepoStars(Element element) {
        String stars = element.getElementsByAttributeValue("aria-label", "Stargazers").text();
        try {
            return NumberFormat.getInstance().parse(stars).intValue();
        } catch (ParseException e) {
            return 0;
        }
    }

    //Return the repo forks in the <a> tag with aria-label="Forks"
    private int getRepoForks(Element element) {
        String forks = element.getElementsByAttributeValue("aria-label", "Forks").text().trim();
        try {
            return NumberFormat.getInstance().parse(forks).intValue();
        } catch (ParseException e) {
            return 0;
        }
    }
}
