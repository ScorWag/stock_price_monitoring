package ru.sw.stock_price_monitoring.helper;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class FileContentHolder {
    private Map<String, String> filesContent;
}
