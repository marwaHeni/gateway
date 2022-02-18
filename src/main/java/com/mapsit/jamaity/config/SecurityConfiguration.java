package com.mapsit.jamaity.config;

import com.mapsit.jamaity.security.AuthoritiesConstants;
import com.mapsit.jamaity.security.jwt.JWTConfigurer;
import com.mapsit.jamaity.security.jwt.TokenProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
//import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    //private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(TokenProvider tokenProvider) {  //,, SecurityProblemSupport problemSupport
        this.tokenProvider = tokenProvider;
     //   this.problemSupport = problemSupport;
    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers("/h2-console/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
            .disable()
            .exceptionHandling()
//                .authenticationEntryPoint(problemSupport)
//                .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
        .and()
            .frameOptions()
            .deny()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/associations/lang/**").permitAll()
            .antMatchers("/api/associations/search/**").permitAll()
            .antMatchers("/api/ptfs/search/**").permitAll()
            .antMatchers("/api/ptfs/lang/**").permitAll()
            .antMatchers("/api/sub-structure-associations/lang/**").permitAll()
            .antMatchers("/api/sub-domains/lang/**").permitAll()
            .antMatchers("/api/articles/**").hasAnyAuthority(AuthoritiesConstants.STOCK, AuthoritiesConstants.PURCHASE, AuthoritiesConstants.SALE)
            .antMatchers("/api/marks/**").hasAuthority(AuthoritiesConstants.STOCK)
            .antMatchers("/api/movements/**").hasAuthority(AuthoritiesConstants.STOCK)
            .antMatchers("/api/inventories/**").hasAuthority(AuthoritiesConstants.STOCK)
            .antMatchers("/api/inventory-items/**").hasAuthority(AuthoritiesConstants.STOCK)
             .antMatchers("/api/items/validateCommande/**").hasAuthority(AuthoritiesConstants.EshopClient)
            .antMatchers("/api/items/**").hasAnyAuthority(AuthoritiesConstants.STOCK, AuthoritiesConstants.PURCHASE, AuthoritiesConstants.SALE)
            .antMatchers("/api/variants/**").hasAuthority(AuthoritiesConstants.STOCK)
            .antMatchers("/api/variant-values/**").hasAuthority(AuthoritiesConstants.STOCK)
            .antMatchers("/api/pivvs/**").hasAuthority(AuthoritiesConstants.STOCK)
            .antMatchers("/api/tva-configs/**").hasAnyAuthority(AuthoritiesConstants.STOCK, AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/tax-articles/**").hasAnyAuthority(AuthoritiesConstants.STOCK, AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/price-categories/**").hasAnyAuthority(AuthoritiesConstants.STOCK, AuthoritiesConstants.PURCHASE, AuthoritiesConstants.CompanyOwner)
            .antMatchers("/api/depots/**").hasAuthority(AuthoritiesConstants.STOCK)
            .antMatchers("/api/bill-operations/**").hasAnyAuthority(AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/bill-operation-items/**").hasAnyAuthority(AuthoritiesConstants.SALE,AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/bases-tva/**").hasAnyAuthority(AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/commercial-registers/**").hasAnyAuthority(AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/tiers/**").hasAnyAuthority(AuthoritiesConstants.SALE,AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/tiers-locations/**").hasAnyAuthority(AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/tax-invoices/**").hasAnyAuthority(AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/invoice-payment-items/**").hasAnyAuthority(AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
            .antMatchers("/api/statistics/**").hasAnyAuthority(AuthoritiesConstants.SALE, AuthoritiesConstants.PURCHASE)
//            .antMatchers("/api/dashboard/**").hasAuthority(AuthoritiesConstants.DASHBOARD)
//            .antMatchers("/api/journals/**").hasAuthority(AuthoritiesConstants.DASHBOARD)
            .antMatchers("/api/company-configs/**").hasAnyAuthority(AuthoritiesConstants.CompanyOwner, AuthoritiesConstants.EshopClient, AuthoritiesConstants.CompanyUser)
            .antMatchers("/api/company-locations/**").hasAuthority(AuthoritiesConstants.CompanyOwner)
            .antMatchers("/api/items/validateCommande/**").hasAuthority(AuthoritiesConstants.EshopClient)
             .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.CompanyOwner)
            .antMatchers("http://localhost:8080/actuator/httptrace").hasAuthority(AuthoritiesConstants.CompanyOwner)
        .and()
            .apply(securityConfigurerAdapter());
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
