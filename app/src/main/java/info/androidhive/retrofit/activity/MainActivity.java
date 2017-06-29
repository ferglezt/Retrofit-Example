package info.androidhive.retrofit.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.MoviesAdapter;
import info.androidhive.retrofit.model.Movie;
import info.androidhive.retrofit.model.MoviesResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.movies_recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        loadTopRated();
    }

    protected void loadTopRated() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(ApiClient.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                setContentView(R.layout.activity_main);
                setTitle("Top Rated");
                ButterKnife.bind(MainActivity.this);

                List<Movie> movies = response.body().getResults();
                setupRecyclerAdapter(movies);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                setContentView(R.layout.loading_error);
            }
        });
    }

    protected void setupRecyclerAdapter(List<Movie> movies) {
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, MainActivity.this));
    }

    protected void loadSearch(final String query) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.searchMovie(query, ApiClient.API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                setContentView(R.layout.activity_main);
                ButterKnife.bind(MainActivity.this);

                List<Movie> movies = response.body().getResults();
                setupRecyclerAdapter(movies);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                setContentView(R.layout.loading_error);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)  MenuItemCompat.getActionView(menuItem);;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearchIntent(intent);
    }

    private boolean handleSearchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            setContentView(R.layout.loading);
            String query = intent.getStringExtra(SearchManager.QUERY);
            loadSearch(query);
            return true;
        }
        return false;
    }

}
