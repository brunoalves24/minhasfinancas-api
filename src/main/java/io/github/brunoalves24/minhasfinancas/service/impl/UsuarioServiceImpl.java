package io.github.brunoalves24.minhasfinancas.service.impl;

import io.github.brunoalves24.minhasfinancas.exceptions.ErroAutenticacao;
import io.github.brunoalves24.minhasfinancas.exceptions.RegraNegocioException;
import io.github.brunoalves24.minhasfinancas.model.entity.Usuario;
import io.github.brunoalves24.minhasfinancas.model.repository.UsuarioRepository;
import io.github.brunoalves24.minhasfinancas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
         Optional<Usuario> usuario = repository.findByEmail(email);

         if(!usuario.isPresent()) {
             throw new ErroAutenticacao("Email invalido ou não encontrado para o email informado");
         }

         if(!usuario.get().getSenha().equals(senha)) {
             throw new ErroAutenticacao("Email ou Senha Inválida");
         }

        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());

        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        if(repository.existsByEmail(email)) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse email.");
        }
    }
}
