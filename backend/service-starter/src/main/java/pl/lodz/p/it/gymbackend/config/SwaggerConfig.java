package pl.lodz.p.it.gymbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Class responsible for configuring swagger documentation.
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Value("${api.yaml.source.code.filepath}")
    private String yamlSourceCodeFilepath;

    /**
     * Method responsible for providing swagger resources.
     *
     * @return SwaggerResourceProvider object
     */
    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            SwaggerResource wsResource = new SwaggerResource();
            wsResource.setName("Documentation");
            wsResource.setSwaggerVersion("3.0");
            wsResource.setLocation(yamlSourceCodeFilepath);
            return List.of(wsResource);
        };
    }

    /**
     * Method responsible for configuring Docket.
     *
     * @return Docket object
     */
    @Bean
    public Docket get() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}

