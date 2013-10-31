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
import sc.model.User;
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
    private static int SESSION_EXPIRATION_TIME = 30 * 60;
    protected static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Invalidate http session, remove connected cookies
     * @param session
     * @param res
     * @return 
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public @ResponseBody
    String doLogout(HttpSession session, HttpServletResponse res) {
        try {
            Cookie cookie = new Cookie(AUTH_COOKIE_NAME, "");
            cookie.setMaxAge(0);
            res.addCookie(cookie);
            
            session.invalidate();
        } catch (IllegalStateException ex) {
            logger.info("Attempt to invalidate already invalidated session");
        }
        return AjaxState.SUCCESS;
    }

    /**
     * Performs http authentication of a new user
     *
     * @param username username
     * @param password password
     * @param rememberMe is user want to be remembered?
     * @param res http response
     * @param session http session
     * @param model model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    String doLogin(@RequestParam(value = "login") String username,
            @RequestParam String password, @RequestParam(required = false) boolean rememberMe,
            HttpServletResponse res, HttpSession session, Model model) {
        String encPassword = passwordEncoder.encodePassword(password, null);

        if (userService.checkCredentails(username, encPassword)) {
            User currentUser = userService.getUser(username);
            if (currentUser != null) {
                session.setAttribute("user", currentUser);

                String sessionId = md5Hasher.encodePassword(username, null);
                Cookie cookie = new Cookie(AUTH_COOKIE_NAME, sessionId);
                cookie.setMaxAge(SESSION_EXPIRATION_TIME);
                res.addCookie(cookie);

                logger.info("User with login {} successfully authenticated into the application", username);
                return AjaxState.AUTHENTICATED;
            }
        }
        logger.debug("User with login {} cannot be authenticated inti the application", username);
        return AjaxState.UNAUTHENTICATED;

    }

    /**
     * Renders and returns application login form
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginForm(Model model) {
        return "login";
    }
}
