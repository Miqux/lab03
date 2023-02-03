package com.example.lab03.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 36)
    private String username;
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$")
    private String password;
    @Transient//właściwość nie będzie odzwierciedlona w db
    private String passwordConfirm;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate addedDate;

    private boolean enabled = false;//czy konto jest aktywne
    @Lob
    private byte[] fileContent;
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(String username){
        this(username, false);
    }

    public User(String username, boolean enabled){
        this.username = username;
        this.enabled = enabled;
    }
    public User() {

    }
    /*Bezargumentowy konstruktor gettery i setery lub Lombok */
}
