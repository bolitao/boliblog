package xyz.bolitao.boliblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author bolitao
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.appInfo())
                .select()
                // .apis(RequestHandlerSelectors.basePackage("xyz.bolitao.java_starter.web"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo appInfo() {
        return new ApiInfoBuilder()
                .title("test api")
                .contact(new Contact(
                        "bolitao",
                        "https://bolitao.xyz",
                        "tblsyx@outlook.com"))
                .description("api description")
                .version("1.0.0")
                .build();
    }
}
