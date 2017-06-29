package info.androidhive.retrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.Movie;

/**
 * Created by fernando on 28/06/17
 */

public class MovieDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getStringExtra("title"));

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));

        Log.d("Poster Path", getIntent().getStringExtra("posterPath"));
        ImageView poster = (ImageView) findViewById(R.id.poster);
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w640" + getIntent().getStringExtra("posterPath"))
                .into(poster);

        TextView originalTitle = (TextView) findViewById(R.id.original_title);
        originalTitle.setText(getIntent().getStringExtra("originalTitle"));



    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
