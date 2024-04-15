package com.atwoz.member.infrastructure.auth;

import com.atwoz.member.domain.auth.JsonMapper;
import com.atwoz.member.exception.exceptions.auth.InvalidJsonKeyException;
import com.atwoz.member.exception.exceptions.auth.JsonDataInvalidException;
import com.atwoz.member.infrastructure.auth.dto.MemberInfoKeyWordRequest;
import com.atwoz.member.infrastructure.auth.dto.MemberInfoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class JackonJsonMapper implements JsonMapper {

    private static final String DELIMITER = "\\.";

    @Override
    public String getValueByKey(final String json, final String key) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(json)
                    .get(key);
            return Optional.ofNullable(jsonNode.asText())
                    .orElseThrow(InvalidJsonKeyException::new);

        } catch (JsonProcessingException exception) {
            throw new JsonDataInvalidException();
        }
    }

    @Override
    public MemberInfoResponse extractMemberInfoFrom(final String memberInfoResponse,
                                                    final MemberInfoKeyWordRequest memberInfoKeyWordRequest) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(memberInfoResponse);
            String email = getValue(memberInfoKeyWordRequest.emailKeyWord(), jsonNode);
            String nickname = getValue(memberInfoKeyWordRequest.nicknameKeyWord(), jsonNode);
            return new MemberInfoResponse(email, nickname);

        } catch (JsonProcessingException exception) {
            throw new JsonDataInvalidException();
        }
    }

    private String getValue(final String keyWord, final JsonNode jsonNode) {
        return Arrays.stream(keyWord.split(DELIMITER))
                .reduce(jsonNode, JsonNode::get, (parentPath, childPath) -> childPath)
                .asText();
    }

    @Override
    public byte[] convertObjectToJsonByteArray(final Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsBytes(object);

        } catch (JsonProcessingException exception) {
            throw new JsonDataInvalidException();
        }
    }
}
