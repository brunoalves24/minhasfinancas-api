package io.github.brunoalves24.minhasfinancas.exceptions;

public class ErroAutenticacao extends RuntimeException{
    public ErroAutenticacao(String mensagem) {
        super(mensagem);
    }
}
