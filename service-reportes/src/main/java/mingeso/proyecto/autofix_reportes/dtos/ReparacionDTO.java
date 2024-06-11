package mingeso.proyecto.autofix_reportes.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReparacionDTO {
    Long id;
    Long tipo;
    Long monto;
    LocalDateTime fechaRegistro;
}
