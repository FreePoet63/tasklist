package by.razlivinsky.tasklist.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * JwtRequest class represents the request object for JWT authentication.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Data
public class JwtRequest {
    @NotNull(message = "Username must be not null.")
    private String username;
    @NotNull(message = "Password must be not null.")
    private String password;
}