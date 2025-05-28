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
    private Integer id;

    private String userId;

    private String userName;

    @Setter
    private String password;

    private String userNickName;

    private String role;

    public UserEntity(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

}
