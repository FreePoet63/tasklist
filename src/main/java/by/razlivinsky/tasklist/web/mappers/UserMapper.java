package by.razlivinsky.tasklist.web.mappers;

import by.razlivinsky.tasklist.domain.user.User;
import by.razlivinsky.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

/**
 * UserMapper interface
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * To dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
    UserDto toDto(User user);

    /**
     * To entity user.
     *
     * @param dto the dto
     * @return the user
     */
    User toEntity(UserDto dto);
}