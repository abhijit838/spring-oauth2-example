package com.oauth2.authorizationserver.config;/*
 * Created by amaity at 10/2/2018
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin-client")
                .authorizedGrantTypes("password","refresh_token")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .scopes("read", "write", "trust")
                .resourceIds("ADMIN_RESOURCES")
                .accessTokenValiditySeconds(5000)
                .secret("$2a$10$9f2S18I9uPq7HgbzUFYGGutEMfNYlCOSnHhIgnZoOfH2JuAHxwKG2")  // admin
                .refreshTokenValiditySeconds(50000)

                .and()
                .withClient("remote-client")
                .authorizedGrantTypes("password","refresh_token", "client_credential", "authorization")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .scopes("read", "write", "trust")
                .resourceIds("REMOTE_RESOURCES")
                .accessTokenValiditySeconds(5000)
                .secret("$2a$10$9f2S18I9uPq7HgbzUFYGGutEMfNYlCOSnHhIgnZoOfH2JuAHxwKG2")  // admin
                .refreshTokenValiditySeconds(50000);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    // TODO: Use Jdbc Client Details Service
    // TODO: Use Jdbc Token Store
    // TODO: Use Jdbc Authorization Code
    // TODO: Use Jdbc Token Approval Store
}
