package org.kong.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String userName;

    @Setter
    private String userPwd;

    private String userNickName;

    private String role;

    public UserEntity(String userName, String userPwd, String role) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.role = role;
    }

}
