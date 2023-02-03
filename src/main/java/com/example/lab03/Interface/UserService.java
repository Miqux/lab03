package com.example.lab03.Interface;

import com.example.lab03.Models.Post;
import com.example.lab03.Models.SearchObject;
import com.example.lab03.Models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

public interface UserService extends UserDetailsService {
    UserDetails GetCurrentUser();
    boolean CreateUser(User user);
    byte [] GetUserAvatarByUsername(String id);
    ArrayList<User> findAll();
    void DeleteUserById(int id);
}
