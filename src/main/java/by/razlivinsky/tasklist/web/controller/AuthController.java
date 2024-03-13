package by.razlivinsky.tasklist.web.controller;

import by.razlivinsky.tasklist.domain.user.User;
import by.razlivinsky.tasklist.service.AuthService;
import by.razlivinsky.tasklist.service.UserService;
import by.razlivinsky.tasklist.web.dto.auth.JwtRequest;
import by.razlivinsky.tasklist.web.dto.auth.JwtResponse;
import by.razlivinsky.tasklist.web.dto.user.UserDto;
import by.razlivinsky.tasklist.web.dto.validation.OnCreate;
import by.razlivinsky.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController class handles authentication-related HTTP requests.
 *
 * @author razlivinsky
 * @since 11.03.2024
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    private final UserMapper userMapper;

    /**
     * Login with JWT credentials and generate a JWT response.
     *
     * @param jwtRequest the JWT login request
     * @return the JWT response containing access and refresh tokens
     */
    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest jwtRequest) {
        return authService.login(jwtRequest);
    }

    /**
     * Register a new user based on the provided UserDto.
     *
     * @param dto the UserDto containing user registration information
     * @return the UserDto of the registered user
     */
    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User createUser = userService.create(user);
        return userMapper.toDto(createUser);
    }

    /**
     * Refresh the access token using the provided refresh token.
     *
     * @param refreshToken the refresh token to generate a new access token
     * @return the JWT response with the refreshed access token
     */
    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}