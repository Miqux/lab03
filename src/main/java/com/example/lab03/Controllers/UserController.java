package com.example.lab03.Controllers;

import com.example.lab03.Interface.UserService;
import com.example.lab03.Models.Post;
import com.example.lab03.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    protected String handleRequest(Model model) throws Exception {
        ArrayList<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userList";
    }
    @RequestMapping(value = "/userDelete/{id}", method = RequestMethod.GET)
    protected String deletePost(Model model,@PathVariable("id") int id) throws Exception {
        userService.DeleteUserById(id);
        return "index";
    }
}
