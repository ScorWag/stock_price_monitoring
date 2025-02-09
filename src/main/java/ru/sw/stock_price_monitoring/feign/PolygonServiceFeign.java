package ru.sw.stock_price_monitoring.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sw.stock_price_monitoring.config.FeignClientConfiguration;
import ru.sw.stock_price_monitoring.feign.dto.BarsDto;
import ru.sw.stock_price_monitoring.feign.dto.CertainDateStockInfoDto;


@FeignClient(
        name = "${client.name}",
        url = "${client.base-url}",
        configuration = FeignClientConfiguration.class
)
public interface  PolygonServiceFeign {
    @GetMapping(path = "/v1/open-close/{stocksTicker}/{date}")
    CertainDateStockInfoDto getCertainDateStockInfo(
            @PathVariable("stocksTicker") String stocksTicker,
            @PathVariable("date") String date
    );

    @GetMapping(
            path = "/v2/aggs/ticker/{stocksTicker}/range/" +
                    "${client.multiplier}/" +
                    "${client.timespan}/" +
                    "{from}/{to}")
    BarsDto getBars(
            @PathVariable("stocksTicker") String stocksTicker,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    );

}
