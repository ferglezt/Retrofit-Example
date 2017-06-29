package info.androidhive.retrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.Movie;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private boolean isOverViewExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra("id", -1);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Movie> call = apiInterface.getMovieDetails(id, ApiClient.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie m = response.body();
                setupMovieDetails(m);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(TAG, t.toString());
                setContentView(R.layout.loading_error);
            }
        });

    }

    protected void setupMovieDetails(Movie m) {
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

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
        overviewTextView.setText(m.getOverview());
        overviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOverViewExpanded) {
                    overviewTextView.setMaxLines(20);
                } else {
                    overviewTextView.setMaxLines(3);
                }
                isOverViewExpanded = !isOverViewExpanded;
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
}
