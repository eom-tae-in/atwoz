package com.atwoz.admin.infrastructure.admin;

import com.atwoz.admin.domain.admin.Admin;
import com.atwoz.admin.domain.admin.AdminRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("NonAsciiCharacters")
public class AdminFakeRepository implements AdminRepository {

    private final Map<Long, Admin> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Admin save(final Admin admin) {
        Admin savedAdmin = Admin.createWith(
                admin.getEmail(),
                admin.getPassword(),
                admin.getPassword(),
                admin.getName(),
                admin.getPhoneNumber()
        );

        map.put(id++, savedAdmin);
        return savedAdmin;
    }

    @Override
    public Optional<Admin> findAdminById(final Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Admin> findAdminByEmail(final String email) {
        return map.values().stream()
                .filter(admin -> email.equals(admin.getEmail()))
                .findAny();
    }
}
