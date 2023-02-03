package com.example.lab03.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lab03.Models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Integer> {
     User findByUsername(String Username);
     ArrayList<User> findAll();
     void deleteById(long id);
}
