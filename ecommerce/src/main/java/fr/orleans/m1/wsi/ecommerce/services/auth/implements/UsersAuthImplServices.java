import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.orleans.m1.wsi.ecommerce.dto.RoleRequestDto;
import fr.orleans.m1.wsi.ecommerce.dto.UserRequestDto;
import fr.orleans.m1.wsi.ecommerce.models.Users;
import fr.orleans.m1.wsi.ecommerce.repositories.UsersRepository;
import fr.orleans.m1.wsi.ecommerce.services.auth.UsersAuthServices;

@Service
public class UsersAuthImplServices implements UsersAuthServices {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersAuthImplServices(
        UsersRepository usersRepository,
        PasswordEncoder passwordEncoder
    ){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createUser(UserRequestDto userRequestDto) {
        // on test pour voir si notre utilisateur existe dans la bd
        Users users = usersRepository.findByUsername(userRequestDto.username())
            .orElseThrow(() -> new UsernameNotFoundException("Cet utilisateur existe déjà"));

        users = Users
                    .builder()
                    .email(userRequestDto.email())
                    .username(userRequestDto.username())
                    .username(passwordEncoder.encode(userRequestDto.password()))
                    .phoneNumber(userRequestDto.phoneNumber())
                    .build();
        usersRepository.save(users);
        return "ok";
    }

    @Override
    public String deleteUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public String createRole(RoleRequestDto roleRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRole'");
    }

    @Override
    public String roleToUser(UserRequestDto username, RoleRequestDto role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'roleToUser'");
    }
    
}
