package bessa.morangon.rafael.api.domain.model;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "despesas")
@NoArgsConstructor
@Data
public class Despesa {
    @Id
    private String id;
    @NotBlank
    @Size(min = 5, max = 50)
    private String descricao;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=6, fraction=2)
    @NotNull
    private BigDecimal valor;
    @Nullable
    private LocalDate dataDespesa = LocalDate.now();
    @Nullable
    private Categoria categoria = Categoria.OUTRAS;
}
