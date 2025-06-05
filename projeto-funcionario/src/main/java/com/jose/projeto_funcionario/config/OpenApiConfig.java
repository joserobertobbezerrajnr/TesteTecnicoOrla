package com.jose.projeto_funcionario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Projetos e Funcionários")
                        .version("1.0")
                        .description("API RESTful para gerenciar projetos e seus respectivos funcionários."));
    }
}
