package uz.personal.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import uz.personal.config.handler.AuthenticationFailureHandler;
import uz.personal.controller.ApiController;

import static uz.personal.controller.ApiController.API_PATH;
import static uz.personal.controller.ApiController.FORGOT_PASSWORD;
import static uz.personal.controller.ApiController.LOGIN_URL;
import static uz.personal.controller.ApiController.REFRESH_TOKEN_URL;
import static uz.personal.controller.ApiController.V_1;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/secured/**";
    private static final String AUTH_PATTERN = ApiController.AUTH + "/**";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
        resources.authenticationEntryPoint(new AuthenticationFailureHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(API_PATH + V_1 + "/resource/uploads/**", LOGIN_URL, REFRESH_TOKEN_URL, FORGOT_PASSWORD).permitAll()
                .antMatchers(API_PATH + "/**").access(SECURED_WRITE_SCOPE)
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
                .and().exceptionHandling().authenticationEntryPoint(new AuthenticationFailureHandler());

        http.cors().and().csrf().disable();
    }
}
