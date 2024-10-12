package com.atwoz.hobby.application;

import com.atwoz.hobby.application.dto.HobbyCreateRequest;
import com.atwoz.hobby.application.dto.HobbyUpdateRequest;
import com.atwoz.hobby.domain.Hobby;
import com.atwoz.hobby.domain.HobbyRepository;
import com.atwoz.hobby.exception.exceptions.HobbyCodeAlreadyExistedException;
import com.atwoz.hobby.exception.exceptions.HobbyNameAlreadyExistedException;
import com.atwoz.hobby.exception.exceptions.HobbyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class HobbyService {

    private final HobbyRepository hobbyRepository;

    public void saveHobby(final HobbyCreateRequest hobbyCreateRequest) {
        validateHobbyCreateRequest(hobbyCreateRequest);
        Hobby createdHobby = Hobby.createWith(hobbyCreateRequest.name(), hobbyCreateRequest.code());
        hobbyRepository.save(createdHobby);
    }

    private void validateHobbyCreateRequest(final HobbyCreateRequest hobbyCreateRequest) {
        validateAlreadyExistedHobbyName(hobbyCreateRequest.name());
        validateAlreadyExitedHobbyCode(hobbyCreateRequest.code());
    }

    private void validateAlreadyExistedHobbyName(final String hobbyName) {
        hobbyRepository.findHobbyByName(hobbyName)
                .ifPresent(hobby -> {
                    throw new HobbyNameAlreadyExistedException();
                });
    }

    private void validateAlreadyExitedHobbyCode(final String hobbyCode) {
        hobbyRepository.findHobbyByCode(hobbyCode)
                .ifPresent(hobby -> {
                    throw new HobbyCodeAlreadyExistedException();
                });
    }

    public void updateHobby(final Long hobbyId,
                            final HobbyUpdateRequest hobbyUpdateRequest) {
        Hobby foundHobby = findHobbyById(hobbyId);
        validateHobbyUpdateRequest(foundHobby, hobbyUpdateRequest);
        foundHobby.update(hobbyUpdateRequest.name(), hobbyUpdateRequest.code());
    }

    private void validateHobbyUpdateRequest(final Hobby hobby,
                                            final HobbyUpdateRequest hobbyUpdateRequest) {
        validateHobbyNameNotChanged(hobby.getName(), hobbyUpdateRequest.name());
        validateHobbyCodeNotChanged(hobby.getCode(), hobbyUpdateRequest.code());
        validateAlreadyExistedHobbyName(hobbyUpdateRequest.name());
        validateAlreadyExitedHobbyCode(hobbyUpdateRequest.code());
    }

    private void validateHobbyNameNotChanged(final String currentHobbyName,
                                             final String newHobbyName) {
        if (currentHobbyName.equals(newHobbyName)) {
            throw new HobbyNameAlreadyExistedException();
        }
    }

    private void validateHobbyCodeNotChanged(final String currentHobbyCode,
                                             final String newHobbyCode) {
        if (currentHobbyCode.equals(newHobbyCode)) {
            throw new HobbyCodeAlreadyExistedException();
        }
    }

    public void deleteHobby(final Long hobbyId) {
        Hobby foundHobby = findHobbyById(hobbyId);
        hobbyRepository.deleteById(foundHobby.getId());
    }

    private Hobby findHobbyById(final Long hobbyId) {
        return hobbyRepository.findHobbyById(hobbyId)
                .orElseThrow(HobbyNotFoundException::new);
    }
}
