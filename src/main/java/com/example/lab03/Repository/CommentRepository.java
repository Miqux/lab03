package com.example.lab03.Repository;

import com.example.lab03.Models.Category;
import com.example.lab03.Models.Comment;
import com.example.lab03.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    ArrayList<Comment> findAllByPost(Post post);
}
