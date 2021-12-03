package com.example.servicecompany.config;

import com.example.servicecompany.gateway.accesscontrol.AccessControlFilter;
import com.example.servicecompany.security.jwt.MyWayProperties;

import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

//    @Configuration
//    public static class SwaggerBasePathRewritingConfiguration {
//
//        @Bean
//        public SwaggerBasePathRewritingFilter swaggerBasePathRewritingFilter() {
//            return new SwaggerBasePathRewritingFilter();
//        }
//    }

    @Configuration
    public static class AccessControlFilterConfiguration {

        @Bean
        public AccessControlFilter accessControlFilter(RouteLocator routeLocator, MyWayProperties myWayProperties) {
            return new AccessControlFilter(routeLocator, myWayProperties);
        }
    }

}
