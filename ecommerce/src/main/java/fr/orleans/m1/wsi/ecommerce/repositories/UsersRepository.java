package fr.orleans.m1.wsi.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orleans.m1.wsi.ecommerce.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    
    List<Users> findByRole(String role); // List users by role

    void deleteById(Integer id);
    
}
