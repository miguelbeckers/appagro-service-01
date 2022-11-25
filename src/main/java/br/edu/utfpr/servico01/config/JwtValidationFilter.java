package br.edu.utfpr.servico01.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtValidationFilter extends BasicAuthenticationFilter{
  public static final String ATTRIBUTE_HEADER = "Authorization";
  public static final String ATTRIBUTE_PREFIX = "Bearer ";


  public JwtValidationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    
    String attribute = request.getHeader(ATTRIBUTE_HEADER);
    if(attribute == null || !attribute.startsWith(ATTRIBUTE_PREFIX)){
      chain.doFilter(request, response);
      return;
    }

    String token = attribute.replace(ATTRIBUTE_PREFIX, "");
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthenticationToken(token);

    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){
    String user = JWT.require(Algorithm.HMAC512(JwtAuthenticationFilter.TOKEN_PASSWORD))
      .build()
      .verify(token)
      .getSubject();
    
    if (user == null){
      return null;
    }

    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
  }
  
}
