package com.elearn.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        InMemoryUserDetailsManager userDetailsManager=new InMemoryUserDetailsManager();
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("abc")
//                        .password("abc")
//                        .roles("ADMIN")
//                        .build()
//        );
//
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("Priyesh")
//                        .password("Xyz")
//                        .roles("ADMIN")
//                        .build()
//        );
//        return userDetailsManager;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //customization
//        httpSecurity.cors(e->e.disable());
//Routes
        httpSecurity.authorizeHttpRequests(e->{
            e.requestMatchers(HttpMethod.GET,"/api/v1/categories","/api/v1/videos").permitAll()
                    .requestMatchers("/client-login","/client-login-process").permitAll()
//                    .requestMatchers(HttpMethod.GET,"/api/v1/courses").permitAll()
                    .anyRequest().authenticated();
        });

        //form customization
        httpSecurity.formLogin(
                form->{
                    form.loginPage("/client-login");
                    form.usernameParameter("username");
                    form.passwordParameter("userpassword");
                    form.loginProcessingUrl("/client-login-process");
                    form.successForwardUrl("/success");

                }
        );
        httpSecurity.logout(logout->{
            logout.logoutUrl("/logout");
        });
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
