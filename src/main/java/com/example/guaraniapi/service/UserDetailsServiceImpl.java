package com.example.guaraniapi.service;

import com.example.guaraniapi.model.Usuario;
import com.example.guaraniapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service // Marca a classe como um serviço do Spring
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository; // Injeta o repositório de usuários

    /**
     * Carrega os detalhes de um usuário com base no email (username).
     * @param email O email do usuário a ser buscado
     * @return Os detalhes do usuário para autenticação
     * @throws UsernameNotFoundException se o usuário não for encontrado
     */
    @Override
    @Transactional // Garante a consistência transacional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados com base no email
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));

        // Retorna os detalhes do usuário (pode ser um UserDetails customizado ou não)
        return JwtUserDetails.build(usuario);
    }
}
