package com.oauth2.authorizationserver.config;/*
 * Created by amaity at 10/2/2018
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    // Admin app resource Id
    private final String ADMIN_RESOURCE_ID = "ADMIN_RESOURCES";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(ADMIN_RESOURCE_ID);
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin()  // To deliver lib contents, like H2 console
                .and()
                .authorizeRequests()
                .antMatchers("/", "/users/**", "/console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole("ADMIN") // Admin app restrict to ADMIN role
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
