package WebPatterns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @GetMapping("/")
    public String getHome(){

        return "index";
    }
    @GetMapping("/registerPage")
    public String register(){

        return "register";
    }
}
