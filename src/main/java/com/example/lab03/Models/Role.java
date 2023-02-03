package com.example.lab03.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)//przechowywane w postaci łańcucha znaków
    private Types type;
    @ManyToMany(mappedBy = "roles")//właściciel relacji to roles
    private Set<User> users;

    public Role(Types type){
        this.type = type;
    }

    public Role() {

    }
    public static enum Types{
        ROLE_ADMIN,
        ROLE_USER
    }
}
