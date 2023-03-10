package com.example.lab03.Repository;

import com.example.lab03.Models.Category;
import com.example.lab03.Models.Post;
import com.example.lab03.Models.PostGenre;
import com.example.lab03.Models.Role;
import com.example.lab03.Models.User;
import com.example.lab03.Repository.CategoryRepository;
import com.example.lab03.Repository.PostGenreRepository;
import com.example.lab03.Repository.PostRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class RepositoriesInitializer {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostGenreRepository postGenreRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    InitializingBean init() {

        return () -> {
            Category temp1 = new Category();
            temp1.setName("Ludzie");
            temp1.setId(1);
            Category temp2 = new Category();
            temp2.setName("Motoryzacja");
            temp2.setId(2);
            Category temp3 = new Category();
            temp3.setName("Sport");
            temp3.setId(3);
            categoryRepository.save(temp1);
            categoryRepository.save(temp2);
            categoryRepository.save(temp3);


            PostGenre temp4 = new PostGenre();
            temp4.setName("Test1");
            temp4.setId(1);

            PostGenre temp5 = new PostGenre();
            temp5.setName("Test2");
            temp5.setId(2);

            PostGenre temp6 = new PostGenre();
            temp6.setName("Test3");
            temp6.setId(3);

            PostGenre temp7 = new PostGenre();
            temp7.setName("Test4");
            temp7.setId(4);

            postGenreRepository.save(temp4);
            postGenreRepository.save(temp5);
            postGenreRepository.save(temp6);
            postGenreRepository.save(temp7);

            ArrayList<Post> temp = new ArrayList<Post>();
            Post post = new Post();
            post.setAddedDate(LocalDate.of(2022, 2, 13));
            post.setAuthor("Mariusz");
            post.setContents("Asdfasdfoansen akrfasfn asneo naskfnasndf asen osanf snae nas.");
            post.setCountOfLike(10);
            post.setCountOfDislike(2);
            post.setTitle("Nowy tytu??");
            temp.add(post);
            Post post2 = new Post();
            post2.setAddedDate(LocalDate.of(2021, 2, 20));
            post2.setAuthor("Janasd");
            post2.setContents("Asdfastyuiyokrfasfn atyui nas.");
            post2.setCountOfLike(3);
            post2.setCountOfDislike(123);
            post2.setTitle("Nowy tytu??2");
            temp.add(post2);
            Post post3 = new Post();
            post3.setAddedDate(LocalDate.of(2022, 2, 2));
            post3.setAuthor("Kubaasd");
            post3.setContents("Asdfasasdfoanssdfasdf naskfnasndf asen osanf snae nas.");
            post3.setCountOfLike(2213);
            post3.setCountOfDislike(412);
            post3.setTitle("Nowy tytu??3");
            temp.add(post3);
            Post post4 = new Post();
            post4.setAddedDate(LocalDate.of(2022, 3, 21));
            post4.setAuthor("Adamasd");
            post4.setTitle("Nowy tytu??4");
            post4.setContents("Asdfasdfoansen akrfasftyuia sd fasdfasrase rqwe rqwer qwer dfasdf asdf  snae nas.");
            post4.setCountOfLike(124523);
            post4.setCountOfDislike(421);
            temp.add(post4);
            postRepository.saveAll(temp);
            Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
            Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));
            User user = new User("user", true);
            user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
            user.setPassword(passwordEncoder.encode("user1"));

            User admin = new User("admin", true);
            admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
            admin.setPassword(passwordEncoder.encode("admin"));

            User test = new User("superuser", true);
            test.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
            test.setPassword(passwordEncoder.encode("superuser"));

            User test2 = new User("1234", true);
            test2.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
            test2.setPassword(passwordEncoder.encode("12345"));
            userRepository.save(user);
            userRepository.save(admin);
            userRepository.save(test);
            userRepository.save(test2);

        };
    }
}
