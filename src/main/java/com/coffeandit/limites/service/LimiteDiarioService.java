package com.coffeandit.limites.service;

import com.coffeandit.limites.entity.LimiteDiario;
import com.coffeandit.limites.repository.LimiteDiarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LimiteDiarioService {

    private final LimiteDiarioRepository limiteDiarioRepository;

    public Optional<LimiteDiario> findById(Long id) {
        return limiteDiarioRepository.findById(id);
    }
}
