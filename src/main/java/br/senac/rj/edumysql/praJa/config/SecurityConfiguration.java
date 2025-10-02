package br.senac.rj.edumysql.praJa.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired
  private UserAuthenticationFilter userAuthenticationFilter;

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  public static final String [] PUBLIC_ENDPOINTS = {
      "/api/usuario/login", // Url que usaremos para fazer login
      "/api/usuario/criar", // Url que usaremos para criar um usuário

      //Swagger/OpenAPI UI,
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/swagger-ui.html"

  };

  // Endpoints que requerem autenticação para serem acessados
  public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
      "/api/usuario/listar"
  };
  public static final String [] ENDPOINTS_CLIENTE = {
      "/api/grupo/fichastecnicas/listar"
  };

  // Endpoints que só podem ser acessador por usuários com permissão de vendedor
  public static final String [] ENDPOINTS_VENDEDOR = {
      "/api/grupo/listar",
      "/api/grupo/fichastecnicas/listar",
      "/api/ficha/listar",
  };
  public static final String [] ENDPOINTS_COZINHA = {
      // GRUPO
      "/api/grupo/criar",
      "/api/grupo/listar",
      "/api/grupo/ingrediente/listar",
      "/api/grupo/fichatecnica/listar",
      "/api/grupo/apagar/",
      "/api/grupo/buscar/{id}",
      "/api/grupo/alterar/status/{id}",

      // Ficha Tecnica
      "/api/ficha",
      "/api/ficha/criar",
      "/api/ficha/listar",
  };

  // Endpoints que só podem ser acessador por usuários com permissão de administrador
  public static final String [] ENDPOINTS_ADMIN = {
      "/api/grupo/**"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
            .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRADOR")
            .requestMatchers(ENDPOINTS_VENDEDOR).hasRole("VENDEDOR")
            .requestMatchers(ENDPOINTS_COZINHA).hasRole("COZINHEIRO")
            .requestMatchers(ENDPOINTS_CLIENTE).hasRole("CLIENTE")
            .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
            .anyRequest().denyAll()
        )
        .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
