package io.github.brunoalves24.minhasfinancas.service;

import io.github.brunoalves24.minhasfinancas.exceptions.ErroAutenticacao;
import io.github.brunoalves24.minhasfinancas.exceptions.RegraNegocioException;
import io.github.brunoalves24.minhasfinancas.model.entity.Usuario;
import io.github.brunoalves24.minhasfinancas.model.repository.UsuarioRepository;
import io.github.brunoalves24.minhasfinancas.service.impl.UsuarioServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ExtendWith( SpringExtension.class )
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioServiceTest {

    @Mock
    UsuarioService usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    @Test
    public void deveValidarEmail() {
        //Scenario
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //action
        usuarioService.validarEmail("email@email.com");

    }

    @Test
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
        //scenario
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //Action
        Assertions.assertThrows(RegraNegocioException.class, () -> {
            usuarioService.validarEmail("email@email.com");
        });

    }
    @Test
    public void deveAutenticarUmUsuarioComSucesso() {
        //Cenário
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when( usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //Ação
        Usuario result = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());

        //Verification
        org.assertj.core.api.Assertions.assertThat(result).isNotNull();

    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComEmail() {

        //Cenario

        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ErroAutenticacao.class, () -> {
            usuarioService.autenticar("email@email.com", "senha");
        });


    }
}
