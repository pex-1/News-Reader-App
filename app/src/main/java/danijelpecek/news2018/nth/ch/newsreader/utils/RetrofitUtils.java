package danijelpecek.news2018.nth.ch.newsreader.utils;

import danijelpecek.news2018.nth.ch.newsreader.JsonApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    /**
     * Returns a Retrofit object after building it
     *
     * @return Retrofit object
     */
    public static Retrofit getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(JsonApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
