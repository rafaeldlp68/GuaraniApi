package com.example.guaraniapi.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Essa classe é responsável por interceptar e lidar com erros de autenticação.
 * Quando uma requisição não autenticada ou com token inválido tenta acessar um recurso protegido,
 * o Spring Security invoca essa classe para gerar a resposta apropriada.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Método invocado sempre que uma requisição não autenticada tenta acessar um recurso seguro.
     * 
     * @param request A requisição HTTP.
     * @param response A resposta HTTP.
     * @param authException A exceção lançada durante a autenticação.
     * @throws IOException Exceção de I/O caso ocorra algum erro ao tentar escrever a resposta.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Define o status HTTP 401 (Unauthorized) quando ocorre uma falha de autenticação.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acesso negado. Você deve estar autenticado para acessar esse recurso.");
    }
}
