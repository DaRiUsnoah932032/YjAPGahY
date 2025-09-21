// 代码生成时间: 2025-09-22 03:47:34
package com.example.errorlogcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ErrorLogCollector {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLogCollector.class);

    public static void main(String[] args) {
        SpringApplication.run(ErrorLogCollector.class, args);
    }

    @Component
    class LogCollectorService {

        // 定义日志文件路径
        private String logFilePath = "logs/error_logs.txt";

        @PostConstruct
        public void collectErrorLogs() {
            try {
                // 读取日志文件内容
                List<String> allLines = Files.readAllLines(Paths.get(logFilePath));

                // 过滤错误日志
                allLines.stream()
                        .filter(line -> line.contains("ERROR"))
                        .forEach(line -> logger.error("Collected Error: " + line));
            } catch (IOException e) {
                logger.error("Error reading log file", e);
            }
        }
    }
}
