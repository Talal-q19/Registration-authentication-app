package com.loginsignup.loginsignup;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class usercontroller {

    @Autowired
    private JdbcTemplate jdbcTemplate;

@GetMapping("/signup")
public String signUpPage() {
    return "signup";
}
   
   @GetMapping("/login")
   public String loginPage() {
       return "login";
   }

    @PostMapping("/users/signup")
    public String signUp(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println("hello");
        String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, username, password);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        String sql = "SELECT username FROM User WHERE username = ? AND password = ?";
        List<String> usernames = jdbcTemplate.query(sql, new Object[]{username, password}, (rs, rowNum) -> rs.getString(1));
        if (!usernames.isEmpty()) {
            return "home"; // return a different page after successful login
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
}



