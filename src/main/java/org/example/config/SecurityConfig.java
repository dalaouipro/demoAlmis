package org.example.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Order(1)
@Configuration
public class SecurityConfig {

    @Value("${management.endpoints.web.base-path:/actuator}")
    private String actuatorEndpoint;

    @Bean
    public SecurityFilterChain h2SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.antMatcher("/h2-console/**")
                .authorizeRequests().anyRequest().anonymous()
                .and().csrf().disable()
                .headers().frameOptions().disable();
        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain actuatorFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.antMatcher(actuatorEndpoint + "/**")
                .authorizeRequests().anyRequest().anonymous()
                .and().csrf().disable();
        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain processFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.antMatcher("/process/**")
                .authorizeRequests().anyRequest().anonymous()
                .and().csrf().disable();
        return httpSecurity.build();
    }
}