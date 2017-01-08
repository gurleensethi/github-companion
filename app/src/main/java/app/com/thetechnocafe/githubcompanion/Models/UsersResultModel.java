package app.com.thetechnocafe.githubcompanion.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gurleensethi on 08/01/17.
 */

public class UsersResultModel implements Parcelable {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<Object> items = null;
    public final static Parcelable.Creator<UsersResultModel> CREATOR = new Creator<UsersResultModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UsersResultModel createFromParcel(Parcel in) {
            UsersResultModel instance = new UsersResultModel();
            instance.totalCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.incompleteResults = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            in.readList(instance.items, (java.lang.Object.class.getClassLoader()));
            return instance;
        }

        public UsersResultModel[] newArray(int size) {
            return (new UsersResultModel[size]);
        }

    };

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(totalCount);
        dest.writeValue(incompleteResults);
        dest.writeList(items);
    }

    public int describeContents() {
        return 0;
    }

}