package sc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Security interceptor
 *
 * @author Paul Kulitski
 */
@Component
public class SecutiryInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String xRequestedWith = (String) request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.equals("XMLHttpRequest")) {
            return true;
        }
        request.getRequestDispatcher("/error").forward(request, response);
        return false;
    }
}
