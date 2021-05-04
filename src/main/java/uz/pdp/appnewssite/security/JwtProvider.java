package uz.pdp.appnewssite.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.appnewssite.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    public static final long EXPIRE_TIME = 36_000_000;      // 10 hours
    public static final String KEY = "ThisIsTheSecretKeyOfToken";

    public String generateToken(String username, Role role) {
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .claim("role", role.getName())
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
