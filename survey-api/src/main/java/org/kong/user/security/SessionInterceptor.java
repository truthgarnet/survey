package org.kong.user.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    private static final String SESSION_KEY = "SESSION";
    private static final String REDIS_SESSION_KEY = ":sessions:";

    @Value("${spring.session.redis.namespace}")
    private String nameSpace;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                            final Object handler) throws  Exception {
        log.info("====prehandle=====");
        HttpSession session = request.getSession(false);
        log.info("====prehandle===== session: {}}", session);

        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        log.info("====prehandle=====context: {}", context);

        if (context == null || context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }
}
