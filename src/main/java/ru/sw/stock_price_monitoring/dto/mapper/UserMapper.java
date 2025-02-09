package ru.sw.stock_price_monitoring.dto.mapper;

import org.mapstruct.Mapper;
import ru.sw.stock_price_monitoring.dto.UserDto;
import ru.sw.stock_price_monitoring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
}
