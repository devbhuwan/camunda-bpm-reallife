package camunda.api.swagger;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(ApiSwaggerProperties.class)
@EnableSwagger2
@Configuration
public class ApiSwaggerAutoConfiguration {

    @Autowired
    private ApiSwaggerProperties apiSwaggerProperties;

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(paths())
                .apis(RequestHandlerSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiSwaggerProperties.getTitle())
                .version(apiSwaggerProperties.getVersion())
                .license(apiSwaggerProperties.getLicense())
                .description(apiSwaggerProperties.getDescription())
                .build();
    }

    private Predicate<String> paths() {
        return regex(apiSwaggerProperties.getPaths());
    }

    @Bean
    @ConditionalOnMissingBean(UiConfiguration.class)
    public UiConfiguration uiConfiguration() {
        return new UiConfiguration(null);
    }

}
