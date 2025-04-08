package com.damao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

/**
 * @author DM
 * @since 2023/10/07
 */
@EnableIntegration
@SpringBootApplication(scanBasePackages = "com.damao")
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run();
        log.info("服务启动╰(*°▽°*)╯");
    }
}
