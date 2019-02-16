package com.rasel.newsviews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rasel.newsviews.R;
import com.rasel.newsviews.model.Articles;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GoogleNewsAdapter extends RecyclerView.Adapter<GoogleNewsAdapter.ViewHolder>{

    private List<Articles> articlesList;

    public GoogleNewsAdapter(List<Articles> articlesList) {
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvNewsTitle.setText(articlesList.get(position).getTitle());
        holder.tvNewsAuthor.setText(articlesList.get(position).getAuthor());
        holder.tvNewsTime.setText(articlesList.get(position).getPublishedAt());

        String profilepicLink = articlesList.get(position).getUrlToImage();
        Picasso.get().load(profilepicLink.trim()).into(holder.imgNewsPicture);

    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNewsTitle, tvNewsAuthor, tvNewsTime;
        private ImageView imgNewsPicture;

        ViewHolder(View itemView) {
            super(itemView);
            imgNewsPicture = itemView.findViewById(R.id.imgNewsPicture);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvNewsAuthor = itemView.findViewById(R.id.tvNewsAuthor);
            tvNewsTime = itemView.findViewById(R.id.tvNewsTime);
        }
    }
}
