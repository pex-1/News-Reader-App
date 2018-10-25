package danijelpecek.news2018.nth.ch.newsreader.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The ArticleList class contains list of all articles and it has the overall structure of our json file
 */
public class ArticleList implements Parcelable {
    private String status;
    private int totalResults;
    @SerializedName("articles")
    private List<Article> articleList;

    public ArticleList(String status, int totalResults, List<Article> articleList) {
        this.status = status;
        this.totalResults = totalResults;
        this.articleList = articleList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeInt(this.totalResults);
        dest.writeTypedList(this.articleList);
    }

    protected ArticleList(Parcel in) {
        this.status = in.readString();
        this.totalResults = in.readInt();
        this.articleList = in.createTypedArrayList(Article.CREATOR);
    }

    public static final Parcelable.Creator<ArticleList> CREATOR = new Parcelable.Creator<ArticleList>() {
        @Override
        public ArticleList createFromParcel(Parcel source) {
            return new ArticleList(source);
        }

        @Override
        public ArticleList[] newArray(int size) {
            return new ArticleList[size];
        }
    };
}
