package com.atwoz.admin.domain.admin;

import java.util.Optional;

public interface AdminRepository {

    Admin save(Admin admin);

    Optional<Admin> findAdminById(Long id);

    Optional<Admin> findAdminByEmail(String email);
}
