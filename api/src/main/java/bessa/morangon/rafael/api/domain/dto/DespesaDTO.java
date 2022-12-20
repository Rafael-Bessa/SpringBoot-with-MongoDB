package bessa.morangon.rafael.api.domain.dto;

import bessa.morangon.rafael.api.domain.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaDTO {
    private String id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dataDespesa = LocalDate.now();
    private Categoria categoria = Categoria.OUTRAS;
}
