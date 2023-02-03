package com.example.lab03.Repository;

import com.example.lab03.Models.Category;
import com.example.lab03.Models.Post;
import com.example.lab03.Models.PostGenre;
import com.example.lab03.Models.SearchObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    ArrayList<Post> findAllByTitle(String title);

    ArrayList<Post> findAllByCategory(Category temp);
    ArrayList<Post> findAllByTitleAndCategory(String title, Category temp);
}
