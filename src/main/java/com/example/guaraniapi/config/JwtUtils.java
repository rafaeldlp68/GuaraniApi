package com.example.guaraniapi.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import com.example.guaraniapi.service.JwtUserDetails;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtils {

    // Chave secreta para assinatura do token. Use uma chave segura em produção.
    private String secret = "minhaChaveSecretaSuperSegura";
    private byte[] secretBytes = secret.getBytes(); // Converte a chave secreta em um array de bytes

    // Tempo de expiração do token (1 hora, por exemplo)
    private long expirationTime = 3600000;

    /**
     * Gera um token JWT a partir dos detalhes do usuário autenticado.
     * @param jwtUserDetails Detalhes do usuário autenticado.
     * @return O token JWT gerado.
     */
    public String generateJwtToken(JwtUserDetails jwtUserDetails) {
        // Gera uma chave de assinatura segura usando o algoritmo HS512
        Key signingKey = Keys.hmacShaKeyFor(secretBytes);

        return Jwts.builder()
                .setSubject(Long.toString(jwtUserDetails.getId())) // Define o ID do usuário como o "subject" do token
                .setIssuedAt(new Date()) // Data de emissão do token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Define a expiração do token
                .signWith(signingKey, SignatureAlgorithm.HS512) // Assina o token com a chave segura
                .compact(); // Finaliza a criação do token JWT
    }

    /**
     * Valida se o token é válido (verifica a assinatura e se não está expirado).
     * @param token O token JWT.
     * @return true se o token é válido, false se não.
     */
    public boolean isTokenValido(String token) {
        try {
            Claims claims = obterClaims(token); // Extrai as informações do token
            if (claims.getExpiration().before(new Date())) { // Verifica se o token já expirou
                return false;
            }
            return true; // O token é válido
        } catch (Exception e) {
            return false; // Qualquer erro durante a validação torna o token inválido
        }
    }

    /**
     * Extrai o ID do usuário do token JWT.
     * @param token O token JWT.
     * @return O ID do usuário.
     */
    public Long getIdUsuario(String token) {
        Claims claims = obterClaims(token); // Extrai as informações do token
        return Long.parseLong(claims.getSubject()); // O ID do usuário está no campo "subject"
    }

    /**
     * Extrai as informações (claims) contidas no token.
     * @param token O token JWT.
     * @return As informações extraídas do token.
     */
    private Claims obterClaims(String token) {
        // Atualiza a lógica para usar Jwts.parserBuilder() em vez de Jwts.parser()
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretBytes)) // Define a chave secreta para verificar a assinatura do token
                .build() // Constrói o JwtParser
                .parseClaimsJws(token) // Faz o parse do token e retorna suas claims
                .getBody();
    }
}
