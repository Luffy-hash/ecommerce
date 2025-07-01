package fr.orleans.m1.wsi.ecommerce.services.custumUsers;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.orleans.m1.wsi.ecommerce.models.Users;
import fr.orleans.m1.wsi.ecommerce.repositories.UsersRepository;
import jakarta.transaction.Transactional;

@Service
public class CustumUsersService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustumUsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("utilisatuer non trouvé: " + email));
        
        return CustumUserDetailService.build(user);
        
        
    }

    
}
