package app.com.thetechnocafe.githubcompanion.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class GitCommandModel {
    @SerializedName("description")
    @Expose
    private String mDescription;

    @SerializedName("command")
    @Expose
    private String mCommand;

    public String getCommand() {
        return mCommand;
    }

    public void setCommand(String command) {
        mCommand = command;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
