package com.example.lab03.Interface;

import com.example.lab03.Models.Comment;

import java.util.ArrayList;

public interface CommentService {
    void addComment(Comment comment, int idPost);
    ArrayList<Comment> getAllCommentByPostId(int idPost);
}
