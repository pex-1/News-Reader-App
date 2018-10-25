package danijelpecek.news2018.nth.ch.newsreader.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import danijelpecek.news2018.nth.ch.newsreader.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(r, 2000);
    }

    //display main screen of news app after 2 seconds
    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashScreen.this, ActivityArticleList.class);
            startActivity(intent);
            finish();
        }
    };
}
