package com.atwoz.member.application.member.profile.hobby;

import com.atwoz.member.application.member.profile.hobby.dto.HobbyCreateRequest;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyUpdateRequest;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyCodeAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNotFoundException;
import java.util.Optional;
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
        Optional<Hobby> foundHobby = hobbyRepository.findHobbyByName(hobbyName);

        if (foundHobby.isPresent()) {
            throw new HobbyNameAlreadyExistedException();
        }
    }

    private void validateAlreadyExitedHobbyCode(final String hobbyCode) {
        Optional<Hobby> foundHobby = hobbyRepository.findHobbyByCode(hobbyCode);

        if (foundHobby.isPresent()) {
            throw new HobbyCodeAlreadyExistedException();
        }
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
