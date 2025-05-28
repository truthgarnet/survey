package org.kong.user.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Getter
    @Setter
    public static class Request {
        private Integer id;
        private String userId;
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
