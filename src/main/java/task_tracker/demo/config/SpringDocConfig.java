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
        // Forzamos que el servidor apunte a tu URL de Render con HTTPS nativo
        Server productionServer = new Server()
                .url("https://task-tracker-backend-xjox.onrender.com")
                .description("Servidor de Producción en Render");

        return new OpenAPI()
                .servers(List.of(productionServer))
                .info(new Info()
                        .title("Task Tracker API")
                        .version("1.0")
                        .description("Documentación de endpoints del Task Tracker"));
    }
}