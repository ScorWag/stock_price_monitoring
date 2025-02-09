package ru.sw.stock_price_monitoring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sw.stock_price_monitoring.controller.doc.StockControllerDocumentation;
import ru.sw.stock_price_monitoring.dto.StockDto;
import ru.sw.stock_price_monitoring.dto.StockInfoRequest;
import ru.sw.stock_price_monitoring.dto.mapper.StockDataMapper;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.User;
import ru.sw.stock_price_monitoring.service.*;
import ru.sw.stock_price_monitoring.service.prepare.request.PrepareRequestForClientFacade;
import ru.sw.stock_price_monitoring.service.request.RequestStockDataService;
import ru.sw.stock_price_monitoring.util.DataContainer;
import ru.sw.stock_price_monitoring.util.DateUtil;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stock")
public class StockController implements StockControllerDocumentation {
    private final StockDataService stockDataService;
    private final UserService userService;
    private final StockDataMapper mapper;
    private final PrepareRequestForClientFacade prepareRequestService;
    private final StockService stockService;
    private final RequestStockDataService requestStockDataService;
    private final StockNotFoundDataService stockNotFoundDataService;

    @GetMapping("/saved")
    @PreAuthorize("hasAuthority(T(ru.sw.stock_price_monitoring.entity.Permission$PermissionConstant)" +
            ".GET_USER_STOCK_INFO_PERMISSION)")
    public ResponseEntity<StockDto> getUserStockInfo(@RequestParam(name = "ticker", required = false) String ticker) {
        User currentUser = userService.getCurrentUser();

        return new ResponseEntity<>(
                StockDto.builder()
                        .userId(currentUser.getId())
                        .data(mapper.toStockDataDtoMap(
                                stockDataService.getStockDataByUserAndTicker(
                                        currentUser,
                                        ticker))
                        )
                        .build(),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority(T(ru.sw.stock_price_monitoring.entity.Permission$PermissionConstant)" +
            ".SAVE_STOCK_INFO_REQUEST_PERMISSION)")
    public ResponseEntity<Void> saveStockInfoRequest(@Valid @RequestBody StockInfoRequest stockInfoRequest) {
        Stock stock = stockService.findByTicker(stockInfoRequest.getTicker());
        User currentUser = userService.getCurrentUser();
        List<LocalDate> stockInfoRequestDates = DateUtil.generateDateListOfRange(
                LocalDate.parse(stockInfoRequest.getStart()),
                LocalDate.parse(stockInfoRequest.getEnd()));

        prepareRequestService.prepareRequest(currentUser, stock, stockInfoRequestDates);

        DataContainer dataContainer = requestStockDataService.getStockData(stock, stockInfoRequestDates);

        userService.addStockDataList(currentUser, stockDataService.saveAll(dataContainer.getStockDataList()));
        stockNotFoundDataService.saveAll(dataContainer.getStockNotFoundDataList());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
