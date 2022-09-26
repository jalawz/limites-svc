package com.coffeandit.limites.service;

import com.coffeandit.limites.entity.LimiteDiario;
import com.coffeandit.limites.repository.LimiteDiarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LimiteDiarioService {

    private final LimiteDiarioRepository limiteDiarioRepository;

    @Value("${limite.valor:0}")
    private BigDecimal valorDiario;

    public Optional<LimiteDiario> findById(Long id) {
        return limiteDiarioRepository.findById(id);
    }

    public Optional<LimiteDiario> buscarLimiteDiario(final Long agencia, final Long conta) {
        Optional<LimiteDiario> limiteDiario = limiteDiarioRepository.findByAgenciaAndConta(agencia, conta);
        if (limiteDiario.isEmpty()) {
            LimiteDiario limite = LimiteDiario.builder()
                    .conta(conta)
                    .agencia(agencia)
                    .valor(valorDiario)
                    .build();
            return Optional.of(limiteDiarioRepository.save(limite));
        }

        return limiteDiario;
    }
}
