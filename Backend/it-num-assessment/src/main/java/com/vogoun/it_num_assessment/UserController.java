package com.vogoun.it_num_assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public String createUser(@RequestParam String username) {

        User u = new User();
        u.setUsername(username);
        userRepository.save(u);
        
        return "Saved";
    }
    
    @GetMapping()
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    
}
