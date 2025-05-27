package org.kong.user.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.kong.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class SessionUtil {

    private final RedisUtil redisUtil;

    /**
     * 세션을 삭제합니다.
     *
     * @param sessionId 삭제할 세션 ID
     */
    public void removeSession(String sessionId) {
        if (sessionId != null && !sessionId.isEmpty()) {
            redisUtil.deleteData(sessionId);
        }
    }

    /**
     * 현재 세션의 ID를 가져옵니다.
     *
     * @return 세션 ID
     */
    public String getCurrentSessionId() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpSession session = attributes.getRequest().getSession(false);
            if (session != null) {
                return session.getId();
            }
        }
        return null;
    }

    /**
     * 세션이 유효한지 확인합니다.
     *
     * @param sessionId 확인할 세션 ID
     * @return 세션 유효 여부
     */
    public boolean isValidSession(String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            return false;
        }
        return redisUtil.hasKey(sessionId);
    }
}
