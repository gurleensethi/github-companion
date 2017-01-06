package app.com.thetechnocafe.githubcompanion.Utilities;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

/**
 * Created by gurleensethi on 06/01/17.
 */

public class GlideUtility {
    //Set the max cache size for Glide
    public static void initGlide(Context context) {
        GlideBuilder glideBuilder = new GlideBuilder(context);
        glideBuilder.setDiskCache(new InternalCacheDiskCacheFactory(context, 4096));
    }
}
