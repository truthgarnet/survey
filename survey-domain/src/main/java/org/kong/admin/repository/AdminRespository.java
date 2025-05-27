package org.kong.admin.repository;

import java.util.Optional;
import org.kong.admin.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRespository extends JpaRepository<AdminEntity, Integer> {

  Optional<AdminEntity> findByAdminId(String adminId);
}
