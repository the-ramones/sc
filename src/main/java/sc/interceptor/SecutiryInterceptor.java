package sc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sc.ajax.SessionConstants;

/**
 * Security interceptor
 *
 * @author Paul Kulitski
 */
@Component
public class SecutiryInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Boolean authenticated = (Boolean) session.getAttribute(SessionConstants.AUTHENTICATED);
            if (authenticated != null && authenticated == true) {
                return true;
            }
        }
        return false;
    }
}
