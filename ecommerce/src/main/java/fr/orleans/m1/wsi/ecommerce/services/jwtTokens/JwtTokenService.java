package fr.orleans.m1.wsi.ecommerce.services.jwtTokens;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import fr.orleans.m1.wsi.ecommerce.services.custumUsers.CustumUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtTokenService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    private final String secretKey;
    private final long expirationToken;


    public JwtTokenService(
        @Value("${api.jwt.secret-key}") String secretKey,
        @Value("${api.jwt.expiration-time}") long expirationToken){
            this.secretKey = secretKey;
            this.expirationToken = expirationToken;
    }

    // genère mon token
    public String generateToken(Map<String, Object> extractClaims, Authentication authentication){
        
        CustumUserDetailService userDetailService = null;
        if (authentication.getPrincipal() instanceof CustumUserDetailService){
            userDetailService = (CustumUserDetailService) authentication.getPrincipal();
        }
        else {
            throw new IllegalAccessError("Accès non autoriser");
        }

        return Jwts.builder()
                   .claims(extractClaims)
                   .subject(userDetailService.getUsername())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + expirationToken))
                   .signWith(getSignInKey())
                   .compact();
    }

    // renvoie le nom d'utilisateur
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // qui extrait un claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolve){
        final Claims claims = extractAllClaims(token);
        return claimsResolve.apply(claims);
    }

    // verifie si mon token est tjrs valide
    public boolean isTokenValid(String token){
        try {
            SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            if (!(secret instanceof SecretKey)){
                throw new IllegalArgumentException("cette clé secret n'est pas valide");
            }
            Jws<Claims> jws = Jwts.parser().verifyWith(secret).build().parseSignedClaims(token);
            Claims claims = jws.getPayload();

            // on vérifie si le token n'as pas expirer
            if (claims.getExpiration().before(new Date())) {
               throw new JwtException("ce token à expirer");
            }
            return true;
        }
        catch (JwtException e){
            logger.error("Token invalide " + e.getMessage());
            return false;
        }
    }

    // extrait tous les claims
    private Claims extractAllClaims(String token){
        SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                   .verifyWith(secret)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    private Key getSignInKey(){
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    
}
