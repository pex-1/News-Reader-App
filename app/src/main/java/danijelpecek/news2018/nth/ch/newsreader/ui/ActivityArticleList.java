package danijelpecek.news2018.nth.ch.newsreader.ui;

import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import danijelpecek.news2018.nth.ch.newsreader.BuildConfig;
import danijelpecek.news2018.nth.ch.newsreader.JsonApi;
import danijelpecek.news2018.nth.ch.newsreader.R;
import danijelpecek.news2018.nth.ch.newsreader.adapter.ArticleViewAdapter;
import danijelpecek.news2018.nth.ch.newsreader.model.Article;
import danijelpecek.news2018.nth.ch.newsreader.model.ArticleList;
import danijelpecek.news2018.nth.ch.newsreader.utils.NetworkUtils;
import danijelpecek.news2018.nth.ch.newsreader.utils.RetrofitUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityArticleList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ArticleViewAdapter adapter;
    private int sort;
    private Parcelable recyclerState;
    final Handler handler = new Handler();
    private List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        //restore state of recycler(scroll) and sort on rotation
        if (savedInstanceState != null) {
            sort = savedInstanceState.getInt("sort");
            recyclerState = savedInstanceState.getParcelable("recyclerState");
        } else sort = 1;

        //set up app bar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        getArticles(sort);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //store state of our recycler (scrolling) and sort
        outState.putInt("sort", sort);
        outState.putParcelable("recyclerState", Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popularity_sort:
                getArticles(2);
                sort = 2;
                recyclerState = null;
                break;
            case R.id.date_sort:
                getArticles(1);
                sort = 1;
                recyclerState = null;
                break;
        }
        return true;
    }

    /**
     * retrieve news using Retrofit and set up recycler view
     * @param select number 1 - display articles sorted by date, number 2 - display articles sorted by popularity
     */
    public void getArticles(int select) {
        //cancel if there's no internet connection
        if (!NetworkUtils.isNetworkConnected(getApplicationContext())) {
            Toast.makeText(this, "No network connection!", Toast.LENGTH_SHORT).show();
            return;
        }
        JsonApi jsonApi = RetrofitUtils.getClient().create(JsonApi.class);


        Call<ArticleList> call;
        if (select == 1) {
            call = jsonApi.getArticles(BuildConfig.ApiKey);
        } else {
            call = jsonApi.getHeadlines(BuildConfig.ApiKey);
        }


        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(Call<ArticleList> call, Response<ArticleList> response) {
                //not successful, 404
                if (!response.isSuccessful()) {
                    Toast.makeText(ActivityArticleList.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                //set up recycler view
                articles = response.body().getArticleList();
                recyclerView = findViewById(R.id.articles_recycler_view);
                adapter = new ArticleViewAdapter(getApplicationContext(), articles);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                if (recyclerState != null) {
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);
                }
            }

            @Override
            public void onFailure(Call<ArticleList> call, Throwable t) {
                Toast.makeText(ActivityArticleList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        //add search to our app bar
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //cancel delayed filtering if user continues to type
        handler.removeCallbacksAndMessages(null);

        //show results after 500ms of inactivity
        final String passed = s;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                filter(passed);
            }

        }, 500);
        return true;
    }

    /**
     * filters list of articles according to users input
     *
     * @param passed text user typed in search
     */
    private void filter(String passed) {
        List<Article> filteredList = new ArrayList<>();

        for (Article item : articles) {
            if (item.getTitle().toLowerCase().contains(passed.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }
}
