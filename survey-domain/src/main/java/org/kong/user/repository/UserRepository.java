package org.kong.user.repository;

import org.kong.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUserId(Integer userId);

    Optional<UserEntity> findByUserName(String userName);

}
