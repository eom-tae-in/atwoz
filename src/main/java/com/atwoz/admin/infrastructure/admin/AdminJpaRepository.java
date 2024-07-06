package com.atwoz.admin.infrastructure.admin;

import com.atwoz.admin.domain.admin.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJpaRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findAdminByEmail(String email);
}
