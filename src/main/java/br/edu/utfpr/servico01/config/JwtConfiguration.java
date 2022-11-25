package br.edu.utfpr.servico01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class JwtConfiguration extends WebSecurityConfigurerAdapter {
  private final UserDetailServiceImp userDetailServiceImp;
  private final PasswordEncoder passwordEncoder;

  public JwtConfiguration(UserDetailServiceImp userDetailServiceImp, PasswordEncoder passwordEncoder) {
    this.userDetailServiceImp = userDetailServiceImp;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailServiceImp).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
      .antMatchers(HttpMethod.POST, "/login").permitAll()
      .and()
      .addFilter(new JwtAuthenticationFilter(authenticationManager()))
      .addFilter(new JwtValidationFilter(authenticationManager()))
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  // .anyRequest().authenticated()

  @Bean
  CorsConfigurationSource corsConfigurationSource(){
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }
}
