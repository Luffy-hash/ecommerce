package fr.orleans.m1.wsi.ecommerce.services;

import fr.orleans.m1.wsi.ecommerce.dto.AuthResponse;
import fr.orleans.m1.wsi.ecommerce.dto.LoginRequest;
import fr.orleans.m1.wsi.ecommerce.dto.RegisterRequest;
import fr.orleans.m1.wsi.ecommerce.models.User;
import fr.orleans.m1.wsi.ecommerce.repositories.UsersRepository;
import fr.orleans.m1.wsi.ecommerce.security.JwtUtilise;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilise jwtUtilise;

    /**
     * Enregistrer un client
     */
    public AuthResponse register(RegisterRequest request){
        if (usersRepository.findUserByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Ce utilisateur existe déjà!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAdresse());
        user.setPhone(request.getPhone());

        user = usersRepository.save(user); // on register ici notre user

        String token = jwtUtilise.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                new AuthResponse.UserDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName()
                )
        );
    }

    /**
     * Login utilisateur
     */
    public AuthResponse login(LoginRequest request){
        User user = usersRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Invalid Credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credential");
        }

        String token = jwtUtilise.generateToken(user.getEmail());
        return new AuthResponse(
                token,
                new AuthResponse.UserDTO(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName()
                )
        );
    }
}
