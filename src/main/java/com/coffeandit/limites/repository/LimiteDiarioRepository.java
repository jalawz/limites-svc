package com.coffeandit.limites.repository;

import com.coffeandit.limites.entity.LimiteDiario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimiteDiarioRepository extends CrudRepository<LimiteDiario, Long> {

    LimiteDiario findByAgenciaAndConta(Long agencia, Long conta);
}