package danijelpecek.news2018.nth.ch.newsreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import danijelpecek.news2018.nth.ch.newsreader.ui.Details;
import danijelpecek.news2018.nth.ch.newsreader.R;
import danijelpecek.news2018.nth.ch.newsreader.model.Article;
import danijelpecek.news2018.nth.ch.newsreader.utils.DateUtils;

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewAdapter.ArticleViewHolder> {

    private Context context;
    private List<Article> articles;

    public ArticleViewAdapter(Context context, List<Article> articles) {
        this.articles = articles;
        this.context = context;
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView articleImage;
        TextView articleTitle;
        TextView articleDate;
        CardView cardView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.article_image_card_view);
            articleTitle = itemView.findViewById(R.id.text_view_title_card_view);
            articleDate = itemView.findViewById(R.id.text_view_date_card_view);
            cardView = itemView.findViewById(R.id.article_card_view);
        }
    }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.article_item, viewGroup, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleViewHolder articleViewHolder, final int position) {
        Picasso.get()
                .load(articles.get(position).getUrlToImage())
                .placeholder(R.drawable.placeholder)
                .into(articleViewHolder.articleImage);
        articleViewHolder.articleTitle.setText(articles.get(position).getTitle());

        //calculate time passed
        String timePassed = DateUtils.getTimePassed(articles.get(position).getPublishedAt(), context);
        articleViewHolder.articleDate.setText(timePassed);

        articleViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("article", articles.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    /**
     * filter movies when user uses search
     *
     * @param filteredList - a list of filtered movies
     */
    public void filterList(List<Article> filteredList) {
        articles = filteredList;
        notifyDataSetChanged();
    }

}
