package sc.controller;

import javax.servlet.http.Cookie;
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

    private static String AUTH_COOKIE_NAME = "SC_SESSION_COOKIE";
    private static String REMEMBERME_COOKIE_NAME = "SC_REMEMBER_ME_COOKIE";

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String setupHomePage(HttpServletRequest request, Model model) {
        boolean result = Boolean.FALSE;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTH_COOKIE_NAME)) {
                result = Boolean.TRUE;
            }
        }
        model.addAttribute("authenticated", result);
        return "home";
    }

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String getService(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTH_COOKIE_NAME)) {
                return "service";
            }
        }
        return "";
    }
}
