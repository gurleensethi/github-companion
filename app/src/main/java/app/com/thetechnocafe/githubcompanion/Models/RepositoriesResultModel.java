package app.com.thetechnocafe.githubcompanion.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class RepositoriesResultModel {
    @SerializedName("total_count")
    @Expose
    private String totalCount;

    @SerializedName("incomplete_results")
    @Expose
    private boolean incompleteResults;

    @SerializedName("items")
    @Expose
    private List<RepositoriesModel> items;

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<RepositoriesModel> getItems() {
        return items;
    }

    public void setItems(List<RepositoriesModel> items) {
        this.items = items;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
