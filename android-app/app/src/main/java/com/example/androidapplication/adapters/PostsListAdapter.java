package com.example.androidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.R;
import com.example.androidapplication.entites.Post;

import java.util.List;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.PostViewHolder> {

    public interface OnPostItemClickListener {
        void onEditClick(Post post, int position);
        void onDeleteClick(Post current, int position);
        void onCommentClick(Post current);
        void onProfileClick(Post current);
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final ImageView ivAuthorImage;
        private final TextView tvDate;
        private final TextView tvContent;
        private final ImageView ivPic;
        private final ImageButton btnEdit;
        private final ImageButton btnDelete;
        private ImageButton btnLike;
        private final TextView tvLikes;
        private final ImageButton btnComment;
        private boolean isLiked;
        private final Button btnProfile;

        private PostViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            ivAuthorImage = itemView.findViewById(R.id.ivAuthorImage);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivPic = itemView.findViewById(R.id.ivPic);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnLike = itemView.findViewById(R.id.btnLike);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            btnComment = itemView.findViewById(R.id.btnComment);
            isLiked = false;
            btnProfile = itemView.findViewById(R.id.btnProfile);
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> posts;
    private OnPostItemClickListener mListener;

    // Constructor
    public PostsListAdapter(Context context, OnPostItemClickListener mListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mListener = mListener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.post_layout, parent, false);
        return new PostViewHolder(itemView);
    }

    // Populate UI elements at post_layout.xml
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        if (posts != null) {
            final Post current = posts.get(position);
            holder.tvAuthor.setText(current.getAuthor().getNickname());
            holder.tvDate.setText(current.getDate());
            holder.tvContent.setText(current.getContent());
            holder.tvLikes.setText(R.string.unliked);
            if (current.getImage() != null) {
                holder.ivPic.setVisibility(View.VISIBLE);
                holder.ivPic.setImageBitmap(ImageUtils.decodeBase64ToBitmap(current.getImage()));
                /*
                Picasso.get()
                        .load(Uri.parse(current.getImage()))
                        .resize(300,300)
                        .centerCrop()
                        .into(holder.ivPic);
                 */
            } else {
                holder.ivPic.setVisibility(View.INVISIBLE);
            }
            holder.ivAuthorImage.setImageBitmap(ImageUtils.decodeBase64ToBitmap(current.getAuthor().getImage()));
            /*
            Picasso.get()
                    .load(Uri.parse(current.getAuthor().getImage())).into(holder.ivAuthorImage);
             */
            holder.btnEdit.setOnClickListener(v -> {
                mListener.onEditClick(current, position);
            });

            holder.btnDelete.setOnClickListener(v -> {
                mListener.onDeleteClick(current, position);
                });

            holder.btnProfile.setOnClickListener(v -> {
                mListener.onProfileClick(current);
            });

             holder.btnLike.setOnClickListener(v -> {
                    boolean newLikeState = !holder.isLiked;
                    // Update the image based on the new like state
                    if (newLikeState) {
                        holder.btnLike.setImageResource(R.drawable.ic_likeon);
                        holder.tvLikes.setText(R.string.liked);
                    } else {
                        holder.btnLike.setImageResource(R.drawable.ic_likeoff);
                        holder.tvLikes.setText(R.string.unliked);
                    }
                    holder.isLiked = newLikeState; });

            holder.btnComment.setOnClickListener(v ->
            {mListener.onCommentClick(current);});
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
