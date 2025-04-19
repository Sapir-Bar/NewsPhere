package com.example.androidapplication.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidapplication.MyApplication;
import com.example.androidapplication.entites.Comment;

import java.util.LinkedList;
import java.util.List;

public class CommentsRepository {

    CommentDao dao;
    CommentListData commentListData;

    public CommentsRepository() {
        AppDB db = AppDB.getInstance(MyApplication.context);
        dao = db.commentDao();
        commentListData = new CommentListData();
    }

    class CommentListData extends MutableLiveData<List<Comment>> {
        public CommentListData() {
            super();
            //setValue must be called on the main (UI) thread.
            setValue(new LinkedList<Comment>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                //postValue can be called from any thread, not just the main thread.
                commentListData.postValue(dao.index());
            }).start();
        }
    }

    public LiveData<List<Comment>> getAll() { return commentListData; }
    public List<Comment> get(int postId) { return dao.get(postId); }
    public void add(Comment comment){ dao.insert(comment); }
    public void delete(Comment comment){ dao.delete(comment); }
    public void update(Comment comment){ dao.update(comment); }
}
