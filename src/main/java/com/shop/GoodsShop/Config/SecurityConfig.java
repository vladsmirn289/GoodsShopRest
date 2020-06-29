package com.shop.GoodsShop.Config;

import com.shop.GoodsShop.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;
    private ClientService clientService;
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                    .antMatchers(
                        "/",
                        "/category/*",
                        "/item/*",
                        "/item/*/image",
                        "/webjars/**",
                        "/css/**",
                        "/images/**",
                        "/registration",
                        "/client/activate/*")
                    .permitAll()

                    .antMatchers(
                            "/basket",
                            "/myOrders",
                            "/personalRoom",
                            "/addToBasket")
                    .authenticated()

                    .anyRequest()
                    .authenticated()

                .and()

                    .formLogin()
                    .loginPage("/login")
                    .successHandler(successHandler)
                    .permitAll()

                .and()

                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(clientService)
                .passwordEncoder(passwordEncoder);
    }
}
