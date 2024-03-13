package by.razlivinsky.tasklist.web.dto.auth;

import lombok.Data;

/**
 * JwtResponse class represents the response object for JWT authentication, containing user identification details and tokens.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Data
public class JwtResponse {
    private Long id;
    private String username;
    private String accessToken;
    private String refreshToken;
}