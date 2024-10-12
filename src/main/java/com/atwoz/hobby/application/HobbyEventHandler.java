package com.atwoz.hobby.application;

import com.atwoz.hobby.domain.HobbyRepository;
import com.atwoz.hobby.exception.exceptions.HobbyNotFoundException;
import com.atwoz.profile.domain.event.ProfileFetchStartedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class HobbyEventHandler {

    private final HobbyRepository hobbyRepository;

    @EventListener(ProfileFetchStartedEvent.class)
    public void checkHobbyExists(final ProfileFetchStartedEvent event) {
        if (hobbyRepository.findHobbyByCode(event.hobbyCode()).isEmpty()) {
            throw new HobbyNotFoundException();
        }
    }
}
