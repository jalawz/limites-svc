package com.coffeandit.limites.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
@ToString(of = {"uuid", "situacao"})
public class TransactionDto {

    private UUID uuid;
    private BigDecimal valor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime data;

    private Conta conta;

    private BeneficiarioDto beneficiario;

    private TipoTransacao tipoTransacao;

    private SituacaoEnum situacao;

    public void suspeitaDeFraude() {
        this.situacao = SituacaoEnum.EM_SUSPEITA_FRAUDE;
    }

    public void analisada() {
        this.situacao = SituacaoEnum.ANALISADA;
    }

    public void analiseHumana() {
        this.situacao = SituacaoEnum.EM_ANALISE_HUMANA;
    }
}
