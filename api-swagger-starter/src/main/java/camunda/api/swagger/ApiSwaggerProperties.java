package camunda.api.swagger;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static camunda.api.swagger.ApiSwaggerProperties.SWAGGER_PREFIX;

@ConfigurationProperties(prefix = SWAGGER_PREFIX)
@Data
@Validated
public class ApiSwaggerProperties {

    public static final String SWAGGER_PREFIX = "api.swagger";

    @Value("${spring.application.name:''}")
    private String appName;
    private String title = appName;

    private String description = "";

    private String version = "";

    private String paths = "/api.*";

    private String license = "Apache License Version 2.0";

}