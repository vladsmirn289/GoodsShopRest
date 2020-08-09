package com.shop.GoodsShop.Config;

import com.shop.GoodsShop.Config.JWT.JwtFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private PasswordEncoder passwordEncoder;
    private ClientService clientService;
    private AuthenticationSuccessHandler successHandler;
    private RestTemplate restTemplate;
    private JwtFilter jwtFilter;

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

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(restTemplate);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        filter.setAuthenticationManager(authenticationManager());

        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()

                    .antMatchers(
                        "/",
                        "/category/*",
                        "/item",
                        "/item/*",
                        "/item/*/image",
                        "/webjars/**",
                        "/css/**",
                        "/images/**",
                        "/registration",
                        "/client/activate/*",
                        "/client/setNewEmail/**")
                    .permitAll()

                    .antMatchers(
                            "/order/manager",
                            "/order/setManager/*",
                            "/order/editOrder/*",
                            "/order/changeOrderStatus/*")
                    .hasRole("MANAGER")

                    .antMatchers("/admin/**")
                    .hasRole("ADMIN")

                    .anyRequest()
                    .authenticated()

                .and()

                    .logout()
                    .permitAll();

        http.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtFilter, customAuthenticationFilter().getClass());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(clientService)
                .passwordEncoder(passwordEncoder);
    }
}
