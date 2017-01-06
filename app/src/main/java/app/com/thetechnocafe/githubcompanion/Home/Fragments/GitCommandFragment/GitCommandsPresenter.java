package app.com.thetechnocafe.githubcompanion.Home.Fragments.GitCommandFragment;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import app.com.thetechnocafe.githubcompanion.Models.GitCommandModel;

/**
 * Created by gurleensethi on 05/01/17.
 */

public class GitCommandsPresenter implements GitCommandsContract.Presenter {

    private static final String TAG = GitCommandsPresenter.class.getSimpleName();
    private GitCommandsContract.View mMainView;

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void attachView(GitCommandsContract.View view) {
        mMainView = view;
        mMainView.initViews();
        loadCommands();
    }

    private void loadCommands() {
        //Get asset manager and get the commands file
        AssetManager assetManager = mMainView.getAppContext().getAssets();

        StringBuilder stringBuilder = new StringBuilder();

        //Create an input stream to open the assets file
        //Create a buffer reader and store to string builder
        try {
            InputStream inputStream = assetManager.open("git_commands.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //Read lines and append to string builder
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            inputStream.close();
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        //Convert to list of GitCommandModel using GSON
        Gson gson = new Gson();
        List<GitCommandModel> commandList = Arrays.asList(gson.fromJson(stringBuilder.toString(), GitCommandModel[].class));

        mMainView.showCommandsList(commandList);
    }
}
