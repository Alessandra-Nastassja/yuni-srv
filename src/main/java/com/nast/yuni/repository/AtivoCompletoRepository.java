package com.nast.yuni.repository;

import com.nast.yuni.domain.AtivosCompleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtivoCompletoRepository extends JpaRepository<AtivosCompleto, Long> {
    List<AtivosCompleto> findAllByOrderByValorAtualDesc();
    List<AtivosCompleto> findByTipo(String tipo);
    List<AtivosCompleto> findByTipoInvestimento(String tipoInvestimento);
}

