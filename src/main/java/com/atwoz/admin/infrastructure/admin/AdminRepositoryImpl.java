package com.atwoz.admin.infrastructure.admin;

import com.atwoz.admin.domain.admin.Admin;
import com.atwoz.admin.domain.admin.AdminRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AdminRepositoryImpl implements AdminRepository {

    private final AdminJpaRepository adminJpaRepository;

    @Override
    public Admin save(final Admin admin) {
        return adminJpaRepository.save(admin);
    }

    @Override
    public Optional<Admin> findAdminById(final Long id) {
        return adminJpaRepository.findById(id);
    }

    @Override
    public Optional<Admin> findAdminByEmail(final String email) {
        return adminJpaRepository.findAdminByEmail(email);
    }
}
