package com.atwoz.admin.infrastructure.auth;

import com.atwoz.admin.application.auth.AdminAccessTokenProvider;
import com.atwoz.admin.domain.admin.service.AdminRefreshTokenProvider;
import com.atwoz.admin.ui.auth.support.AdminTokenExtractor;
import com.atwoz.member.exception.exceptions.auth.ExpiredTokenException;
import com.atwoz.member.exception.exceptions.auth.SignatureInvalidException;
import com.atwoz.member.exception.exceptions.auth.TokenFormInvalidException;
import com.atwoz.member.exception.exceptions.auth.TokenInvalidException;
import com.atwoz.member.exception.exceptions.auth.UnsupportedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class AdminJwtAccessTokenProvider implements AdminAccessTokenProvider,
        AdminRefreshTokenProvider,
        AdminTokenExtractor {

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String TOKEN_TYPE = "token type";
    private static final String REFRESH_TOKEN = "refresh token";
    private static final String ACCESS_TOKEN = "access token";
    private static final String ROLE = "role";
    private static final String ADMIN = "admin";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration-period}")
    private int accessTokenExpirationPeriod;

    @Value("${jwt.refresh-token-expiration-period}")
    private int refreshTokenExpirationPeriod;

    private Key key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public String createAccessToken(final Long id) {
        Claims claims = Jwts.claims();
        claims.put(ID, id);
        claims.put(TOKEN_TYPE, ACCESS_TOKEN);
        claims.put(ROLE, ADMIN);

        return createToken(claims, accessTokenExpirationPeriod);
    }

    @Override
    public String createRefreshToken(final String email) {
        Claims claims = Jwts.claims();
        claims.put(EMAIL, email);
        claims.put(TOKEN_TYPE, REFRESH_TOKEN);
        claims.put(ROLE, ADMIN);

        return createToken(claims, refreshTokenExpirationPeriod);
    }

    private String createToken(final Claims claims,
                               final int expirationPeriod) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt())
                .setExpiration(expiredAt(expirationPeriod))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Date issuedAt() {
        LocalDateTime now = LocalDateTime.now();

        return Date.from(now.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private Date expiredAt(final int expirationPeriod) {
        LocalDateTime now = LocalDateTime.now();

        return Date.from(now.plusDays(expirationPeriod)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    @Override
    public <T> T extract(final String token,
                         final String claimName,
                         final Class<T> classType) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(claimName, classType);
        } catch (SecurityException e) {
            throw new SignatureInvalidException();
        } catch (MalformedJwtException e) {
            throw new TokenFormInvalidException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedTokenException();
        } catch (IllegalArgumentException e) {
            throw new TokenInvalidException();
        }
    }
}
