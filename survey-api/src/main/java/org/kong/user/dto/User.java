package org.kong.user.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    private Integer userId;
    private String userName;
    private String userNickName;

    @Getter
    public static class Request {
        private int userId;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int userId;
        private String userName;
        private String userNickName;
    }

}
