package ru.sw.stock_price_monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDto {
    @Schema(description = "Id пользователя",example = "87")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Nikolaev Igor")
    private String name;

    @Schema(description = "Адрес электронной почты пользователя", example = "batman@mail.ru")
    private String email;
}
