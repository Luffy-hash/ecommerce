package fr.orleans.m1.wsi.ecommerce.controllers.admin;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orleans.m1.wsi.ecommerce.dto.RegistrationRequestDto;
import fr.orleans.m1.wsi.ecommerce.dto.loginRequestDto;
import fr.orleans.m1.wsi.ecommerce.dto.tokenRequestDto;
import fr.orleans.m1.wsi.ecommerce.models.ERole;
import fr.orleans.m1.wsi.ecommerce.models.Role;
import fr.orleans.m1.wsi.ecommerce.models.Users;
import fr.orleans.m1.wsi.ecommerce.repositories.RoleRepository;
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
    private final RoleRepository roleRepository;


    public AuthController(
        AuthenticationManager authentication, UsersRepository usersRepository,
        PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService,
        RoleRepository roleRepository
    ){
        this.authentication = authentication;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.roleRepository = roleRepository;
    }

    // @PostMapping("/signin")
    // public ResponseEntity<?> login(@Valid @RequestBody loginRequestDto login){
    //     Authentication authentifiedUser = authentication.authenticate(
    //         new UsernamePasswordAuthenticationToken(login.email(), login.password())
    //     );

    //     SecurityContextHolder.getContext().setAuthentication(authentifiedUser);
    //     //String token = jwtTokenService.generateToken(authentifiedUser);
    //     if (!(authentifiedUser.getPrincipal() instanceof CustumUserDetailService)){ throw new IllegalAccessError("Erreur");}
    //     CustumUserDetailService userDetailService = (CustumUserDetailService) authentifiedUser.getPrincipal();
    //     List<String> roles = userDetailService
    //                             .getAuthorities()
    //                             .stream()
    //                             .map(GrantedAuthority::getAuthority)
    //                             .collect(Collectors.toList());

    //     // return ResponseEntity.ok(new tokenRequestDto(
    //     //     token, userDetailService.getUsername(), roles
    //     // ));
    // }

    @PostMapping("/singnup")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequestDto registration){

        Users user = usersRepository.findByUsername(registration.username())
        .orElseThrow(() -> new UsernameNotFoundException("Cet e-mail existe déjà"));

        if (user.getUsername() != null){
            return ResponseEntity.badRequest().body("Cet email est déjà inscrit");
        }

        Users
            .builder()
            .email(registration.email())
            .username(registration.username())
            .password(passwordEncoder.encode(registration.password()))
            .build();
        
        Set<String> strRoles = registration.roles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("pas de role"));
            roles.add(userRole);    
        }
        else {
            strRoles.forEach(
                role -> 
                    {switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("zut admin"));
                            roles.add(adminRole);
                            break;
                        case "vendeur":
                            Role vendeurRole = roleRepository.findByName(ERole.ROLE_VENDEUR)
                                .orElseThrow(() -> new RuntimeException("zut vendeur"));
                                roles.add(vendeurRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("zut utilisateur"));
                            roles.add(userRole);
                            break;
                    } } 
            );
        }

        user.setRoles(roles);
        usersRepository.save(user);

        return ResponseEntity.ok("Vous êtes inscrit!");

    }
    
}
