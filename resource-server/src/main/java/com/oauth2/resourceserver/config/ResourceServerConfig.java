package com.oauth2.resourceserver.config;
/*
 * Created by amaity at 10/2/2018
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/*
This is an example of remote resource server oauth2 authentication
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${oauth-config.resource-id}")
    private String remoteResources;

    @Value("${oauth-config.client-id}")
    private String clientId;

    @Value("${oauth-config.client-secret}")
    private String clientSecret;

    @Value("${oauth-config.token-endpoint}")
    private String checkTokenEndpoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(remoteResources);  // make sure that resource id is configured
        super.configure(resources);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/resource/**").access("#oauth2.hasScope('read')")
            .anyRequest().authenticated();
    }

    @Bean
    @Primary
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        tokenServices.setCheckTokenEndpointUrl(checkTokenEndpoint);

        return tokenServices;
    }
}
