package com.coffeandit.limites.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Conta implements Serializable {


    private Long codigoAgencia;
    private Long codigoConta;
}
