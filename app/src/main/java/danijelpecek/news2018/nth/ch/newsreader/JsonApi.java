package danijelpecek.news2018.nth.ch.newsreader;

import danijelpecek.news2018.nth.ch.newsreader.model.ArticleList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 */
public interface JsonApi {
    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines?sources=bbc-news")
    Call<ArticleList> getArticles(@Query("apiKey") String apiKey);

    @GET("everything?q=bbc-news&sortBy=popularity")
    Call<ArticleList> getHeadlines(@Query("apiKey") String apiKey);
}
