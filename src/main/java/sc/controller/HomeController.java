package sc.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String getService(HttpServletRequest request, HttpSession session,
            Locale locale, Model model) {
        if (locale.equals(Locale.forLanguageTag("be"))) {
            session.setAttribute("flag", "Belarus-large.png");
        } else if (locale.equals(Locale.forLanguageTag("ru"))) {
            session.setAttribute("flag", "Russia-large.png");
        } else if (locale.equals(Locale.forLanguageTag("en"))) {
            session.setAttribute("flag", "UnitedKingdom-large.png");
        }
        return "service";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(HttpServletRequest request, Model model) {
        return "error";
    }
}
