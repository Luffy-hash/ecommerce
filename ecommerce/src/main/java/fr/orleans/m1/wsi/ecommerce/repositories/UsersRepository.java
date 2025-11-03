package fr.orleans.m1.wsi.ecommerce.repositories;

import fr.orleans.m1.wsi.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>
{
    Optional<User> findUserByEmail(String email);
}
