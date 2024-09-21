package com.example.guaraniapi.service;

import com.example.guaraniapi.model.Usuario;
import com.example.guaraniapi.repository.UsuarioRepository;
import com.example.guaraniapi.exception.EntidadeJaExisteException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    // Injetamos o repositório de usuário para interagir com o banco de dados
    private final UsuarioRepository usuarioRepository;

    // Construtor para injetar o repositório usando o Spring (sem necessidade de @Autowired por causa do Lombok/Java 17)
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método para salvar um novo usuário no banco de dados, verificando antes se o email já está em uso.
     * @param usuario O usuário que será salvo.
     * @return O usuário salvo no banco de dados.
     * @throws EntidadeJaExisteException Se o email já estiver cadastrado no sistema.
     */
    public Usuario salvar(Usuario usuario) throws EntidadeJaExisteException {
        // Verifica se o email já está em uso por outro usuário
        boolean emailEmUso = usuarioRepository.findByEmail(usuario.getEmail())
                .stream()
                .anyMatch(usuarioExistente -> !usuarioExistente.equals(usuario));

        // Se o email já estiver em uso, lança uma exceção
        if (emailEmUso) {
            throw new EntidadeJaExisteException(String.format("Já existe um usuário cadastrado com esse E-mail"));
        }
        
        // Salva o usuário no banco de dados
        return usuarioRepository.save(usuario);
    }

    /**
     * Método para listar os usuários com suporte à paginação.
     * @param paginacao Objeto que contém as informações de paginação (página e tamanho da página).
     * @return Página com a lista de usuários.
     */
    public Page<Usuario> listaUsuario(Pageable paginacao) {
        // Busca todos os usuários no banco de dados com paginação
        return usuarioRepository.findAll(paginacao);
    }

    /**
     * Método para obter as informações de um usuário específico com base no ID.
     * @param idUsuario O ID do usuário que está sendo buscado.
     * @return Um Optional contendo o usuário, se encontrado.
     */
    public Optional<Usuario> GetInfoUsuario(Long idUsuario) {
        // Busca o usuário pelo ID no banco de dados
        return usuarioRepository.findById(idUsuario);
    }
}
