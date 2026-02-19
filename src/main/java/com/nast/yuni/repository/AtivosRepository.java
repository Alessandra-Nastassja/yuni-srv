package com.nast.yuni.repository;

import com.nast.yuni.domain.Ativos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtivosRepository extends JpaRepository<Ativos, Long> {
    List<Ativos> findAllByOrderByValorAtualDesc();
}
