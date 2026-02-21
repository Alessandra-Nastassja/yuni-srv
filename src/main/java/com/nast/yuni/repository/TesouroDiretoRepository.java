package com.nast.yuni.repository;

import com.nast.yuni.domain.TesouroDireto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesouroDiretoRepository extends JpaRepository<TesouroDireto, Long> {
}

