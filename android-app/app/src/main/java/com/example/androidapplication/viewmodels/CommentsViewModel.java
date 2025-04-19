package com.example.androidapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapplication.entites.Comment;
import com.example.androidapplication.repositories.CommentsRepository;

import java.util.List;

public class  CommentsViewModel extends ViewModel {

    private CommentsRepository repository;
    private LiveData<List<Comment>> comments;

    public CommentsViewModel () {
        repository = new CommentsRepository();
        comments = repository.getAll();
    }

    public LiveData<List<Comment>> getAll() { return comments; }
    public List<Comment> get(int postId) { return repository.get(postId); }
    public void add(Comment comment) { repository.add(comment); }
    public void delete(Comment comment) { repository.delete(comment); }
    public void update(Comment comment) { repository.update(comment); }
}
