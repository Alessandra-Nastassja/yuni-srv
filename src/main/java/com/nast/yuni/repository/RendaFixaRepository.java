package com.nast.yuni.repository;

import com.nast.yuni.domain.RendaFixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendaFixaRepository extends JpaRepository<RendaFixa, Long> {
    List<RendaFixa> findByTipoAtivoRendaFixa(String tipoAtivo);
}

