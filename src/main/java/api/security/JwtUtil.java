package api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private final Algorithm algorithm;
    private final long expirationTimeMs;
    private final long refreshExpirationTimeMs;

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationTimeMs,
            @Value("${jwt.refresh-expiration}") long refreshExpirationTimeMs
    ) {
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.expirationTimeMs = expirationTimeMs;
        this.refreshExpirationTimeMs = refreshExpirationTimeMs;
    }

    public String generateToken(String username, List<String> roles) {
        return buildToken(username, roles, expirationTimeMs, false);
    }

    public String generateRefreshToken(String username, List<String> roles) {
        return buildToken(username, roles, refreshExpirationTimeMs, true);
    }

    private String buildToken(String username, List<String> roles, long duration, boolean isRefresh) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + duration);

        com.auth0.jwt.JWTCreator.Builder builder = JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .withClaim("roles", roles); // <-- Aqui adiciona os perfis ao token

        if (isRefresh) {
            builder.withClaim("refresh", true);
        }
        return builder.sign(algorithm);
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        return decodedJWT.getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        return decodedJWT.getClaim("roles").asList(String.class);
    }

    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        Boolean refresh = decodedJWT.getClaim("refresh").asBoolean();
        return Boolean.TRUE.equals(refresh);
    }





}