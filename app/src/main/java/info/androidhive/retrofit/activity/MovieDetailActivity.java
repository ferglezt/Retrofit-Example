package info.androidhive.retrofit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.Movie;

/**
 * Created by fernando on 28/06/17
 */

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    @Bind(R.id.image_loading_bar) ProgressBar imageLoadingBar;
    @Bind(R.id.no_image_found) TextView noImageFound;

    @Bind(R.id.title) TextView titleTextView;
    @Bind(R.id.poster) ImageView posterImageView;
    @Bind(R.id.original_title) TextView originalTitleTextView;
    @Bind(R.id.release_date) TextView releaseDateTextView;
    @Bind(R.id.overview) TextView overviewTextView;
    @Bind(R.id.vote_average) TextView voteAverageTextView;

    private boolean isOverviewExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Movie m = buildMovieFromIntent(getIntent());
        Log.d(TAG, m.toString());
        setupMovieDetails(m);
    }

    protected Movie buildMovieFromIntent(Intent i) {
        String fullOriginalLanguage = new Locale(
                i.getStringExtra("originalLanguage")
        ).getDisplayLanguage();

        return new Movie(
                i.getStringExtra("posterPath"),
                i.getBooleanExtra("adult", false),
                i.getStringExtra("overview"),
                i.getStringExtra("releaseDate"),
                i.getIntegerArrayListExtra("genreIds"),
                i.getIntExtra("id", -1),
                i.getStringExtra("originalTitle"),
                fullOriginalLanguage,
                i.getStringExtra("title"),
                i.getStringExtra("backdropPath"),
                i.getDoubleExtra("popularity", -1),
                i.getIntExtra("voteCount", -1),
                i.getBooleanExtra("video", false),
                i.getDoubleExtra("voteAverage", -1)
        );
    }

    protected void setupMovieDetails(Movie m) {
        setTitle(m.getTitle());

        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w640" + m.getPosterPath())
                .into(posterImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        imageLoadingBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        imageLoadingBar.setVisibility(View.GONE);
                        noImageFound.setVisibility(View.VISIBLE);
                    }
                });

        titleTextView.setText(m.getTitle());
        originalTitleTextView.setText(m.getOriginalTitle());
        releaseDateTextView.setText(m.getReleaseDate());
        voteAverageTextView.setText(String.valueOf(m.getVoteAverage()));

        overviewTextView.setText(m.getOverview());
        overviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOverviewExpanded) {
                    overviewTextView.setMaxLines(20);
                } else {
                    overviewTextView.setMaxLines(3);
                }
                isOverviewExpanded = !isOverviewExpanded;
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
