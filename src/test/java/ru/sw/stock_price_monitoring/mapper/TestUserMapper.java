package ru.sw.stock_price_monitoring.mapper;

import org.mapstruct.Mapper;
import ru.sw.stock_price_monitoring.dto.SignUp;
import ru.sw.stock_price_monitoring.entity.User;

@Mapper(componentModel = "spring")
public interface TestUserMapper {
    SignUp toSignUp(User user);
}
