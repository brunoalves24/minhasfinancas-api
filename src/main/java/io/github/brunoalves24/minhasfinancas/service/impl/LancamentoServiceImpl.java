package io.github.brunoalves24.minhasfinancas.service.impl;

import io.github.brunoalves24.minhasfinancas.exceptions.RegraNegocioException;
import io.github.brunoalves24.minhasfinancas.model.entity.Lancamento;
import io.github.brunoalves24.minhasfinancas.model.enums.StatusLancamento;
import io.github.brunoalves24.minhasfinancas.model.repository.LancamentoRepository;
import io.github.brunoalves24.minhasfinancas.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImpl implements LancamentoService {
    private LancamentoRepository lancamentoRepository;

    @Autowired
    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validarLancamento(lancamento);
        lancamento.setStatusLancamento(StatusLancamento.PENDENTE);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        validarLancamento(lancamento);
        Objects.requireNonNull(lancamento.getId());
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deletarLancamento(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        lancamentoRepository.delete(lancamento);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Lancamento> buscarLancamento(Lancamento lancamentoFiltro) {
        Example example = Example.of(lancamentoFiltro,
                ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return lancamentoRepository.findAll(example);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento) {
        lancamento.setStatusLancamento(statusLancamento);
        atualizar(lancamento);
    }

    @Override
    public void validarLancamento(Lancamento lancamento) {
        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals(""))
            throw new RegraNegocioException("Informe uma descrição valida");

        if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12)
            throw new RegraNegocioException("Informe um mês valido");

        if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4)
            throw new RegraNegocioException("Informe um ano valido");

        if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null)
            throw new RegraNegocioException("Informe um usuário");

        if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1)
            throw new RegraNegocioException("Informe um valor válido");

        if(lancamento.getTipoLancamento() == null)
            throw new RegraNegocioException("Informe um tipo de lançamento válido");
    }
}
