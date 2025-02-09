package ru.sw.stock_price_monitoring.service.prepare.request;

import org.springframework.stereotype.Service;
import ru.sw.stock_price_monitoring.entity.Stock;
import ru.sw.stock_price_monitoring.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class PrepareRequestForClientFacade {
    private final PrepareRequestForClient entry;

    public PrepareRequestForClientFacade(List<PrepareRequestForClient> prepareFilters) {
        Map<PrepareType, PrepareRequestForClient> prepares = prepareFilters.stream()
                .collect(toMap(PrepareRequestForClient::getType, identity()));

        PrepareRequestForClient userData = prepares.get(PrepareType.FILTER_USER_DATA);
        PrepareRequestForClient dbData = prepares.get(PrepareType.FILTER_AND_SAVE_DB_DATA);
        PrepareRequestForClient notFoundDbData = prepares.get(PrepareType.FILTER_NOT_FOUND_DB_DATA);

        userData.setNext(dbData);
        dbData.setNext(notFoundDbData);
        this.entry = userData;
    }

    public boolean prepareRequest(User user, Stock stock, List<LocalDate> dates) {
        return this.entry.prepare(user, stock, dates);
    }
}
