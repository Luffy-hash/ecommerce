package fr.orleans.m1.wsi.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.orleans.m1.wsi.ecommerce.models.ERole;
import fr.orleans.m1.wsi.ecommerce.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole eRoles);
}
