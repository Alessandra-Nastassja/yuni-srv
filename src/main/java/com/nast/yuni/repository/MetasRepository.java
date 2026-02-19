package com.nast.yuni.repository;

import com.nast.yuni.domain.Metas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetasRepository extends JpaRepository<Metas, Long> {
    List<Metas> findAllByOrderByPrazoAsc();
}

