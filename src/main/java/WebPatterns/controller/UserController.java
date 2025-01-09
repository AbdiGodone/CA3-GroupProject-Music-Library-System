package WebPatterns.controller;


import WebPatterns.business.User;
import WebPatterns.persistence.UserDao;
import WebPatterns.persistence.UserDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class UserController {
    @PostMapping("register")
    public String register(
            @RequestParam(name="username") String username,
            @RequestParam(name="password") String password,
            @RequestParam(name="confpassword") String confpassword,
            @RequestParam(name="firstname", required = false) String firstname,
            @RequestParam(name="lastname", required = false) String lastname,
            @RequestParam(name="email") String email,
            Model model){
        String errorMsg = null;
        if(username == null || username.isBlank()){
            errorMsg = "You cannot register without username";
        }
        else if(password == null || password.isBlank()){
            errorMsg = "You cannot register without password";
        }
        else if(confpassword == null || confpassword.isBlank() || !confpassword.equals(password)){
            errorMsg = "Passwords must match !";
        }
        else if(email == null || email.isEmpty()){
            errorMsg = "You cannot register without a valid email";
        }
        if(errorMsg != null){
            model.addAttribute("errorMessage", errorMsg);
            //return  "registerUser";
            //return  "register";
            return "registration";
        }
        // Build new user with the detail entered in registration form
        User newUser = User.builder()
                .username(username)
                .password(password)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .build();

        // create new dao and register new user
        UserDao userDao = new UserDaoImpl("database.properties");
        boolean registered = userDao.register(newUser);
        if(registered){
            String success = "Registration Successful";
            model.addAttribute("message", success);
            return "index";
        }else{
            // Log Info of failed Registration Attempt with imidiate line below
            log.info("Could not register user with username: " + username+ "and email: "+ email + ",");
            String failed = "could not register at this time";
            model.addAttribute("errorMessage", failed);
        }
        //return "register";
        return "registration";
    }

    /*=======================================================================================
    @PostMapping("registerUser")
    public String registerUser(
            @RequestParam(name="username") String username,
            @RequestParam(name="password") String password,
            @RequestParam(name="confpassword") String confpassword,
            @RequestParam(name="firstname", required = false) String firstname,
            @RequestParam(name="lastname", required = false) String lastname,
            @RequestParam(name="email") String email,
            Model model, HttpSession session) throws FileNotFoundException {
        // VALIDATION
        String view = "";
        UserDao userDao = new UserDaoImpl("database.properties");
        User u = new User(username, password, firstname, lastname, email);
        boolean added = userDao.register(u);
        if(added){
            view = "registerSuccess";
            model.addAttribute("registeredUser", u);
            log.info("User {} registered", u.getUsername());
        }else{
            view = "registerFailed";
            log.info("Registration failed with username {}", username);
        }
        return view;
    }
    @PostMapping("/login")
    public String loginUser(
            @RequestParam(name="username")String username,
            @RequestParam(name="password") String password,
            Model model, HttpSession session) throws FileNotFoundException {

        if(username.isBlank() || password.isBlank()){
            String errorMsg = "Username and password cannot be blank";
            model.addAttribute("errMsg", errorMsg);
            return "error";
        }

        UserDao userDao = new UserDaoImpl("database.properties");
        User u = userDao.login(username, password);

        if(u == null){
            String message = "No such username/password combination";
            model.addAttribute("message", message);
            return "loginFailed";
        }

        session.setAttribute("loggedInUser", u);
        return "loginSuccessful";
    }*/

}
