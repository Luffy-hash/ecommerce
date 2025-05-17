package fr.orleans.m1.wsi.ecommerce.controllers.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.orleans.m1.wsi.ecommerce.services.auth.UsersAuthServices;

@RestController
@RequestMapping("/admin/auth")
public class AuthController {
    private final UsersAuthServices authService;

    public AuthController(UsersAuthServices authService) {
        this.authService = authService;
    }

    
}
