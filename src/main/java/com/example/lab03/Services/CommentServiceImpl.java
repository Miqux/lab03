package com.example.lab03.Services;

import com.example.lab03.Interface.CommentService;
import com.example.lab03.Models.Comment;
import com.example.lab03.Models.Post;
import com.example.lab03.Models.User;
import com.example.lab03.Repository.CommentRepository;
import com.example.lab03.Repository.PostRepository;
import com.example.lab03.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CommentServiceImpl implements CommentService {
@Autowired
private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
@Autowired
private PostRepository postRepository;
    @Override
    public void addComment(Comment comment, int idPost) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User temp = userRepository.findByUsername(user.getUsername());
        comment.setPost(postRepository.getById(idPost));
        comment.setAddedDate(LocalDate.now());
        comment.setUser(temp);
        commentRepository.save(comment);
    }

    @Override
    public ArrayList<Comment> getAllCommentByPostId(int idPost) {
        Post post = postRepository.getById(idPost);
        return commentRepository.findAllByPost(post);
    }
}
