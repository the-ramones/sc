package sc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Security interceptor
 *
 * @author Paul Kulitski
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    
    protected static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String xRequestedWith = (String) request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.equals("XMLHttpRequest")) {
            return true;
        }
        logger.debug("Rejected request to prohibited area of a site: {}", request.getRequestURL());
        request.getRequestDispatcher("/error").forward(request, response);
        return false;
    }
}
