package com.example.lab03.Controllers;

import com.example.lab03.Interface.UserService;
import com.example.lab03.Models.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Log4j2
@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String handleRequest() throws Exception {
        return "login";
    }
    @RequestMapping(value = "/registery", method = RequestMethod.GET)
    protected String registeryUser(Model model) throws Exception {
        model.addAttribute("user", new User());
        return "registery";
    }
    @RequestMapping(value = "/confirmReg", method = RequestMethod.POST)
    protected String handleRequest(MultipartFile multipartFile, Model model, @Valid @ModelAttribute("user") User user, BindingResult result) throws Exception {
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "registery";
        }

        user.setFileContent(multipartFile.getBytes());
        userService.CreateUser(user);
        return "index";
    }
}
