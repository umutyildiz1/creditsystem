package com.paycoreumutyildiz.creditsystem.Controller;

import com.paycoreumutyildiz.creditsystem.Model.User;
import com.paycoreumutyildiz.creditsystem.Service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public String login(@Valid @RequestBody User user) {
        return userService.signin(user.getUsername(), user.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid User user) {
        return userService.signup(user);
    }

    @DeleteMapping(value = "/{username}")
    public String delete(@PathVariable String username) {
        userService.delete(username);
        return username;
    }

    @GetMapping(value = "/me")
    public User whoami(HttpServletRequest req) {
        return userService.whoami(req);
    }
}
