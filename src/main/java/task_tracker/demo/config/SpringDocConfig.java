package task_tracker.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        Server productionServer = new Server()
                .url("https://task.164-132-187-235.sslip.io")
                .description("Servidor de Producción VPS");

        return new OpenAPI()
                .servers(List.of(productionServer))
                .info(new Info()
                        .title("Task Tracker API")
                        .version("1.0")
                        .description("Documentación de endpoints del Task Tracker"));
    }
}