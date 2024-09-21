package com.example.guaraniapi.controller;

import com.example.guaraniapi.model.Usuario;
import com.example.guaraniapi.dto.UsuarioCadastro;
import com.example.guaraniapi.dto.UsuarioListagemDto;
import com.example.guaraniapi.exception.EntidadeJaExisteException;
import com.example.guaraniapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios") // Define a rota base para todas as requisições relacionadas a "usuarios"
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService; // Injeta o serviço que gerencia as operações com usuários

    /**
     * Endpoint para cadastrar um novo usuário.
     * @param usuarioDto Dados do usuário recebidos no corpo da requisição.
     * @return Retorna o usuário salvo ou uma mensagem de erro em caso de conflito (usuário já existente).
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid UsuarioCadastro usuarioDto) {
        try {
            // Converte o DTO de cadastro em um objeto Usuario e salva no banco de dados
            Usuario usuarioSalvo = usuarioService.salvar(usuarioDto.converterCadastro());
            return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED); // Retorna 201 Created se tudo der certo
        } catch (EntidadeJaExisteException e) {
            // Se o usuário já existe, retorna um erro 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os usuários paginados.
     * @param paginacao Parâmetros de paginação fornecidos na requisição.
     * @return Uma página de usuários no formato DTO.
     */
    @GetMapping("/listar")
    public Page<UsuarioListagemDto> listar(@PageableDefault(page = 0, size = 15) Pageable paginacao) {
        // Lista todos os usuários com paginação
        Page<Usuario> usuarios = usuarioService.listaUsuario(paginacao);
        // Converte a lista de usuários para DTO antes de enviar a resposta
        return UsuarioListagemDto.converter(usuarios);
    }

    /**
     * Endpoint para obter as informações do usuário logado.
     * @param logado Objeto do usuário autenticado, obtido automaticamente.
     * @return Informações do perfil do usuário autenticado.
     */
    @GetMapping("/perfil")
    public Optional<UsuarioListagemDto> infoUsuario(@AuthenticationPrincipal Usuario logado) {
        // Busca as informações do usuário logado com base no ID
        Optional<Usuario> usuarioSalvo = usuarioService.GetInfoUsuario(logado.getId());

        // Converte o Optional<Usuario> diretamente para Optional<UsuarioListagemDto> usando map()
        return usuarioSalvo.map(UsuarioListagemDto::new);
    }

    /**
     * Endpoint para buscar um usuário por ID.
     * @param id ID do usuário fornecido na URL.
     * @return Informações do usuário encontradas, ou vazio se não existir.
     */
    @GetMapping("/id/{id}")
    public Optional<UsuarioListagemDto> buscaPorId(@PathVariable Long id) {
        // Busca as informações de um usuário com base no ID fornecido
        Optional<Usuario> usuarioSalvo = usuarioService.GetInfoUsuario(id);

        // Converte o Optional<Usuario> diretamente para Optional<UsuarioListagemDto> usando map()
        return usuarioSalvo.map(UsuarioListagemDto::new);
    }
}
