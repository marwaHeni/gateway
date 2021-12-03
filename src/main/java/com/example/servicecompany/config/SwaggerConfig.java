package com.example.servicecompany.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {


    @Bean
    public Docket api() {
        //Docket est l'objet de swagger qui contient toutes les configurations et qui nous donnera accès a differents méthodes pour filtré ce qui sera
        //documenté
        return new Docket(DocumentationType.SWAGGER_2)
                .host("localhost")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.servicecompany")) //la méthode api va permettre  de filter selon des prédicats (en demande de documenté tout ce qui dans la pk)
                .paths(PathSelectors.any()) //filter votre URI
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
