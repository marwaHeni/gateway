package com.mapsit.jamaity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    private final StorageProperties storageProps;

    public WebConfig(StorageProperties storageProps) {
        this.storageProps = storageProps;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:/home/med/Pictures/");*/

        String path = storageProps.getPath();
        // do your stuff here
        System.out.println(path);


        String realPath = path.substring(7,path.length());
        System.out.println(realPath);

       /* registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:/home/med/Documents/workspace/angular+springboot/last/micro-service - gatwa+micro avec jwt - services/service-company/service-company/compaktorImages/");*/
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:"+realPath+"/");
    }


    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US); // Set default Locale as US
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");  // name of the resource bundle
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }
}
