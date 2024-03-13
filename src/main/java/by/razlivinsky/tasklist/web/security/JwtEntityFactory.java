package by.razlivinsky.tasklist.web.security;

import by.razlivinsky.tasklist.domain.user.Role;
import by.razlivinsky.tasklist.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtEntityFactory class contains a method for creating JwtEntity objects based on User information.
 *
 * @author razlivinsky
 * @since 10.03.2024
 */
public class JwtEntityFactory {
    /**
     * Create a JwtEntity object based on the provided User object.
     *
     * @param user the User object from which to create the JwtEntity
     * @return the JwtEntity containing the user's information
     */
    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    /**
     * Converts a list of Role objects to a list of GrantedAuthority objects.
     *
     * @param roles the list of Role objects to be converted
     * @return a list of GrantedAuthority objects based on the provided Role objects
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}