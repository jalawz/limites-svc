package com.coffeandit.limites.service;

import com.coffeandit.limites.dto.SituacaoEnum;
import com.coffeandit.limites.dto.TransactionDto;
import com.coffeandit.limites.entity.LimiteDiario;
import com.coffeandit.limites.events.LimiteProducer;
import com.coffeandit.limites.repository.LimiteDiarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LimiteDiarioService {

    private final LimiteDiarioRepository limiteDiarioRepository;
    private final LimiteProducer limiteProducer;

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
                    .data(LocalDateTime.now())
                    .build();
            return Optional.of(limiteDiarioRepository.save(limite));
        }

        return limiteDiario;
    }

    public void validarLimiteDiario(TransactionDto transactionDto) {
        var limiteDiario = limiteDiarioRepository.findByAgenciaAndContaAndData(
                transactionDto.getConta().getCodigoAgencia(),
                transactionDto.getConta().getCodigoConta(),
                LocalDateTime.now()
        );

        if (Objects.isNull(limiteDiario)) {
            limiteDiario = limiteDiarioRepository.save(
                    LimiteDiario.builder()
                            .agencia(transactionDto.getConta().getCodigoConta())
                            .conta(transactionDto.getConta().getCodigoConta())
                            .valor(valorDiario)
                            .data(LocalDateTime.now())
                            .build()
            );
        }

        if (limiteDiario.getValor().compareTo(transactionDto.getValor()) < 0) {
            transactionDto.suspeitaDeFraude();
            log.info("Transação excede o valor diário: {}", transactionDto);
        } else if (limiteDiario.getValor().compareTo(BigDecimal.valueOf(10000L)) > 0) {
            transactionDto.analiseHumana();
            log.info("Transação está em análise humana: {}", transactionDto);
        } else {
            transactionDto.analisada();
            log.info("Transação está analisada: {}", transactionDto);
            limiteDiario.setValor(limiteDiario.getValor().subtract(transactionDto.getValor()));
            limiteDiarioRepository.save(limiteDiario);
        }

        limiteProducer.send(transactionDto);
    }
}
