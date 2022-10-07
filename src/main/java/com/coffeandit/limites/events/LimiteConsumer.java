package com.coffeandit.limites.events;

import com.coffeandit.limites.dto.TransactionDto;
import com.coffeandit.limites.service.LimiteDiarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LimiteConsumer {

    private final LimiteDiarioService limiteDiarioService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${app.topic}")
    public void onConsumer(final String message) {
        try {
            TransactionDto transactionDto = getTransaction(message);
            limiteDiarioService.validarLimiteDiario(transactionDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    private TransactionDto getTransaction(String message) throws JsonProcessingException {
        TransactionDto transactionDto = objectMapper.readValue(message, TransactionDto.class);
        transactionDto.setData(LocalDateTime.now());
        return transactionDto;
    }
}
