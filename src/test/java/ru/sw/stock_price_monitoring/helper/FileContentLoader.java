package ru.sw.stock_price_monitoring.helper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FileContentLoader {
    private final FileContentHolder fileContentHolder;

    @PostConstruct
    public void loadFilesContent() throws IOException {
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:data/**/*.json");

        Map<String, String> filesContent = new HashMap<>();

        for (Resource resource : resources) {
            String content = new String(Files.readAllBytes(resource.getFile().toPath()));
            filesContent.put(resource.getFilename(), content);
        }

        fileContentHolder.setFilesContent(filesContent);
    }
}
