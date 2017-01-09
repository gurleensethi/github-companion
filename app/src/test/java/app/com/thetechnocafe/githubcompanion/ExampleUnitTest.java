package app.com.thetechnocafe.githubcompanion;

import org.junit.Test;

import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.UsersSearchModel;
import app.com.thetechnocafe.githubcompanion.Networking.Crawlers.GitHubTrendingDevelopersCrawler;
import app.com.thetechnocafe.githubcompanion.Utilities.Constants;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void trendingDevelopersCrawler_isWorking() {
        GitHubTrendingDevelopersCrawler crawler = GitHubTrendingDevelopersCrawler.getInstance();
        List<UsersSearchModel> list = crawler.crawlAndGetUserData(Constants.GITHUB_TRENDING_DEVELOPERS_URL + "daily");

        assertEquals(true, (list.size() > 10));
    }
}