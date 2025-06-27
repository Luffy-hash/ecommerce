package fr.orleans.m1.wsi.ecommerce.services.custumUsers;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.orleans.m1.wsi.ecommerce.models.Users;
import lombok.Getter;

@Getter
public class CustumUserDetailService implements UserDetails {

    private final static long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String username;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    
    public CustumUserDetailService(Long id, String email, String username, String password,
                                    Collection<? extends GrantedAuthority> authorities){
            this.id = id;
            this.email = email;
            this.username = username;
            this.password = password;
            this.authorities = authorities;
    }

    public static CustumUserDetailService build(Users userDto){
        List<GrantedAuthority> authorities = userDto.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getERoles().name()))
        .collect(Collectors.toList());
        return new CustumUserDetailService(userDto.getId(), 
                                        userDto.getEmail(), userDto.getUsername(), 
                                        userDto.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustumUserDetailService user = (CustumUserDetailService) o;
        return Objects.equals(id, user.id);
    }
    
}