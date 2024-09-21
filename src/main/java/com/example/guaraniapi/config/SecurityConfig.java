package com.example.guaraniapi.config;

import com.example.guaraniapi.service.UserDetailsServiceImpl;
import com.example.guaraniapi.config.JwtUtils;
import com.example.guaraniapi.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils, UsuarioRepository usuarioRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Atualização para usar a nova sintaxe com lambda DSL
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF
            .cors(cors -> {}) // Habilita CORS (se houver configuração adicional)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/dissertacoes", "/monografias", "/teses", "/artigos").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios/cadastrar").permitAll()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated() // Qualquer outra requisição precisa estar autenticada
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sessão stateless
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, usuarioRepository), UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro de autenticação via JWT

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
