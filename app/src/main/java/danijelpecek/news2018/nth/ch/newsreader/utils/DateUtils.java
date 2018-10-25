package danijelpecek.news2018.nth.ch.newsreader.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import danijelpecek.news2018.nth.ch.newsreader.R;


public class DateUtils {

    /**
     * Returns a String, date when article was published formatted so it would display a month and a year
     *
     * @param publishedAt date when article was published
     * @return the date of a specific article
     */
    public static String getStringDate(String publishedAt) {
        Date date = getDate(publishedAt);
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, yyyy");
            return dateFormat.format(date);
        }

        return publishedAt;
    }

    /**
     * Returns a Date object, converts our article published at date String to a Date
     *
     * @param publishedAt date when article was published
     * @return a Date object
     */
    public static Date getDate(String publishedAt) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            return formatter.parse(publishedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns time passed in days, hours and minutes from now to the date when this article was published
     *
     * @param publishedAt date when article was published
     * @return a String time passed
     */
    public static String getTimePassed(String publishedAt, Context context) {
        //get date when our article was published
        Date articleDate = getDate(publishedAt);
        //get current date
        Date current = new Date();

        //calculate number of milliseconds and convert it to minutes
        long milliseconds = current.getTime() - articleDate.getTime();
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;

        //calculate number of days, hours and minutes
        long day = minutes / 1440;
        long hour = (minutes % 1440) / 60;
        long minute = (minutes % 1440) % 60;
        String hours_label = context.getResources().getString(R.string.hours);

        return day + "d " + hour + hours_label + " " + minute + "m";
    }

}
