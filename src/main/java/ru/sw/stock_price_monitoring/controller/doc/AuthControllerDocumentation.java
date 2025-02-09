package ru.sw.stock_price_monitoring.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import ru.sw.stock_price_monitoring.dto.JwtAuthenticationResponse;
import ru.sw.stock_price_monitoring.dto.JwtRegisterResponse;
import ru.sw.stock_price_monitoring.dto.SignIn;
import ru.sw.stock_price_monitoring.dto.SignUp;
import ru.sw.stock_price_monitoring.dto.error.ErrorResponseDto;

@Tag(name = "Аутентификация")
public interface AuthControllerDocumentation {

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Успешная регистрация. Пользователь зарегистрирован и сохранен в базе данных.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JwtRegisterResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    ResponseEntity<JwtRegisterResponse> signUp(SignUp request);

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JwtAuthenticationResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Неверные данные для авторизации",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    ))
    })
    JwtAuthenticationResponse signIn(SignIn request);

    @Operation(summary = "Получение нового токена аутентификации")
    ResponseEntity<JwtAuthenticationResponse> refreshToken(
            HttpServletRequest request, HttpServletResponse response);
}
