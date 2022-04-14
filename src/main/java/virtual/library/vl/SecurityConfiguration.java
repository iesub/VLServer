package virtual.library.vl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import virtual.library.vl.handler.CustomAuthenticationFailureHandler;
import virtual.library.vl.service.UserService;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories
@EnableTransactionManagement
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
        implements WebMvcConfigurer {

    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("https://virtual-book-shelf.herokuapp.com")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws
            Exception {
        http.csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers("/delete/book").hasAuthority("ROLE_ADMINISTRATOR")
                .antMatchers( "/login", "/ifAuthenticated", "/", "/login", "/registration",
                        "/loginSuccess", "/registration", "/get/book-count", "/get/book-list", "logout",
                        "/get/book-by-name", "/get/authors", "/get/book-genres", "/get/book-tags", "/get/book-by-filter")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/loginSuccess")
                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().rememberMe().rememberMeParameter("rememberme").key("keepsecret").
                tokenValiditySeconds(60*60*24*7);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
}
