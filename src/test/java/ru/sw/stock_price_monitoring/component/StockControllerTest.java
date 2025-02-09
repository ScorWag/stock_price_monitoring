package ru.sw.stock_price_monitoring.component;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sw.stock_price_monitoring.dao.specification.StockDataSpecification;
import ru.sw.stock_price_monitoring.dto.*;
import ru.sw.stock_price_monitoring.dto.mapper.StockDataMapper;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.StockData;
import ru.sw.stock_price_monitoring.entity.User;
import ru.sw.stock_price_monitoring.entity.User_;
import ru.sw.stock_price_monitoring.feign.dto.BarsDto;
import ru.sw.stock_price_monitoring.feign.dto.DailyInfoDto;
import ru.sw.stock_price_monitoring.helper.FileContentHolder;
import ru.sw.stock_price_monitoring.mapper.TestUserMapper;
import ru.sw.stock_price_monitoring.repository.TestStockDataRepository;
import ru.sw.stock_price_monitoring.repository.TestStockRepository;
import ru.sw.stock_price_monitoring.repository.TestUserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sw.stock_price_monitoring.constants.AuthenticationConstants.BEARER_PREFIX;
import static ru.sw.stock_price_monitoring.constants.AuthenticationConstants.HEADER_AUTHORIZATION;
import static ru.sw.stock_price_monitoring.constants.ControllerConstants.BASE_URL_AGGREGATES_BARS;
import static ru.sw.stock_price_monitoring.constants.ControllerConstants.URI_SEPARATOR;
import static ru.sw.stock_price_monitoring.constants.TestControllerConstants.*;


public class StockControllerTest extends BaseComponentTest {

    @Autowired
    private FileContentHolder fileContentHolder;

    @Autowired
    private TestUserRepository userRepository;

    @Autowired
    private TestStockDataRepository stockDataRepository;

    @Autowired
    private TestStockRepository stockRepository;

    @Autowired
    private TestUserMapper userMapper;

    @Autowired
    private StockDataMapper stockDataMapper;

    @Value("${client.multiplier}")
    private String multiplier;

    @Value("${client.timespan}")
    private String timespan;

    private Long userId;

    private String jwt;

    @BeforeEach
    void setUp() throws Exception {

        SignUp requestBody = objectMapper.readValue(
                fileContentHolder.getFilesContent().get("testUser.json"),
                SignUp.class
        );

        String response = mockMvc.perform(
                        post(BASE_URL_USER_CONTROLLER + "register")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JwtRegisterResponse jwtRegisterResponse = objectMapper.readValue(response, JwtRegisterResponse.class);
        userId = jwtRegisterResponse.getUser().getId();
        jwt = jwtRegisterResponse.getToken();

        User savedUser = userRepository.findById(userId).orElseThrow();

        assertThat(userMapper.toSignUp(savedUser))
                .usingRecursiveComparison()
                .ignoringFields(User_.PASSWORD)
                .isEqualTo(requestBody);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteById(userId);
    }

    @Test
    void shouldGetUserStockInfo() throws Exception {
        String ticker = "AMZN";
        Stock stock = stockRepository.findByTicker(ticker);
        List<StockData> testStockDataList = objectMapper.readValue(fileContentHolder.getFilesContent().get("testStockData.json"),
                new TypeReference<List<StockData>>(){});

        for (StockData stockData : testStockDataList) {
            stockData.setStock(stock);
        }
        stockDataRepository.saveAll(testStockDataList);

        User user = userRepository.findById(userId).orElseThrow();
        for (StockData stockData : testStockDataList) {
            user.addStockData(stockData);
        }
        userRepository.save(user);

        String responseJson = mockMvc.perform(
                MockMvcRequestBuilders.get(BASE_URL_STOCK_CONTROLLER + "saved")
                        .param("ticker", ticker)
                        .header(HEADER_AUTHORIZATION, BEARER_PREFIX + jwt))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Map<String, List<StockDataDto>> expectedStockData = new HashMap<>();
        expectedStockData.put(ticker, stockDataMapper.toStockDataDtoList(testStockDataList));
        StockDto expected =  StockDto.builder()
                .userId(userId)
                .data(expectedStockData)
                .build();
        StockDto actual = objectMapper.readValue(responseJson, StockDto.class);

        assertEquals(expected, actual);
    }

    @Test
    void shouldSaveStockInfo() throws Exception {
        String request = fileContentHolder.getFilesContent().get("testStockInfoRangeRequest.json");
        String response = fileContentHolder.getFilesContent().get("testStockInfoRangeResponse.json");
        String requestUrlAggregateBars = generateUrlAggregatesBars(
                objectMapper.readValue(request, StockInfoRequest.class)
        );

        stubFor(get(urlEqualTo(requestUrlAggregateBars))
                .willReturn(
                        aResponse()
                                .withBody(response)
                                .withHeader("Content-Type", "application/json"))
        );

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL_STOCK_CONTROLLER + "save")
                        .header(HEADER_AUTHORIZATION, BEARER_PREFIX + jwt)
                .contentType(APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated());

        List<DailyInfoDto> expected = objectMapper.readValue(response, BarsDto.class).getResults();
        List<DailyInfoDto> actual = stockDataMapper.toDailyInfoDtoList(stockDataRepository.findAll(
                StockDataSpecification.byUser(userRepository.getReferenceById(userId)))
        );

        assertIterableEquals(expected, actual);
    }

    private String generateUrlAggregatesBars(StockInfoRequest stockInfoRequest) {
        return BASE_URL_AGGREGATES_BARS +
                stockInfoRequest.getTicker() + URI_SEPARATOR +
                "range" + URI_SEPARATOR +
                multiplier + URI_SEPARATOR +
                timespan + URI_SEPARATOR +
                stockInfoRequest.getStart() + URI_SEPARATOR + stockInfoRequest.getEnd();
    }
}
