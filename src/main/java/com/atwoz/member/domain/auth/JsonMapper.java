package com.atwoz.member.domain.auth;

import com.atwoz.member.infrastructure.auth.dto.MemberInfoKeyWordRequest;
import com.atwoz.member.infrastructure.auth.dto.MemberInfoResponse;

public interface JsonMapper {

    String getValueByKey(String json, String key);

    MemberInfoResponse extractMemberInfoFrom(String memberInfoResponse,
                                             MemberInfoKeyWordRequest memberInfoKeyWordRequest);

    byte[] convertObjectToJsonByteArray(Object object);
}
