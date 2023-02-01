package com.example.lab03.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="genres")
@Data
@NoArgsConstructor

public class PostGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public PostGenre(String name){
        this.name = name;
    }
}
