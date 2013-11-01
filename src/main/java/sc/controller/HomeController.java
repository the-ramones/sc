package sc.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home page controller
 *
 * @author Paul Kulitski
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String setupHomePage(Model model) {
        return "home";
    }

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String getService(HttpServletRequest request, Model model) {
        return "service";
    }
}
