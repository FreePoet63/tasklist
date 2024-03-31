package by.razlivinsky.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * JwtRequest class represents the request object for JWT authentication.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Data
@Schema(description = "Request for login")
public class JwtRequest {
    @Schema(description = "email", example = "smith190@gmail.com")
    @NotNull(message = "Username must be not null.")
    private String username;

    @Schema(description = "password", example = "0000")
    @NotNull(message = "Password must be not null.")
    private String password;
}