package mingeso.proyecto.autofix_reportes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReparacionDTO {
    Long id;
    String nombre;
    Long monto;
    String descripcion;
}
