package bessa.morangon.rafael.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaDTO {
    private String id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataReceita;
}
