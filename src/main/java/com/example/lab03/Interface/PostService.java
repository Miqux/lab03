package com.example.lab03.Interface;

import com.example.lab03.Models.Post;
import com.example.lab03.Models.SearchObject;

import java.util.ArrayList;

public interface PostService {
    void CreatePost(Post post);
    boolean IsAuthor(Post post);
    ArrayList<Post> FindAllBySearchObject(SearchObject serachObject);
}
