package com.example.lab03.Controllers;

import com.example.lab03.Interface.CommentService;
import com.example.lab03.Interface.PostService;
import com.example.lab03.Interface.UserService;
import com.example.lab03.Models.*;
import com.example.lab03.Repository.CategoryRepository;
import com.example.lab03.Repository.PostGenreRepository;
import com.example.lab03.Repository.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;

@Log4j2
@Controller
public class PostController {
    private PostRepository repository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostGenreRepository postGenreRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    protected String handleRequest(Model model,@PathVariable("id") int id) throws Exception {
        Post post = repository.getById(id);
        model.addAttribute("atrybut", post);
        boolean temp;
        if(postService.IsAuthor(post))
            temp = true;
        else
            temp = false;
        model.addAttribute("isAuthor", temp);
        var temp2 = userService.GetUserAvatarByUsername(post.getAuthor());
        if(temp2 == null)
            model.addAttribute("userAvatar", null);
        else
            model.addAttribute("userAvatar", Base64Utils.encodeToString(temp2));
        Comment comment = new Comment();
        model.addAttribute("commentTemp", comment);
        model.addAttribute("comments", commentService.getAllCommentByPostId(id));
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
    protected String editPost(Model model, @Valid @ModelAttribute("postModel") Post post, BindingResult result) throws Exception {
        if(result.hasErrors()){
            ArrayList<PostGenre> tempPG = (ArrayList<PostGenre>) postGenreRepository.findAll();
            model.addAttribute("isNew", true);
            model.addAttribute("postModel", post);
            model.addAttribute("genresModel", tempPG);
            return "addPost";
        }
        Post postToUpdate = repository.getOne(post.getId());
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContents(post.getContents());
        postToUpdate.setCategory(post.getCategory());
        postToUpdate.setGenres(post.getGenres());
        repository.save(postToUpdate);
        SearchObject searchObject = new SearchObject();
        model.addAttribute("searchObject", searchObject);
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
        SearchObject searchObject = new SearchObject();
        model.addAttribute("searchObject", searchObject);
        redirAttrs.addFlashAttribute("success", "Pomyślnie usunięto post!");
        return "redirect:/postList";
    }

    @RequestMapping(value = "/postList", method = RequestMethod.GET)
    protected String postList(Model model) throws Exception {
        ArrayList<Post> temp = new ArrayList<Post>();
        temp = (ArrayList<Post>) repository.findAll();
        ArrayList<Category> categories = (ArrayList<Category>)categoryRepository.findAll();
        SearchObject searchObject = new SearchObject();
        model.addAttribute("atrybut", temp);
        model.addAttribute("searchObject", searchObject);
        model.addAttribute("categories", categories);
        return "postList";
    }
    @RequestMapping(value = "/postList", method = RequestMethod.POST)
    protected String postList(Model model, SearchObject serachObject) throws Exception {
        ArrayList<Post> temp = postService.FindAllBySearchObject(serachObject);
        ArrayList<PostGenre> genres = (ArrayList<PostGenre>)postGenreRepository.findAll();
        ArrayList<Category> categories = (ArrayList<Category>)categoryRepository.findAll();
        model.addAttribute("atrybut", temp);
        model.addAttribute("searchObject", serachObject);
        model.addAttribute("categories", categories);
        return "postList";
    }
    @RequestMapping(value = "/addComment/{idPost}", method = RequestMethod.POST)
    protected String addPost(Model model, Comment comment, @PathVariable("idPost") int idPost) throws Exception {
        commentService.addComment(comment,idPost);
        ArrayList<Post> temp = new ArrayList<Post>();
        temp = (ArrayList<Post>) repository.findAll();
        ArrayList<PostGenre> genres = (ArrayList<PostGenre>)postGenreRepository.findAll();
        ArrayList<Category> categories = (ArrayList<Category>)categoryRepository.findAll();
        SearchObject searchObject = new SearchObject();
        model.addAttribute("atrybut", temp);
        model.addAttribute("searchObject", searchObject);
        model.addAttribute("categories", categories);
        return "postList";
    }
}
