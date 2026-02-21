package com.nast.yuni.repository;

import com.nast.yuni.domain.NaoAtivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NaoAtivosRepository extends JpaRepository<NaoAtivos, Long> {
    List<NaoAtivos> findAllByOrderByValorAtualDesc();
}

