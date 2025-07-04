package fr.orleans.m1.wsi.ecommerce.services;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import fr.orleans.m1.wsi.ecommerce.models.Users;
import fr.orleans.m1.wsi.ecommerce.repositories.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users createUser(Users user) {
        Long compteur = 0L;

        if (userExistsByEmail(user.getEmail()) || userExistsByUsername(user.getUsername())) {
            throw new UsernameNotFoundException("Cet utilisateur existe déjà."); // User already exists
        }

        // Set default values for count and role
        user.setCount(compteur++);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setRoles(user.getRoles() != null ? user.getRoles() : Set.of(new Role("ROLE_USER"))); // Default role is USER

        // Save the user to the database
        return usersRepository.save(user);
    }

    public Users getUserById(Long id) { 
        return usersRepository.findById(id).orElseThrow(() -> {
            throw new UsernameNotFoundException("Utilisateur non trouvé."); // User not found
        });
    }

    public List<Users> getAllUsers() {
        // List all users
        if (usersRepository.findAll().isEmpty()) { 
            throw new UsernameNotFoundException("Aucun utilisateur trouvé."); // No users found
        }
        return usersRepository.findAll();
    }

    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow();
    }

    public Users getUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("Utilisateur non trouvé."); // User not found
        });
    }

    public boolean userExistsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    public boolean userExistsByUsername(String username) {
        return usersRepository.existsByUsername(username);
    }

    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }

    public Users updateUser(Users user) {
        Users existingUser = usersRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) { 
            existingUser.setEmail(user.getEmail());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser.setRoles(user.getRoles());
            existingUser.setAddress(user.getAddress());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            return usersRepository.save(existingUser);
        }
        return null;
    }

    public List<Users> getUsersByRole(String role) {
        return usersRepository.findByRole(role);
    }
    
    
}
