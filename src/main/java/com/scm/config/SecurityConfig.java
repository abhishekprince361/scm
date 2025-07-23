package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.scm.services.impl.SecurityCustomeUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomeUserDetailsService userDetailsService;

    @Autowired
    private OAuthAuthenicationsuccessHandler oAuthAuthenicationsuccessHandler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    // @Bean
    // private UserDetailsService userDetailsService(){
    // // this bean use in memory it don't use the database
    // UserDetails user1 =
    // User.withDefaultPasswordEncoder().withUsername("admin123").password("admin123").roles("ADMIN",
    // "USER").build();

    // // we can insert multiple user also

    // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
    // return inMemoryUserDetailsManager;

    // }
    @Bean
    @SuppressWarnings("deprecation")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home", "/register").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        httpSecurity.formLogin(formLogin -> {
            // override the spring login
            formLogin.loginPage("/login");
            // processing and submit on this url
            formLogin.loginProcessingUrl("/authenticate");
            // after successful login
            formLogin.successForwardUrl("/user/profile");
            // if got any error
            // formLogin.failureForwardUrl("/login?error=true");
            // customize username field by default it is (username)
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            /*
             * formLogin.successHandler(new AuthenticationSuccessHandler() {
             * 
             * @Override
             * public void onAuthenticationSuccess(HttpServletRequest request,
             * HttpServletResponse response,
             * Authentication authentication) throws IOException, ServletException {
             * 
             * throw new
             * UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'"
             * );
             * }
             * 
             * });
             * 
             * formLogin.successHandler(new AuthenticationSuccessHandler() {
             * 
             * @Override
             * public void onAuthenticationSuccess(HttpServletRequest request,
             * HttpServletResponse response,
             * Authentication authentication) throws IOException, ServletException {
             * 
             * throw new
             * UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'"
             * );
             * }
             * 
             * });
             */
            formLogin.failureHandler(authFailureHandler);
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // oauth configuration
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(oAuthAuthenicationsuccessHandler);
        });

        // by default it is get method but logout required post method because it is
        // secure with csrf so we have to desable csrf
        // csrf is activatet by default because spring is using EnableWebSecurity
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout?true");
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
