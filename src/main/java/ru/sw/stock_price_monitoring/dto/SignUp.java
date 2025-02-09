package ru.sw.stock_price_monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@Schema(description = "Запрос на регистрацию")
public class SignUp {

  @Schema(description = "Имя пользователя", example = "Nikolaev Igor")
  @Size(min = 5, max = 50, message = "{validation-size}")
  @NotBlank(message = "{validation-not-blank}")
  private String name;

  @Schema(description = "Адрес электронной почты", example = "batman@mail.ru")
  @Size(min = 5, max = 255, message = "{validation-size}")
  @NotBlank(message = "{validation-not-blank}")
  @Email(message = "{validation-email}")
  private String email;

  @Schema(description = "Пароль", example = "D9{:uk76`K6Z")
  @Size(min = 8, max = 255, message = "{validation-size}")
  @NotBlank(message = "{validation-not-blank}")
  private String password;
}

