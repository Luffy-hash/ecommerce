package fr.orleans.m1.wsi.ecommerce.services.auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.orleans.m1.wsi.ecommerce.dto.loginRequestDto;
import fr.orleans.m1.wsi.ecommerce.repositories.UsersRepository;
import fr.orleans.m1.wsi.ecommerce.services.jwtTokens.JwtTokenService;

@Service
public class UsersAuthServices {
    
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public UsersAuthServices(
        JwtTokenService jwtTokenService, 
        AuthenticationManager authenticationManager,
        UsersRepository userRepository,
        PasswordEncoder passwordEncoder) {
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    public String login(loginRequestDto loginRequestDto) {
        // Authenticate the user using the authentication manager
        // This will throw an exception if authentication fails
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
            loginRequestDto.email(),
            loginRequestDto.password()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Generate and return a JWT token for the authenticated user
        return jwtTokenService.generateToken(authentication);
    }

    // public ResponseEntity<?> register(RegistrationRequestDto request) {

    //     // Vérification de l'existence de l'utilisateur
    //     if (userRepository.existsByUsername(request.username())) {
    //         return ResponseEntity.badRequest().body("Cet utilisateur existe déjà");
    //     }

    //     // Vérification de la longueur du mot de passe
    //     if (request.password().length() < 8) {
    //         return ResponseEntity.badRequest().body("Le mot de passe doit faire au moins 8 caractères");
    //     }
    //     // Vérification de la présence de l'email
    //     if (request.email() == null || request.email().isBlank()) {
    //         return ResponseEntity.badRequest().body("L'email ne peut pas être vide");
    //     }
    //     // Vérification de la présence du mot de passe
    //     if (request.password() == null || request.password().isBlank()) {
    //         return ResponseEntity.badRequest().body("Le mot de passe ne peut pas être vide");
    //     }
    //     // Vérification de la présence du nom d'utilisateur
    //     if (request.username() == null || request.username().isBlank()) {
    //         return ResponseEntity.badRequest().body("Le nom d'utilisateur ne peut pas être vide");
    //     }
    //     // Vérification de la présence de l'email
    //     if (request.email() == null || request.email().isBlank()) {
    //         return ResponseEntity.badRequest().body("L'email ne peut pas être vide");
    //     }
        
        
    //     // construisons l'utilisateur
    //     Users user = Users.builder()
    //         .username(request.username())
    //         .password(passwordEncoder.encode(request.password()))
    //         .build();

    //     // enregistrement
    //     userRepository.save(user);

    //     String token = jwtTokenService.generateToken(
    //         new UsernamePasswordAuthenticationToken(
    //             request.username(),
    //             Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
    //         )
    //     );
    //     tokenRequestDto loginResponse = new tokenRequestDto(token);
    //     return ResponseEntity.ok(loginResponse);
    // }

    public void logout() {
        // Invalidate the user's session or token
        SecurityContextHolder.clearContext();
    }

}