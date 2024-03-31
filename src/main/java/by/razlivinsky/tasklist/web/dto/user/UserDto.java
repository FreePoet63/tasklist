package by.razlivinsky.tasklist.web.dto.user;

import by.razlivinsky.tasklist.web.dto.validation.OnCreate;
import by.razlivinsky.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * UserDto class represents the data transfer object for users, containing user-related information.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Data
@Schema(description = "User DTO")
public class UserDto {
    @Schema(description = "User Id", example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User name", example = "John Smith")
    @NotNull(message = "Name must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Schema(description = "User email", example = "smith190@gmail.com")
    @NotNull(message = "Username must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Schema(description = "User crypted password", example = "$2a$10$ddAw.G41GPWHNPEgeSrl0uGELmjR4880vTXs2aLj2gCr/xUHltfam")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(description = "User password confirmation", example = "$2a$10$ddAw.G41GPWHNPEgeSrl0uGELmjR4880vTXs2aLj2gCr/xUHltfam")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null.", groups = {OnCreate.class, OnUpdate.class})
    private String passwordConfirmation;
}