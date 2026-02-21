package com.nast.yuni.repository;

import com.nast.yuni.domain.RendaVariavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendaVariavelRepository extends JpaRepository<RendaVariavel, Long> {
    List<RendaVariavel> findByTipoRendaVariavel(String tipoRendaVariavel);
}

