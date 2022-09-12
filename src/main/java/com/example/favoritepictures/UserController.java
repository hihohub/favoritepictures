package com.example.favoritepictures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/new")
    @ResponseBody
    public String newUser(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setActive(1);
        user.setRoles("USER");
        userRepository.save(user);
        return "" + user.getId();
    }

    @PostMapping(value="/json",consumes="application/json")
    @ResponseBody
    public String newUser(@RequestBody User user) {
        user.setUserName(user.getUserName());
        user.setPassword(user.getPassword());
        user.setActive(1);
        user.setRoles("USER");
        userRepository.save(user);
        return "" + user.getId();
    }

    @PostMapping("/validate")
    public String validate(@RequestParam String username, @RequestParam String password){
        String result = "error - could not validate user";
        try{
            User user = userRepository.findByUserName(username);
            if(user.getPassword().equals(password)){
                result = "redirect:/picture.html?user=" + username;
            }
        }catch(Exception exc){
        }
        return result;
    }

    /*@GetMapping("/signup")
    public String signup(){
        return "redirect:/signup.html";
    }*/

    /*@GetMapping("/login")
    public String login(){
        return "redirect:/login.html";
    }*/

}
