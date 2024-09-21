package com.example.guaraniapi.config;

import com.example.guaraniapi.model.Usuario;
import com.example.guaraniapi.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Filtro de autorização que valida o token JWT e define o usuário autenticado no contexto
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils; // Serviço de manipulação de tokens JWT
    private final UsuarioRepository usuarioRepository; // Repositório de usuários

    // Construtor que injeta as dependências de utilitários JWT e o repositório de usuários
    public JwtAuthorizationFilter(JwtUtils jwtUtils, UsuarioRepository usuarioRepository) {
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // Recupera o token da requisição
        String token = recuperarToken(request);

        // Verifica se o token é válido
        if (token != null && jwtUtils.isTokenValido(token)) {
            Long idUsuario = jwtUtils.getIdUsuario(token); // Recupera o ID do usuário a partir do token
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null); // Busca o usuário no banco de dados

            if (usuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Cria o objeto de autenticação do Spring Security
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                // Adiciona os detalhes da requisição
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Define o contexto de segurança com o usuário autenticado
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continua o fluxo de requisição
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para recuperar o token JWT do cabeçalho da requisição
    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7); // Remove o prefixo "Bearer " do token
        }
        return null;
    }
}
