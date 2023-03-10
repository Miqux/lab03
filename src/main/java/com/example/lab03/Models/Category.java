package com.example.lab03.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "Category")
public class Category {
    @Column(name = "name", nullable = false)
    private String name;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
}
