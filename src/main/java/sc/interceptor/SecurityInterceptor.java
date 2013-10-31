package sc.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Security interceptor
 *
 * @author Paul Kulitski
 */
@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private static String AUTH_COOKIE_NAME = "SC_SESSION_COOKIE";
    private static String REMEMBERME_COOKIE_NAME = "SC_REMEMBER_ME_COOKIE";

    @Override
    public boolean preHandle(HttpServletRequest request, 
        HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        if (request.getAttribute("X-Requested-With") != null
                && !request.getAttribute("X-Requested-With").equals("XMLHttpRequest")) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                System.out.println("COOKIE: " + cookie.getName() + " " + cookie.getValue());
                if (cookie.getName().equals(AUTH_COOKIE_NAME)) {
                    if (!cookie.getValue().isEmpty()) {
                        modelAndView.addObject("authenticated", Boolean.TRUE);
                        return;
                    }
                }
                break;
            }
            modelAndView.addObject("authenticated", Boolean.FALSE);
        }
    }
}
