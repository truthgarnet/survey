package org.kong.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_USER")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Getter
    private String userName;
    @Getter
    private String userPwd;
    private String userNickName;
    @Getter
    private String role;

    public UserEntity(String userName, String userPwd, String role) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.role = role;
    }

}
