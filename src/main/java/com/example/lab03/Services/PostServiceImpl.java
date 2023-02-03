package com.example.lab03.Services;

import com.example.lab03.Interface.PostService;
import com.example.lab03.Models.Category;
import com.example.lab03.Models.Post;
import com.example.lab03.Models.SearchObject;
import com.example.lab03.Models.User;
import com.example.lab03.Repository.CategoryRepository;
import com.example.lab03.Repository.PostRepository;
import com.example.lab03.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void CreatePost(Post post) {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User temp = userRepository.findByUsername(user.getUsername());
        post.setAddedDate(LocalDate.now());
        post.setAuthor(temp.getUsername());
        post.setCountOfDislike(0);
        post.setCountOfLike(0);
        postRepository.save(post);
    }

    @Override
    public boolean IsAuthor(Post post) {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getUsername().equals(post.getAuthor()))
            return true;
        return false;
    }

    @Override
    public ArrayList<Post> FindAllBySearchObject(SearchObject serachObject) {
        if(serachObject.getTitle().isEmpty() && serachObject.getCategoryId() == 0)
            return (ArrayList<Post>)postRepository.findAll();
        else if (serachObject.getCategoryId() == 0)
            return postRepository.findAllByTitle(serachObject.getTitle());
        else if (serachObject.getTitle().isEmpty())
            return postRepository.findAllByCategory(categoryRepository.getById(serachObject.getCategoryId()));
        else
            return postRepository.findAllByTitleAndCategory(serachObject.getTitle(), categoryRepository.getById(serachObject.getCategoryId()));
    }
}
