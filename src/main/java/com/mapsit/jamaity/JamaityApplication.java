package com.mapsit.jamaity;

import com.mapsit.jamaity.security.jwt.MyWayProperties;
import com.mapsit.jamaity.storage.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableDiscoveryClient //on dit au micro-service qd tu demarre publie ta reference ds l'annuaire(sans cette annotation, le service d'enregistrement ne peut detecter aucune instance de ce micro-service:il a trouver zero instance)
@SpringBootApplication
@EnableConfigurationProperties({MyWayProperties.class})
@EnableSwagger2 // swagger va scan voter projet et génére une documentaion pour votre API
@ComponentScan("com.mapsit.jamaity")
public class JamaityApplication implements CommandLineRunner {

    @Autowired
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(JamaityApplication.class, args);


    }

    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }


    @Override
    public void run(String... args) throws Exception {

        storageService.init();
    }
}