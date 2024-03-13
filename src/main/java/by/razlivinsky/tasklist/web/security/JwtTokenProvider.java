package by.razlivinsky.tasklist.web.security;

import by.razlivinsky.tasklist.domain.exception.AccessDeniedException;
import by.razlivinsky.tasklist.domain.user.Role;
import by.razlivinsky.tasklist.domain.user.User;
import by.razlivinsky.tasklist.service.UserService;
import by.razlivinsky.tasklist.service.props.JwtProperties;
import by.razlivinsky.tasklist.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JwtTokenProvider class handles the creation and validation of JWT tokens for authentication.
 *
 * @author razlivinsky
 * @since 10.03.2024
 */
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private Key key;

    /**
     * Initializes the JwtTokenProvider by setting the key using the specified secret from jwtProperties.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    /**
     * Creates an access token using the provided user information and roles.
     *
     * @param userId   the user id for the token
     * @param username the username for the token
     * @param roles    the roles associated with the user
     * @return the generated access token
     */
    public String createAccessToken(Long userId, String username, Set<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        claims.put("roles", resolveRoles(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * Resolves the role names from the provided set of roles.
     *
     * @param roles the set of roles from which to resolve the role names
     * @return a list of role names derived from the provided set of roles
     */
    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    /**
     * Creates a refresh token using the provided user information.
     *
     * @param userId   the user id for the token
     * @param username the username for the token
     * @return the generated refresh token
     */
    public String createRefreshToken(Long userId, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * Refreshes the user tokens based on the provided refresh token.
     *
     * @param refreshToken the refresh token to validate and use for token refresh
     * @return the updated JWT response containing new access and refresh tokens
     */
    public JwtResponse refreshUserTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if(!validateToken(refreshToken)) {
            throw new AccessDeniedException();
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(createAccessToken(userId, user.getUsername(), user.getRoles()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, user.getUsername()));
        return jwtResponse;
    }

    /**
     * Validates the provided token.
     *
     * @param token the token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    /**
     * Retrieves the user ID from the provided token.
     *
     * @param token the token from which to retrieve the user ID
     * @return the user ID extracted from the token
     */
    private String getId(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
    }

    /**
     * Retrieves the username from the provided token.
     *
     * @param token the token from which to retrieve the username
     * @return the username extracted from the token
     */
    private String getUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Retrieves the user authentication details based on the provided token.
     *
     * @param token the token from which to retrieve user authentication details
     * @return the user authentication details
     */
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}