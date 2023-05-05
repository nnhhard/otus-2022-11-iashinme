package ru.iashinme.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;
import ru.iashinme.blog.model.Authority;
import ru.iashinme.blog.model.User;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private String fullName;

    private String email;

    private boolean enabled;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private List<Authority> authorities;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.accountNonLocked = true;
        this.authorities = List.of(user.getAuthority());
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .fullName(fullName)
                .enabled(enabled)
                .email(email)
                .authority(authorities.get(0))
                .build();
    }
}
