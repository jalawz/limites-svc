package com.coffeandit.limites.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@Getter
@Setter
@ToString
public class BeneficiarioDto implements Serializable {


    private Long cpf;
    private Long codigoBanco;
    private String agencia;
    private String conta;
    private String nomeFavorecido;
}
