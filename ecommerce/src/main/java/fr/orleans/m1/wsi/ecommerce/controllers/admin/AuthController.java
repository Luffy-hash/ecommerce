package fr.orleans.m1.wsi.ecommerce.controllers.admin;


import java.util.List;
import java.util.stream.Collectors;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orleans.m1.wsi.ecommerce.dto.loginRequestDto;
import fr.orleans.m1.wsi.ecommerce.dto.tokenRequestDto;
import fr.orleans.m1.wsi.ecommerce.repositories.UsersRepository;
import fr.orleans.m1.wsi.ecommerce.services.custumUsers.CustumUserDetailService;
import fr.orleans.m1.wsi.ecommerce.services.jwtTokens.JwtTokenService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authentication;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;


    public AuthController(
        AuthenticationManager authentication, UsersRepository usersRepository,
        PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService
    ){
        this.authentication = authentication;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody loginRequestDto login){
        Authentication authentifiedUser = authentication.authenticate(
            new UsernamePasswordAuthenticationToken(login.email(), login.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentifiedUser);
        String token = jwtTokenService.generateToken(authentifiedUser);
        if (!(authentifiedUser.getPrincipal() instanceof CustumUserDetailService)){ throw new IllegalAccessError("Erreur");}
        CustumUserDetailService userDetailService = (CustumUserDetailService) authentifiedUser.getPrincipal();
        List<String> roles = userDetailService
                                .getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList());

        return ResponseEntity.ok(new tokenRequestDto(
            token, userDetailService.getUsername(), roles
        ));
    }

    
}
