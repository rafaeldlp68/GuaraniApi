package com.example.guaraniapi.controller;

import com.example.guaraniapi.model.JwtRequest;
import com.example.guaraniapi.model.JwtResponse;
import com.example.guaraniapi.service.JwtUserDetails;
//import com.example.guaraniapi.service.UserDetailsServiceImpl;
import com.example.guaraniapi.config.JwtUtils;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth") // Define a rota base para autenticação
@RequiredArgsConstructor // Gera um construtor com base nos campos finais (final fields)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    //private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    /**
     * Endpoint para fazer login e gerar um token JWT.
     * @param jwtRequest Contém as credenciais (email e senha) fornecidas pelo usuário.
     * @return ResponseEntity contendo o token JWT gerado ou uma mensagem de erro.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid JwtRequest jwtRequest) {
        // Autentica o usuário usando o AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                jwtRequest.getEmail(),
                jwtRequest.getSenha()
            )
        );

        // Define o contexto de segurança
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Obtém os detalhes do usuário autenticado
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

        // Gera o token JWT com base nos detalhes do usuário
        String jwt = jwtUtils.generateJwtToken(userDetails);

        // Retorna o token JWT
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    /**
     * Endpoint para validar o token JWT e fornecer os detalhes do usuário.
     * @return ResponseEntity contendo as informações do usuário autenticado.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        // Obtém o usuário autenticado do contexto de segurança
        JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Retorna os detalhes do usuário autenticado
        return ResponseEntity.ok(userDetails);
    }
}
