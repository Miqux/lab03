package com.example.lab03.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name = "contents")
    private String contents;
    @Column(name = "added_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate addedDate;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;
}
