package ru.sw.stock_price_monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignIn {

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

