package org.kong.user.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Getter
    public static class Request {
        private int userId;
        private String userPwd;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int userId;
        private String userName;
        private String role;
    }

}
