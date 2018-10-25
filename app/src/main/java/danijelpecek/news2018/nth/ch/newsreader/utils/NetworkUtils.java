package danijelpecek.news2018.nth.ch.newsreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtils {
    /**
     * Returns true if there is a network connection
     *
     * @param context application context
     * @return true or false
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
