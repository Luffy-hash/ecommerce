package fr.orleans.m1.wsi.ecommerce.services.auth;

import fr.orleans.m1.wsi.ecommerce.dto.RoleRequestDto;
import fr.orleans.m1.wsi.ecommerce.dto.UserRequestDto;

public interface UsersAuthServices {

    public String createUser(UserRequestDto userRequestDto); // créer un utilisateur

    public String deleteUser(); // supprimer un utilisateur

    public String createRole(RoleRequestDto roleRequestDto); // créer un role

    public String roleToUser(UserRequestDto username, RoleRequestDto role); // associé un role à un utilisateur
    
}