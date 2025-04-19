package com.example.androidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.R;
import com.example.androidapplication.entites.Post;

import java.util.List;

public class SimplePostsListAdapter extends RecyclerView.Adapter<SimplePostsListAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final ImageView ivAuthorImage;
        private final TextView tvDate;
        private final TextView tvContent;
        private final ImageView ivPic;

        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            ivAuthorImage = itemView.findViewById(R.id.ivAuthorImage);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivPic = itemView.findViewById(R.id.ivPic);
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> posts;

    public SimplePostsListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.simple_post_layoyt, parent, false);
        return new PostViewHolder(itemView);
    }

    // Populate UI elements at simple_post_layout.xml
    @Override
    public void onBindViewHolder(SimplePostsListAdapter.PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getAuthor().getNickname());
            holder.tvDate.setText(current.getDate());
            holder.tvContent.setText(current.getContent());
            if (current.getImage() != null) {
                holder.ivPic.setVisibility(View.VISIBLE);
                holder.ivPic.setImageBitmap(ImageUtils.decodeBase64ToBitmap(current.getImage()));
            } else {
                holder.ivPic.setVisibility(View.INVISIBLE);
            }
            holder.ivAuthorImage.setImageBitmap(ImageUtils.decodeBase64ToBitmap(current.getAuthor().getImage()));
        }
    }

    public void setPosts(List<Post> s) {
        posts = s;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (posts != null)
            return posts.size();
        else return 0;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
