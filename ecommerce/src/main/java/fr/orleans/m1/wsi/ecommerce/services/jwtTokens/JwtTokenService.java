package fr.orleans.m1.wsi.ecommerce.services.jwtTokens;


import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import fr.orleans.m1.wsi.ecommerce.configSecurity.RsaKeyProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.security.oauth2.jwt.JwtEncoder;

@Service
public class JwtTokenService {

    @Value("${rsa.public-key}")
    private RSAPublicKey rsaKeys;
    private final JwtEncoder jwtEncoder;

    // Constructor
    public JwtTokenService(JwtEncoder jwtEncoder, RsaKeyProperties rsaKeyProperties) {
        this.jwtEncoder = jwtEncoder;
        this.rsaKeys = rsaKeyProperties.publicKey();
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String getUsername(String token) {
        try {
            JWT jwt = JWTParser.parse(token);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token non valide", e);
        }
    }

    // public boolean validateToken(String token){
    //     Jwts
    //     .parser()
    //     .verifyWith(rsaKeys)
    //     .build()
    //     .parse(token);
    //     return true;
    // }

    public boolean validateToken(String token) {
        try {            
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(rsaKeys)
                    .build()
                    .parseSignedClaims(token);
            
            // Vérification de l'expiration
            return jws.getPayload().getExpiration().after(new Date());
        } catch (SignatureException e) {
            // Signature invalide
            throw new JwtException("Signature du token invalid", e);
        } catch (JwtException | IllegalArgumentException e) {
            // Token invalide ou malformé
            throw new JwtException("Token invalid", e);
        }
    }

    public String extractUsername(String token) {
        try {
            JWT jwt = JWTParser.parse(token);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    public String refreshToken(String token) {
        try {
            // Parse the existing token
            JWT jwt = JWTParser.parse(token);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
    
            // Extract the subject (username) and scope
            String subject = claims.getSubject();
            String scope = claims.getStringClaim("scope");
    
            // Generate a new token with updated expiration
            Instant now = Instant.now();
            JwtClaimsSet newClaims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(1, ChronoUnit.HOURS)) // Extend expiration by 1 hour
                    .subject(subject)
                    .claim("scope", scope)
                    .build();
    
            return jwtEncoder.encode(JwtEncoderParameters.from(newClaims)).getTokenValue();
        } catch (Exception e) {
            throw new RuntimeException("Token non rafraichi", e);
        }
    }
    
}
