package sc.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sc.ajax.CookieNames;
import sc.ajax.SessionConstants;
import sc.model.Token;
import sc.model.User;
import sc.service.TokenService;
import sc.service.UserService;

/**
 * Security interceptor
 *
 * @author Paul Kulitski
 */
@Component
public class RememberMeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    protected static final Logger logger = LoggerFactory.getLogger(RememberMeInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session;
        boolean isMalformedRememberMe = false;
        String originTokenUsername = null;
        String originTokenValue = null;
        session = request.getSession(false);
        if (session == null || ((session.getAttribute(SessionConstants.AUTHENTICATED) == null)
                || (session.getAttribute(SessionConstants.AUTHENTICATED) == Boolean.FALSE))) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(CookieNames.SC_AUTH_COOKIE_NAME)) {
                    try {
                        System.out.println("INTERCEPTOR: " + cookie.getName() + " : [" + cookie.getValue() + "]");
                        String[] split = cookie.getValue().split("-");
                        String usernameHash = split[0];
                        String token = split[1];
                        System.out.println("SPLIT. TOKEN : { " + usernameHash + " , " + token + "}");
                        Token originToken = tokenService.findTokenByHash(usernameHash);
                        if (originToken != null) {
                            originTokenUsername = originToken.getUsername();
                            originTokenValue = originToken.getToken();
                        }
                        System.out.println("TOKEN : { " + originTokenUsername + " , " + originTokenValue + ",}");
                        if (token != null && originTokenValue != null) {

                            if (token.equals(originTokenValue)) {
                                //assumed 'remember-me' authentication

                                session = request.getSession();
                                //TODO: revert from hash?
                                
                                User currentUser = userService.getUser(originTokenUsername);
                                System.out.println("PREPARE TO ADD AUTHENTICATED");
                                if (currentUser != null) {
                                    System.out.println("ADDED AUTHENTICATED AND CURRENT USER");
                                    session.setAttribute(SessionConstants.SC_USER, currentUser);
                                    session.setAttribute(SessionConstants.AUTHENTICATED, Boolean.TRUE);
                                }
                                tokenService.removeToken(originToken);
                                tokenService.addToken(originTokenUsername);
                            } else {
                                //assume cookie stolen
                                isMalformedRememberMe = true;
                            }

                        }
                    } catch (IndexOutOfBoundsException ioex) {
                        logger.warn("Malformed 'remember-me' cookie encountered");
                        //assume cookie malformed
                        isMalformedRememberMe = true;
                    }
                    if (isMalformedRememberMe) {
                        System.out.println("MALFORMED COOKIE");
                        // assumed 'remember-me' cookie stolen
                        if (originTokenUsername != null) {
                            tokenService.removeToken(originTokenUsername);
                        }
                        session = request.getSession();
                        if (session != null) {
                            session.setAttribute(SessionConstants.SC_USER, null);
                            session.setAttribute(SessionConstants.AUTHENTICATED, false);
                        }
                        // remove cookie
                        Cookie removeCookie = new Cookie(CookieNames.SC_AUTH_COOKIE_NAME, "");
                        cookie.setMaxAge(0);
                        response.addCookie(removeCookie);

                        logger.warn("Weren't able to perform remember-me authentication: maybe token has been corrupted or stolen?");
                    }
                }
            }
        }
    }
}
