package info.androidhive.retrofit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.MovieDetailActivity;
import info.androidhive.retrofit.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;


        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final Movie m = movies.get(position);

        holder.movieTitle.setText(m.getTitle());
        holder.data.setText(m.getReleaseDate());
        holder.movieDescription.setText(m.getOverview());
        holder.rating.setText(m.getVoteAverage().toString());

        holder.moviesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MovieDetailActivity.class);

                i.putExtra("posterPath", m.getPosterPath());
                i.putExtra("adult", m.isAdult());
                i.putExtra("overview", m.getOverview());
                i.putExtra("releaseDate", m.getReleaseDate());
                i.putIntegerArrayListExtra("genreIds", new ArrayList<>(m.getGenreIds()));
                i.putExtra("id", m.getId());
                i.putExtra("originalTitle", m.getOriginalTitle());
                i.putExtra("originalLanguage", m.getOriginalLanguage());
                i.putExtra("title", m.getTitle());
                i.putExtra("backdropPath", m.getBackdropPath());
                i.putExtra("popularity", m.getPopularity());
                i.putExtra("voteCount", m.getVoteCount());
                i.putExtra("video", m.getVideo());
                i.putExtra("voteAverage", m.getVoteAverage());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}