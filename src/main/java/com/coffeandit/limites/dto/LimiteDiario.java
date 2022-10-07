package com.coffeandit.limites.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
public class LimiteDiario {

    private Long id;
    private Long agencia;
    private Long conta;
    private BigDecimal valor;
}
