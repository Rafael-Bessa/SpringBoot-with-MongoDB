package bessa.morangon.rafael.api.domain.dto;

import bessa.morangon.rafael.api.domain.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumoDTO {

    private BigDecimal totalReceitaMes;
    private BigDecimal totalDespesaMes;
    private BigDecimal saldoMes;
    private Map<Categoria, BigDecimal> gastosPorCategoria = new HashMap<>();
}
