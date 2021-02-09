package io.github.brunoalves24.minhasfinancas.model.repository;

import io.github.brunoalves24.minhasfinancas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
