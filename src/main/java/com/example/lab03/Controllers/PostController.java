package com.example.lab03.Controllers;

import com.example.lab03.Models.Post;
import com.example.lab03.Repository.PostGenreRepository;
import com.example.lab03.Repository.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;

@Log4j2
@Controller
public class PostController {
    private PostRepository repository;
    private PostGenreRepository postGenreRepository;

    public PostController(PostRepository repository, PostGenreRepository postGenreRepository) {
        this.repository = repository;
        this.postGenreRepository = postGenreRepository;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    protected String handleRequest(Model model,@PathVariable("id") int id) throws Exception {
        Post post = repository.getById(id);
        model.addAttribute("atrybut", post);
        return "post";
    }
    @RequestMapping(value = "/postAdd", method = RequestMethod.GET)
    protected String addPost(Model model) throws Exception {
        Post post = new Post();
        post.setAddedDate(LocalDate.now());
        post.setAuthor("Mariusz");
        model.addAttribute("atrybut", post);
        return "post";
    }
    @RequestMapping(value = "/editPost", method = RequestMethod.POST)
    protected String editPost(Model model,@ModelAttribute("postModel") Post post) throws Exception {
        Post postToUpdate = repository.getOne(post.getId());
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContents(post.getContents());
        postToUpdate.setCategory(post.getCategory());
        postToUpdate.setGenres(post.getGenres());
        repository.save(postToUpdate);
        return "redirect:/postList";
    }
    @RequestMapping(value = "/postDelete/{id}", method = RequestMethod.GET)
    protected String deletePost(Model model,@PathVariable("id") int id) throws Exception {
        Post post = repository.getById(id);
        model.addAttribute("atrybut", post);
        return "confirmDeletePost";
    }
    @RequestMapping(value = "/postDeleteConfirm/{id}", method = RequestMethod.GET)
    protected String postDeleteConfirm(Model model,@PathVariable("id") int id, RedirectAttributes redirAttrs) throws Exception {
        repository.deleteById(id);
        redirAttrs.addFlashAttribute("success", "Pomyślnie usunięto post!");
        return "redirect:/postList";
    }

    @RequestMapping(value = "/postList", method = RequestMethod.GET)
    protected String postList(Model model) throws Exception {
        ArrayList<Post> temp = new ArrayList<Post>();
        temp = (ArrayList<Post>) repository.findAll();
        model.addAttribute("atrybut", temp);
        return "postList";
    }
}
