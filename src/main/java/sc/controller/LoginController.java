package sc.controller;

import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sc.ajax.AjaxStates;
import sc.ajax.CookieNames;
import sc.ajax.Languages;
import sc.ajax.SessionConstants;
import sc.model.Token;
import sc.model.User;
import sc.service.TokenService;
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
    @Autowired
    TokenService tokenService;
    protected static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Invalidate http session, remove connected cookies
     *
     * @param session
     * @param res
     * @return
     */
    @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
    public @ResponseBody
    String doLogout(HttpSession session, HttpServletResponse res) {
        try {
            Cookie cookie = new Cookie(CookieNames.SC_AUTH_COOKIE_NAME, "");
            cookie.setMaxAge(0);
            res.addCookie(cookie);

            session.invalidate();
        } catch (IllegalStateException ex) {
            logger.info("Attempt to invalidate already invalidated session");
        }
        return AjaxStates.SUCCESS;
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
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public @ResponseBody
    String doLogin(@RequestParam(value = "login") String username,
            @RequestParam String password,
            @RequestParam(required = false) boolean rememberMe,
            @RequestParam(required = false) String language,
            HttpServletResponse res, HttpSession session, Model model) {

        String encPassword = passwordEncoder.encodePassword(password, null);
        
        if (userService.checkCredentails(username, encPassword)) {
            User currentUser = userService.getUser(username);
            if (currentUser != null) {
                session.setAttribute(SessionConstants.SC_USER, currentUser);
                session.setAttribute(SessionConstants.AUTHENTICATED, Boolean.TRUE);

                if (rememberMe == true) {

                    //sync?
                    tokenService.removeToken(username);
                    tokenService.addToken(username);
                    logger.debug("Added a new token");
                    Token token = tokenService.findToken(username);
                    if (token != null) {
                        String sessionId = token.getHashedUsername() + '-' + token.getToken();

                        Cookie cookie = new Cookie(CookieNames.SC_AUTH_COOKIE_NAME, sessionId);

                        cookie.setMaxAge(CookieNames.SESSION_EXPIRATION_TIME);
                        res.addCookie(cookie);
                        logger.debug("Remember-me cookie has been added");
                    } else {
                        logger.warn("Cannot apply a 'Remember Me' authentication. Sync?");
                    }
                }

                String lang;
                if (language != null && Languages.supports(language)) {
                    lang = language.trim().toLowerCase();
                } else {
                    lang = userService.getLanguage(username);
                    if (lang == null || lang.isEmpty()) {
                        lang = Languages.DEFAULT;
                    }
                }
                if (lang != null) {
                    Cookie langCookie = new Cookie(CookieNames.SC_USER_LANGUAGE, lang);
                    langCookie.setMaxAge(CookieNames.SESSION_EXPIRATION_TIME);
                    res.addCookie(langCookie);
                    LocaleContextHolder.setLocale(Locale.forLanguageTag(lang));
                }

                logger.info("User with login {} successfully authenticated into the application", username);
                return AjaxStates.AUTHENTICATED;
            }
        }
        logger.debug("User with login {} cannot be authenticated inti the application", username);
        return AjaxStates.UNAUTHENTICATED;

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
