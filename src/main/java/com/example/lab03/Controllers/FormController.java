package com.example.lab03.Controllers;

import com.example.lab03.Repository.CategoryRepository;
import com.example.lab03.Models.Category;
import com.example.lab03.Models.Post;
import com.example.lab03.Models.PostGenre;
import com.example.lab03.Repository.PostGenreRepository;
import com.example.lab03.Repository.PostRepository;
import com.example.lab03.Services.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;

@Log4j2
@Controller
@RequestMapping("/form")
public class FormController {
    private PostRepository repository;
    private EmailService emailService;
    private CategoryRepository categoryRepository;
    private PostGenreRepository postGenreRepository;

    public FormController(PostRepository repository, PostGenreRepository postGenreRepository, EmailService emailService, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.postGenreRepository = postGenreRepository;
        this.emailService = emailService;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String showForm(@Valid Model model, @RequestParam(value="uid", required=false, defaultValue="-1") int postId, RedirectAttributes redirAttrs) throws Exception {
        ArrayList<PostGenre> tempPG = (ArrayList<PostGenre>) postGenreRepository.findAll();
        Post post = new Post();
        boolean isNew;
        if(postId == -1){
            post.setAuthor("asdfasdf");
            isNew = true;
        }else {
            isNew = false;
            post = repository.getById(postId);
        }
        model.addAttribute("isNew", isNew);
        model.addAttribute("postModel", post);
        model.addAttribute("genresModel", tempPG);
        return "addPost";
    }
    @RequestMapping(method = RequestMethod.POST)
    protected String getForm(@ModelAttribute("postModel") Post post) throws Exception {
        post.setAuthor("Mariusz");
        post.setCountOfDislike(0);
        post.setAddedDate(LocalDate.now());
        post.setCountOfLike(0);
        repository.save(post);
        return "redirect:/postList";
    }

    @ModelAttribute(value = "category")
    private ArrayList<Category>getCategory(){
        return (ArrayList<Category>)categoryRepository.findAll();
    }
}
