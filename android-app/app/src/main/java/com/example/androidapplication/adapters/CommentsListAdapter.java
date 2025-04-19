package com.example.androidapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.R;
import com.example.androidapplication.entites.Comment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentViewHolder> {

    public interface OnCommentItemClickListener {
        void onEditClick(int commentID, String content);
        void onDeleteClick(int commentID);
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAuthor;
        private final ImageView ivAuthorImage;
        private final TextView commentText;
        private final EditText editCommentText;
        private final ImageButton btnEdit;
        private final ImageButton btnDelete;

        private CommentViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvCommentAuthorNickname);
            ivAuthorImage = itemView.findViewById(R.id.ivCommentAuthorImage);
            commentText = itemView.findViewById(R.id.commentText);
            editCommentText = itemView.findViewById(R.id.editCommentText);
            btnEdit = itemView.findViewById(R.id.btnCommentEdit);
            btnDelete = itemView.findViewById(R.id.btnCommentDelete);
        }
    }

    private final LayoutInflater mInflater;
    private List<Comment> comments;
    private OnCommentItemClickListener mListener;

    // Constructor
    public CommentsListAdapter(Context context, OnCommentItemClickListener mListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mListener = mListener;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.comment_layout, parent, false);
        return new CommentViewHolder(itemView);
    }

    // Populate UI elements at comment_activity.xml
    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        if (comments != null) {
            final Comment current = comments.get(position);
            holder.tvAuthor.setText(current.getAuthor_nickname());
            holder.commentText.setText(current.getContent());
            Picasso.get()
                    .load(current.getAuthor_image())
                    .resize(25, 25)
                    .centerCrop()
                    .into(holder.ivAuthorImage);
        }

        holder.btnEdit.setOnClickListener(v -> {
            // Toggle visibility of TextView and EditText
            if (holder.commentText.getVisibility() == View.VISIBLE) {
                holder.commentText.setVisibility(View.GONE);
                holder.editCommentText.setVisibility(View.VISIBLE);
                holder.editCommentText.setText(holder.commentText.getText().toString());
                holder.editCommentText.requestFocus();
            } else {
                holder.commentText.setVisibility(View.VISIBLE);
                holder.editCommentText.setVisibility(View.GONE);
            }
        });

        holder.editCommentText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mListener.onEditClick(position, v.getText().toString());
                // Toggle visibility of TextView and EditText
                holder.commentText.setVisibility(View.VISIBLE);
                holder.editCommentText.setVisibility(View.GONE);
                return true;
            }
            return false;
        });

        holder.btnDelete.setOnClickListener(v -> { mListener.onDeleteClick(position);} );
    }

    @Override
    public int getItemCount() {
        if (comments != null)
            return comments.size();
        else return 0;
    }

    public void setComments(List<Comment> comments) {
        if (comments != null) {
            this.comments = comments;
            notifyDataSetChanged();
        }
    }
}


