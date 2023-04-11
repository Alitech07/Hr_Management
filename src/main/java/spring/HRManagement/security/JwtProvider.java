package spring.HRManagement.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import spring.HRManagement.entity.Roles;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    static String secretKey = "HrManagementDasturiUchunMaxfiyKalitSozHisoblanadi";
    static long expireTime = 40_000_000;

    public String generateToken(String username, Set<Roles> roles){
        Date expireDate = new Date(System.currentTimeMillis()+expireTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles",roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return token;
    }

    public String getUsernameForToken(String token){
        String username = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }

}
