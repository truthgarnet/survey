package org.kong.admin.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUsersResponse {

    private Integer userId;
    private String userName;
    private LocalDateTime loginDate;
    private LocalDateTime rgstDateTime;

}
