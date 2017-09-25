package api.swagger;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiSwaggerAutoConfigurationTest.TestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.application.name=sampleSwagger",
        "api.swagger.title=API Starter Swagger",
        "api.swagger.description=API Swagger",
        "api.swagger.version=1.0",
        "api.swagger.paths=/rest.*|/api.*",
        "api.swagger.license: Apache License Version 2.0"
})
public class ApiSwaggerAutoConfigurationTest {

    @SpringBootApplication
    @ComponentScan(basePackageClasses = ApiSwaggerAutoConfigurationTest.class)
    static class TestConfiguration {
        public static void main(String[] args) {
            SpringApplication.run(TestConfiguration.class, args);
        }
    }


}