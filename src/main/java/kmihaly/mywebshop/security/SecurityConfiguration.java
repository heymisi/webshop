/*
package kmihaly.mywebshop.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(WebSecurity  web) throws Exception {

        web.ignoring()
                .antMatchers("/VAADIN/**","/favicon.ico", "/vaadinServlet/**")
                .antMatchers("/actuator/**")
                .antMatchers("/h2/**");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http.authorizeRequests()
                			.antMatchers("/actuator/**", "/h2/**").permitAll()
                			.anyRequest().authenticated()
                			.and()
                			.formLogin()
                			.and()
                			.headers().frameOptions().disable().and()
                			.csrf().disable();


        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
*/
