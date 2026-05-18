package com.example.jumbo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FilmAdapter extends ArrayAdapter<Film> {
    public FilmAdapter(@NonNull Context context, int resource, @NonNull List<Film> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.item_film, parent, false);
        }

        Film film = getItem(position);

        if (film != null) {
            android.widget.TextView title = convertView.findViewById(R.id.tvFilmTitle);
            android.widget.TextView details = convertView.findViewById(R.id.tvFilmDetails);
            android.widget.TextView rating = convertView.findViewById(R.id.tvFilmRating);

            title.setText(film.getTitle());

            String info = film.getGenre() + ", " + film.getYear();
            details.setText(info);

            rating.setText("Rating: " + film.getRating());
        }

        return convertView;
    }
}
