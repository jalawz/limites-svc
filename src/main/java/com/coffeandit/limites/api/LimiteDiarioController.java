package com.coffeandit.limites.api;

import com.coffeandit.limites.entity.LimiteDiario;
import com.coffeandit.limites.service.LimiteDiarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/limite-diario")
@RequiredArgsConstructor
public class LimiteDiarioController {

    private final LimiteDiarioService service;

    @GetMapping("/{id}")
    public LimiteDiario findById(@PathVariable("id") Long id) {
        Optional<LimiteDiario> limiteDiario = service.findById(id);

        if (limiteDiario.isPresent()) {
            return limiteDiario.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado");
    }
}
