package bessa.morangon.rafael.api.configurations.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ErrorsDTO {
    private String campo;
    private String erro;
}
