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

public class GoogleNewsAdapterReverseOrder extends RecyclerView.Adapter<GoogleNewsAdapterReverseOrder.ViewHolder>{

    private OnItemClickListener listener;
    private List<Articles> articlesList;

    public GoogleNewsAdapterReverseOrder(List<Articles> articlesList) {
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

        int showItem = articlesList.size() -(position+1);

        holder.tvNewsTitle.setText(articlesList.get(showItem).getTitle());
        holder.tvNewsAuthor.setText(articlesList.get(showItem).getAuthor());
        holder.tvNewsTime.setText(articlesList.get(showItem).getPublishedAt());

        String profilepicLink = articlesList.get(showItem).getUrlToImage();

            Picasso.get().load(profilepicLink)
                    .fit().centerCrop()
                    .into(holder.imgNewsPicture);
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
            tvNewsAuthor = itemView.findViewById(R.id.tvNewsAuthorValue);
            tvNewsTime = itemView.findViewById(R.id.tvNewsTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = articlesList.size() -(getAdapterPosition()+1);
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(articlesList.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Articles articles);
    }

    public void setOnItemClickListener(GoogleNewsAdapterReverseOrder.OnItemClickListener listener) {
        this.listener = listener;
    }
}
