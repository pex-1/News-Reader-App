package danijelpecek.news2018.nth.ch.newsreader.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import danijelpecek.news2018.nth.ch.newsreader.R;
import danijelpecek.news2018.nth.ch.newsreader.model.Article;
import danijelpecek.news2018.nth.ch.newsreader.utils.DateUtils;

public class Details extends AppCompatActivity {

    private TextView articleBody;
    private TextView articleTitle;
    private TextView articleDate;
    private TextView articleAuthor;
    private ImageView articleImage;
    private TextView articleLink;

    //back functionality
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        articleBody = findViewById(R.id.details_article_body);
        articleTitle = findViewById(R.id.details_article_title);
        articleDate = findViewById(R.id.details_article_date);
        articleImage = findViewById(R.id.details_article_image);
        articleAuthor = findViewById(R.id.details_article_author);
        articleLink = findViewById(R.id.details_link_text_view);

        //set up app bar
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //back functionality (display back arrow)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        setUpUi();


    }

    /**
     * Set up UI
     */
    private void setUpUi() {
        final Article article = getIntent().getParcelableExtra("article");

        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.placeholder)
                .into(articleImage);

        articleBody.setText(article.getContent());
        articleTitle.setText(article.getTitle());
        articleDate.setText(article.getPublishedAt());
        articleAuthor.append(article.getAuthor());
        articleDate.setText(DateUtils.getStringDate(article.getPublishedAt()));

        //open link in a browser
        articleLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(article.getArticleUrl()));
                startActivity(browserIntent);
            }
        });
    }
}
