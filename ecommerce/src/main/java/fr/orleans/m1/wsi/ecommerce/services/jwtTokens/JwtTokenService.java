package fr.orleans.m1.wsi.ecommerce.services.jwtTokens;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

// import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import fr.orleans.m1.wsi.ecommerce.services.custumUsers.CustumUserDetailService;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtTokenService {

    // private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    private final String secretKey;
    private final long expirationToken;


    public JwtTokenService(
        @Value("${api.jwt.secret-key}") String secretKey,
        @Value("${api.jwt.expiration-time}") long expirationToken){
            this.secretKey = secretKey;
            this.expirationToken = expirationToken;
    }

    // // genère mon token
    // public String generateToken(Authentication authentication){
    
    //     if (!(authentication.getPrincipal() instanceof CustumUserDetailService)){ throw new IllegalAccessError("Accès non autoriser");}
    //     CustumUserDetailService userDetailService = (CustumUserDetailService) authentication.getPrincipal();
        
    //     return Jwts.builder()
    //                .subject(userDetailService.getUsername())
    //                .issuedAt(new Date(System.currentTimeMillis()))
    //                .expiration(new Date(System.currentTimeMillis() + expirationToken))
    //                .signWith(getSignInKey())
    //                .compact();
    // }

    // // renvoie le nom d'utilisateur
    // public String extractUsername(String token){
    //     return extractClaim(token, Claims::getSubject);
    // }

    // // qui extrait un claims
    // public <T> T extractClaim(String token, Function<Claims, T> claimsResolve){
    //     final Claims claims = extractAllClaims(token);
    //     return claimsResolve.apply(claims);
    // }

    // // verifie si mon token est tjrs valide
    // public boolean isTokenValid(String token, Authentication authentication){

    //     // on recupère l'utilisateur connecté
    //     if (!(authentication.getPrincipal() instanceof CustumUserDetailService)){ throw new IllegalAccessError("Accès non autoriser");}
    //     CustumUserDetailService userDetailService = (CustumUserDetailService) authentication.getPrincipal();
        
    //     // on renvoie la valeur true ou false
    //     String username = extractUsername(token); // utilisateur du token
    //     return (username.equals(userDetailService.getUsername())) && !isTokenExpired(token);
    // }

    // // # Helpers methodes

    // // on verifie si la date est bonne ou pas (is date token expirée)
    // public boolean isTokenExpired(String token){
    //     return extractExpiration(token).before(new Date());
    // }

    // // qui me renvoie la date d'expiration
    // private Date extractExpiration(String token){
    //     return extractClaim(token, Claims::getExpiration);
    // }

    // // extrait tous les claims
    // private Claims extractAllClaims(String token){
    //     SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    //     return Jwts.parser()
    //                .verifyWith(secret)
    //                .build()
    //                .parseSignedClaims(token)
    //                .getPayload();
    // }

    // private Key getSignInKey(){
    //     byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
    //     return Keys.hmacShaKeyFor(keyBytes);
    // }

    
}
