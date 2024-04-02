package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.profile.physical_profile.YearManager;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class YearManagerImpl implements YearManager {

    @Override
    public int getCurrentYear() {
        LocalDateTime now = LocalDateTime.now();
        return now.getYear();
    }
}
