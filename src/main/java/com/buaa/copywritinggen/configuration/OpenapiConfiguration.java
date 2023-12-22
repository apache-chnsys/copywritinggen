package com.buaa.copywritinggen.configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Author jiangxintian
 * @Date 2023/11/7 14:23
 * @PackageName:com.buaa.copywritinggen.configuration
 * @ClassName: Swagger2Configuration
 * @Description: 如有bug，吞粪自尽
 */
@Configuration
public class OpenapiConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Your API Title")
                        .version("1.0.0")
                        .description("Your API Description"));
    }
}
