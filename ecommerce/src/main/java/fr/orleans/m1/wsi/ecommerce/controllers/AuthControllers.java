package fr.orleans.m1.wsi.ecommerce.controllers;

import fr.orleans.m1.wsi.ecommerce.dto.AuthResponse;
import fr.orleans.m1.wsi.ecommerce.dto.LoginRequest;
import fr.orleans.m1.wsi.ecommerce.dto.RegisterRequest;
import fr.orleans.m1.wsi.ecommerce.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class AuthControllers
{
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
