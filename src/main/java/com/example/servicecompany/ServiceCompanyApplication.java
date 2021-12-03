package com.example.servicecompany;

import com.example.servicecompany.config.apidoc.ClassInit;
import com.example.servicecompany.domain.User;
import com.example.servicecompany.repository.*;
import com.example.servicecompany.security.jwt.MyWayProperties;
import com.example.servicecompany.storage.FilesStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableDiscoveryClient //on dit au micro-service qd tu demarre publie ta reference ds l'annuaire(sans cette annotation, le service d'enregistrement ne peut detecter aucune instance de ce micro-service:il a trouver zero instance)
@EnableZuulProxy //pour activer le proxy
@EnableDiscoveryClient //pour qu'il publie son nom ds l'annuaire
@SpringBootApplication
@EnableConfigurationProperties({MyWayProperties.class})
@EnableSwagger2 // swagger va scan voter projet et génére une documentaion pour votre API
@EnableScheduling
public class ServiceCompanyApplication implements CommandLineRunner{

    @Autowired
    ClassInit classInit;


    public static void main(String[] args) {
        SpringApplication.run(ServiceCompanyApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {

        classInit.initialize();
    }
}
