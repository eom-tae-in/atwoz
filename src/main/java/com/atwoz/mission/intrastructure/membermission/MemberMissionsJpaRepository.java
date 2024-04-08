package com.atwoz.mission.intrastructure.membermission;

import com.atwoz.mission.domain.membermission.MemberMissions;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMissionsJpaRepository extends JpaRepository<MemberMissions, Long> {

    MemberMissions save(final MemberMissions memberMissions);

    Optional<MemberMissions> findByMemberId(final Long memberId);
}
