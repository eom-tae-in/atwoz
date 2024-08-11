package com.atwoz.member.infrastructure.member.profile.hobby;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyResponse;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class HobbyFakeRepository implements HobbyRepository {

    private final Map<Long, Hobby> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Hobby save(final Hobby hobby) {
        Hobby createdHobby = Hobby.builder()
                .id(id)
                .name(hobby.getName())
                .code(hobby.getCode())
                .build();
        map.put(id++, createdHobby);

        return createdHobby;
    }

    @Override
    public Optional<Hobby> findHobbyById(final Long hobbyId) {
        return Optional.ofNullable(map.get(hobbyId));
    }

    @Override
    public Optional<Hobby> findHobbyByName(final String hobbyName) {
        return map.values()
                .stream()
                .filter(hobby -> hobbyName.equals(hobby.getName()))
                .findAny();
   }

    @Override
    public Optional<Hobby> findHobbyByCode(final String hobbyCode) {
        return map.values()
                .stream()
                .filter(hobby -> hobbyCode.equals(hobby.getCode()))
                .findAny();
    }

    @Override
    public Page<HobbyResponse> findHobbiesWithPaging(final Pageable pageable) {
        List<HobbyResponse> hobbyResponses = map.values()
                .stream()
                .sorted(Comparator.comparing(Hobby::getId))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(HobbyResponse::from)
                .toList();

        return new PageImpl<>(hobbyResponses);
    }

    @Override
    public void deleteById(final Long hobbyId) {
        map.remove(hobbyId);
    }
}
