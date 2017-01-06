package app.com.thetechnocafe.githubcompanion.Utilities;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class LanguageColorUtility {
    private static LanguageColorUtility mInstance = null;
    private JSONObject colorJSONObject = null;

    //Singleton class
    private LanguageColorUtility(Context context) {
        setUpJSONObject(context);
    }

    public static LanguageColorUtility getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LanguageColorUtility(context);
        }
        return mInstance;
    }

    //Returns color code corresponding to a particular language
    public String getColorCode(String language) {
        try {
            return colorJSONObject.getString(language);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    //Create the JSON color object from the color file
    private void setUpJSONObject(Context context) {
        try {
            //Get asset manager
            AssetManager assetManager = context.getAssets();

            //Get the file containing colors
            InputStream inputStream = assetManager.open("git_language_colors.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();

            String line;

            //Read the file and append to string builder
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            //Initialize the json object
            colorJSONObject = new JSONObject(stringBuilder.toString());

            //Close the streams
            inputStream.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
