package com.example.lab03.Repository;

import com.example.lab03.Models.PostGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostGenreRepository extends JpaRepository<PostGenre, Integer> {
}
