package sc.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sc.ajax.AjaxState;
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
    @Qualifier("md5Hasher")
    PasswordEncoder md5Hasher;
    @Autowired
    MessageSource messageSource;
    private static String AUTH_COOKIE_NAME = "SC_SESSION_COOKIE";
    private static String REMEMBERME_COOKIE_NAME = "SC_REMEMBER_ME_COOKIE";
    private static int SESSION_EXPIRATION_TIME = 84600;
    protected static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public @ResponseBody
    String setupLoginForm(HttpSession session, HttpServletRequest req,
            HttpServletResponse res, Model model) {
        try {
            session.invalidate();
            Cookie cookie = new Cookie(AUTH_COOKIE_NAME, "");
            cookie.setMaxAge(0);
            res.addCookie(cookie);
        } catch (IllegalStateException ex) {
            logger.info("Attempt to invalidate already invalidated session");
        }
        return AjaxState.SUCCESS;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    String doAuth(@RequestParam(value = "login") String username,
            @RequestParam String password, @RequestParam(required = false) boolean rememberMe,
            HttpServletResponse res, HttpSession session, Model model) {
        String encPassword = passwordEncoder.encodePassword(password, null);
        String sessionId = md5Hasher.encodePassword(username, null);
        if (userService.checkCredentails(username, encPassword)) {
            Cookie cookie = new Cookie(AUTH_COOKIE_NAME, sessionId);
            cookie.setMaxAge(30 * 60);
            res.addCookie(cookie);
//            session.setAttribute("user", userService.);

            logger.info("User with login {} successfully authenticated into the application", username);
            return AjaxState.AUTHENTICATED;
        } else {

            logger.debug("User with login {} cannot be authenticated inti the application", username);
            return AjaxState.UNAUTHENTICATED;
        }

    }
}
