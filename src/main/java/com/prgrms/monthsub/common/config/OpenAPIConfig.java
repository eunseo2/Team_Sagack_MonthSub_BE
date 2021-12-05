package com.prgrms.monthsub.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.Arrays;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenAPIConfig {

    private static final String PROD = "prod";

    private final JwtConfig jwtConfig;

    private final Environment environment;

    public OpenAPIConfig(JwtConfig jwtConfig, Environment environment) {
        this.jwtConfig = jwtConfig;
        this.environment = environment;
    }

    @Bean
    public OpenApiCustomiser customOpenAPI(BuildProperties buildProperties) {
        return openAPI -> {
            if (!Arrays.asList(environment.getActiveProfiles()).contains(PROD)) {
                openAPI.info(new Info()
                        .title("MonthSub API").version(buildProperties.getVersion())
                        .termsOfService("Sagack")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                    )
                    .components(
                        openAPI.getComponents()
                            .addSecuritySchemes(
                                "bearer",
                                new SecurityScheme()
                                    .type(Type.HTTP)
                                    .scheme("bearer").bearerFormat("JWT")
                                    .in(SecurityScheme.In.HEADER).name(jwtConfig.getHeader())
                            )
                    )
                    .addSecurityItem(
                        new SecurityRequirement()
                            .addList("bearer", Arrays.asList("read", "write"))
                    );
            } else {
                openAPI.setComponents(new Components());
                openAPI.setPaths(new Paths());
            }
        };
    }

}
