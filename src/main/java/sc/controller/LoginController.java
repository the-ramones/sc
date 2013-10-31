package sc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sc.service.UserService;

/**
 * Login page controller
 *
 * @author Paul Kulitski
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;
    
    @Autowired
    @Qualifier("sha1Encryptor")
    PasswordEncoder passwordEncoder;
    
    @Autowired
    MessageSource messageSource;
    
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String setupLoginForm(Model model) {
        return "login";
    }

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public String doAuth(@RequestParam String username, @RequestParam String password,
            @RequestParam boolean rememberMe, Model model) {
        passwordEncoder.encodePassword(password, null);
        if (userService.checkCredentails(username, password)) {
            return "success";
        } else {
            return "failure";
        }
                
    }
}
