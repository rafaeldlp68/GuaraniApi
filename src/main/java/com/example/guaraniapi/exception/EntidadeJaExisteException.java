package com.example.guaraniapi.exception;

/**
 * Exceção personalizada que é lançada quando uma entidade já existe no sistema.
 */
public class EntidadeJaExisteException extends RuntimeException {

    // Construtor que recebe uma mensagem personalizada
    public EntidadeJaExisteException(String mensagem) {
        super(mensagem);  // Chama o construtor da superclasse (RuntimeException) com a mensagem de erro
    }
}
