package com.example.lab03.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Post")
public class Post {
    @Column(name = "date_new")
    private Date dateNew;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "author")
    private String author;
    @Column(name = "title")
    @NotEmpty(message = "Pole nie może być puste!")
    @Size(min=3, max=100, message="Długość musi być między  {min} a {max} znaków")
    private String title;
    @Column(name = "contents")
    @NotEmpty(message = "Pole nie może być puste!")
    @Size(min=5, max=3000, message="Długość musi być między {min} a {max} znaków")
    private String contents;
    @Column(name = "added_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate addedDate;
    @Column(name = "count_of_like")
    private int countOfLike;
    @Column(name = "count_of_dislike")
    private int countOfDislike;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<PostGenre> genres;
    public Post(){
        this.genres = new HashSet<>();
    }
}
