package com.salesianostriana.edu.MiarmaProject.error.config.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class ApiError {

    private HttpStatus status;
    private int codigo;
    private String mensaje;
    private String ruta;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fecha = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> subErrorList;

    public ApiError(HttpStatus status, int codigo, String mensaje,String ruta, List<ApiSubError> subErrorList, LocalDateTime fecha) {
        this.status = status;
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.ruta = ruta;
        this.subErrorList = subErrorList;
        this.fecha = fecha;
    }
}
