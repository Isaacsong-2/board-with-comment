package com.sparta.boardwithcomment.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig{

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Study Community API")
                .version("v1.0.0")
                .description("스터디 모집을 위한 뉴스피드 프로젝트 Study Community 의 API 문서 입니다.");

        SecurityScheme auth = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("basicAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicAuth", auth))
                .addSecurityItem(securityRequirement)
                .info(info);
    }

//    @Bean
//    public OperationCustomizer customize() {
//        return (Operation operation, HandlerMethod handlerMethod) -> {
//            DisableSwaggerSecurity methodAnnotation =
//                    handlerMethod.getMethodAnnotation(DisableSwaggerSecurity.class);
//            if (methodAnnotation != null) {
//                operation.setSecurity(Collections.emptyList());
//            }
//            return operation;
//        };
//    }

}
