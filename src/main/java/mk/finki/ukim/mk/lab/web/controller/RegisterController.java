package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.exceptions.InvalidCredentialsException;
import mk.finki.ukim.mk.lab.exceptions.PasswordMismatchException;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatPassword){
        try{
            userService.register(username, password, repeatPassword);

        } catch (InvalidCredentialsException | PasswordMismatchException e){
            return "redirect:/register?error=" + e.getMessage();
        }

        // TODO: 20.12.2024 Implement a Success template
        return "redirect:/login";
    }
}
