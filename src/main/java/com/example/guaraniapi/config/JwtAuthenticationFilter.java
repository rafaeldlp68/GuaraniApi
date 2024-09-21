package com.example.guaraniapi.config;

import com.example.guaraniapi.model.Usuario;
import com.example.guaraniapi.repository.UsuarioRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Filtro de autenticação que intercepta todas as requisições
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils; // Classe para manipulação de tokens JWT
    private final UsuarioRepository usuarioRepository; // Repositório de usuários

    // Construtor que recebe o serviço de token e o repositório de usuários
    public JwtAuthenticationFilter(JwtUtils jwtUtils, UsuarioRepository usuarioRepository) {
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) 
            throws ServletException, IOException {
        // Recupera o token JWT da requisição
        String token = recuperarToken(request);

        // Verifica se o token é válido
        boolean tokenValido = jwtUtils.isTokenValido(token);

        if (tokenValido) {
            // Autentica o usuário com base no token
            autenticarCliente(token);
        }

        // Prossegue com o fluxo de requisição
        filterChain.doFilter(request, response);
    }

    // Método para autenticar o usuário com base no token JWT
    private void autenticarCliente(String token) {
        Long idUsuario = jwtUtils.getIdUsuario(token); // Recupera o ID do usuário a partir do token
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null); // Busca o usuário no banco
        if (usuario != null) {
            // Cria o token de autenticação do Spring Security
            UsernamePasswordAuthenticationToken authentication = 
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            // Define o usuário autenticado no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    // Método para recuperar o token JWT do cabeçalho da requisição
    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null; // Se o token não estiver presente ou mal formatado, retorna null
        }
        return token.substring(7); // Retorna o token sem o prefixo "Bearer "
    }
}
