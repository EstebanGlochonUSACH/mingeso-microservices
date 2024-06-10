package mingeso.proyecto.autofix_ordenes.dtos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BonoDTO {
    Long id;
    MarcaDTO marca;
    Integer monto;
    Boolean usado;
    LocalDateTime fechaInicio;
    LocalDateTime fechaTermino;
}
