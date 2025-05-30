package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Peca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {
}
