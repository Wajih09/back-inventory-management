package com.tech.gestiondestock.config;

//import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*import springfox.documentation.builders.ApiInfoBuilder; //videos
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;*/

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

import static com.tech.gestiondestock.utils.Constants.APP_ROOT;

@Configuration
@EnableSwagger2 //v16 min 22 on veut activer swagger
public class SwaggerConfiguration {

    public SwaggerConfiguration() {
        System.out.println("SwaggerConfiguration instantiated");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("SwaggerConfiguration postConstruct");
    }
    //@Bean
    public Docket api(){ //suivant video alibou et https://www.youtube.com/watch?v=BVLhVCX7byY
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                                .description("Gestion de stock API documentation")
                                .title("Gestion de stock REST API")
                                .build()
                )
                .groupName("REST API V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tech.gestiondestock"))//dire a swagger c quoi le package de base de notre application
                .paths(PathSelectors.ant(APP_ROOT + "/**")) // cad /peut importe ou PathSelectors.any()
                .build();

    }
//    public GroupedOpenApi api(){ //suivant migration vers openAPI
//        return GroupedOpenApi.builder()
//                .group("REST API V1")
//                .pathsToMatch(APP_ROOT + "/**")
//                .build();
//    }
}
