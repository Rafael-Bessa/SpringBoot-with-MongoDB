package bessa.morangon.rafael.api.configurations.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.DateTimeException;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsDTO> tratamentoMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        return ResponseEntity.badRequest().body(new ErrorsDTO(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErrorsDTO> tratamentoDateTimeException(DateTimeException e){
        return ResponseEntity.badRequest().body(new ErrorsDTO(null, e.getMessage()));
    }
}
