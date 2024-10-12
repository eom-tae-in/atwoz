package com.atwoz.profile.infrastructure;

import com.atwoz.profile.domain.YearManager;
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
