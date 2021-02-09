package io.github.brunoalves24.minhasfinancas.service;

import io.github.brunoalves24.minhasfinancas.model.entity.Lancamento;
import io.github.brunoalves24.minhasfinancas.model.enums.StatusLancamento;

import java.util.*;

public interface LancamentoService {
    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletarLancamento(Lancamento lancamento);

    List<Lancamento> buscarLancamento(Lancamento lancamentoFiltro);

    void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento);

    void validarLancamento(Lancamento lancamento);
}
